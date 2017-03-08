package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.models.rows.UsersEntity;
import org.wtiger.inno.litportal.services.AuthenticatorV2;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by olymp on 07.03.2017.
 */
@Service
public class AuthenticatorForMVC implements AuthenticatorV2 {
    private static Logger logger = Logger.getLogger(AuthenticatorForMVC.class);
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

    @Override
    public boolean checkAuthentication(String login, String password) throws ServiceException {
        boolean result = false;
        UsersEntity user = null;
        try {
            user = serviceUsers.getUserByLogin(login);
        } catch (ServiceException e) {
            logger.error("Не удалось провести авторизацию пользователя.", e);
            throw new ServiceException();
        }
        String passwordSHAPS = sha256PlusSalt(password != null ? password : "");
        if (user != null && (user.getLogin().toLowerCase()).equals(login.toLowerCase())
                && user.getPassword().equals(passwordSHAPS)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkAuthenticationAndCreateSession(String login, String password, HttpServletRequest req) throws ServiceException {
        boolean result = false;
        UsersEntity user = null;
        try {
            user = serviceUsers.getUserByLogin(login);
        } catch (ServiceException e) {
            logger.error("Не удалось провести авторизацию пользователя.", e);
            throw new ServiceException();
        }
        String passwordSHAPS = sha256PlusSalt(password != null ? password : "");
        HttpSession session = req.getSession(true);
        session.setAttribute("user", null);
        if (user != null && (user.getLogin().toLowerCase()).equals(login.toLowerCase())
                && user.getPassword().equals(passwordSHAPS)) {
            session.setAttribute("user", user);
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void closeSession(HttpSession session) {
        if (session != null) session.invalidate();
    }
}
