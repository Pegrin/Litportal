package org.wtiger.inno.litportal.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olymp on 03.03.2017.
 */
public interface Authenticator {
    String sha256PlusSalt(String input);

    boolean checkAuth(boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp);

    void goToAuthPage(HttpServletRequest req, HttpServletResponse resp);

    boolean authenticate(String login, String password, boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp) throws IOException;

    void closeSession(HttpServletRequest req, HttpServletResponse resp);
}
