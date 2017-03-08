package org.wtiger.inno.litportal.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olymp on 03.03.2017.
 */
@Deprecated
public interface Authenticator {
    String sha256PlusSalt(String input);

    @Deprecated
    boolean checkAuth(boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp);

    @Deprecated
    void goToAuthPage(HttpServletRequest req, HttpServletResponse resp);

    @Deprecated
    boolean authenticate(String login, String password, boolean redirectOnFail, HttpServletRequest req, HttpServletResponse resp) throws IOException;

    @Deprecated
    void closeSession(HttpServletRequest req, HttpServletResponse resp);
}
