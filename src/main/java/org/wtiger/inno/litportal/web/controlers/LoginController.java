package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.services.AuthenticatorV2;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 */
@Controller
public class LoginController {
    private AuthenticatorV2 authenticator;
    private Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    public void setAuthenticator(AuthenticatorV2 authenticator) {
        this.authenticator = authenticator;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultPage() {
        return "redirect:./posts";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        authenticator.closeSession(session);
        return "redirect:./posts";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authorize(@RequestParam(name = "login", defaultValue = "") String login,
                            @RequestParam(name = "password", defaultValue = "") String password,
                            HttpServletRequest req) {
        String result = "";
        try {
            boolean authed = authenticator.checkAuthenticationAndCreateSession(login, password, req);
            if (authed) {
                result = "redirect:./posts";
            } else {
                result = "authError";
            }
        } catch (ServiceException e) {
            logger.error("Ошибка  БД при попытке авторизации.", e);
            result = "dbError";
        }
        return result;
    }


}
