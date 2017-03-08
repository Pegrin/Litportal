package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by olymp on 07.03.2017.
 */
public interface AuthenticatorV2 {
    String sha256PlusSalt(String input);

    boolean checkAuthentication(String login, String password) throws ServiceException;

    boolean checkAuthenticationAndCreateSession(String login, String password, HttpServletRequest req) throws ServiceException;

    void closeSession(HttpSession session);
}
