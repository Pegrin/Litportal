package org.wtiger.inno.litportal.servlets.utils;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DBUsers;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.rows.TRUsers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

/**
 * Created by olymp on 26.02.2017.
 */
public class Authenticator {
    private static Logger logger = Logger.getLogger(Authenticator.class);

    public static String sha256PlusSalt(String input) {
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

    private static boolean isLoginedIn(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Object login = session.getAttribute("user");
        return (login != null);
    }

    public static boolean checkAuth(HttpServletRequest req, HttpServletResponse resp) {
        boolean result;
        if (!isLoginedIn(req, resp)) {
            result = false;
            goToAuthPage(req, resp);
        } else result = true;
        return result;
    }

    public static void goToAuthPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("./login");
        } catch (IOException e) {
            logger.warn("Не удалось перенаправить пользователя на страницу авторизации.", e);
        }
    }

    public static boolean authenticate(String login, String password, boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp) {
        boolean result = false;
        Connection con = PoolConnections.getConnection();
        DBUsers dbUsers = new DBUsers(con);
        TRUsers user = dbUsers.getObjectByLogin(login);
        String passwordSHAPS = sha256PlusSalt(password);
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

    public void closeSession(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
    }
}
