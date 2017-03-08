package org.wtiger.inno.litportal.web.controlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wtiger.inno.litportal.services.AuthenticatorV2;
import org.wtiger.inno.litportal.services.ServiceUsers;

/**
 * Created by olymp on 08.03.2017.
 */
@Controller
public class RegistrationController {
    private static Logger logger = Logger.getLogger(RegistrationController.class);
    private AuthenticatorV2 authenticatorV2;
    private ServiceUsers serviceUsers;

    @Autowired
    public void setServiceUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    @Autowired
    public void setAuthenticatorV2(AuthenticatorV2 authenticatorV2) {
        this.authenticatorV2 = authenticatorV2;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationPage() {
        return "registration";
    }
}
