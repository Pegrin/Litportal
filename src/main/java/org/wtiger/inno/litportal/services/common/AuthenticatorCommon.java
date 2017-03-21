package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.services.Authenticator;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by olymp on 26.02.2017.
 */
@Service
@Deprecated
public class AuthenticatorCommon implements Authenticator {
    private static Logger logger = Logger.getLogger(AuthenticatorCommon.class);
    ServiceUsers serviceUsers;

    @Autowired
    public void setServiceUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    @Override
    public String sha256PlusSalt(String input) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Алгоритм SHA256 не обнаружен в библиотеке.");
        }
        input += "mybeel0vedslat";
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private boolean isLoginedIn(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Object login = session.getAttribute("user");
        boolean result = false;
        if (login != null) {
            result = true;
            req.setAttribute("user", login);
        }
        return result;
    }

    @Override
    public boolean checkAuth(boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp) {
        boolean result;
        if (!isLoginedIn(req, resp)) {
            result = false;
            if (redirectOnFail) goToAuthPage(req, resp);
        } else result = true;
        return result;
    }

    @Override
    public void goToAuthPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("./login");
        } catch (IOException e) {
            logger.warn("Не удалось перенаправить пользователя на страницу авторизации.", e);
        }
    }

    @Override
    public boolean authenticate(String login, String password, boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean result = false;
        UserPojo user = null;
        try {
            user = serviceUsers.getUserByLogin(login);
        } catch (ServiceException e) {
            logger.error("Не удалось провести авторизацию пользователя.", e);
            if (redirectOnFail) resp.sendRedirect("./error");

        }
        String passwordSHAPS = sha256PlusSalt(password != null ? password : "");
        HttpSession session = req.getSession(true);
        session.setAttribute("user", null);
        if (user != null && (user.getLogin().toLowerCase()).equals(login.toLowerCase())
                && user.getPassword().equals(passwordSHAPS)) {
            session.setAttribute("user", user);
            return true;
        } else {
            if (redirectOnFail) goToAuthPage(req, resp);
        }
        return result;
    }

    @Override
    public void closeSession(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
    }
}
