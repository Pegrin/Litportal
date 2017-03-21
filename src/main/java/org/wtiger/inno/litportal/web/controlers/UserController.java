package org.wtiger.inno.litportal.web.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 19.03.2017.
 */
@Controller
public class UserController {
    private ServiceUsers serviceUsers;

    @RequestMapping(value = "/editUserProfile", method = RequestMethod.GET)
    public String getUserProfileEditPage(Model model,
                                         @RequestParam("userUuid") String userUuidParam) {
        String result = "userProfileEdit";
        try {
            UUID userUuid = UUID.fromString(userUuidParam);
            UserPojo user = serviceUsers.getUserByID(userUuid);
            model.addAttribute("euser", user);
        } catch (Exception e) {
            return "dbError";
        }
        return result;
    }

    @RequestMapping(value = "/editUserProfile", method = RequestMethod.POST)
    public String updateUserProfile(UserPojo userPojo,
                                    @RequestParam(value = "enabled", required = false, defaultValue = "false") Boolean enabled,
                                    HttpServletRequest httpServletRequest) {
        String result = "redirect:./editUserProfile?userUuid=" + userPojo.getUserUuid();
        try {
            userPojo.setEnabled(enabled);
            serviceUsers.updateUserInfo(userPojo);
        } catch (ServiceException e) {
            result = "dbError";
        }
        return result;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getMyProfilePage(Model model, HttpSession session) {
        String result = "myProfile";
        UserPojo userPojo = (UserPojo) session.getAttribute("user");
        if (userPojo == null) {
            result = "redirect:./login";
        } else {
            model.addAttribute("euser", userPojo);
        }
        return result;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String updateMyProfilePage(UserPojo userPojo, HttpSession session) {
        String result = "redirect:./profile";
        try {
            serviceUsers.updateUserInfo(userPojo);
            UserPojo user = serviceUsers.getUserByID(userPojo.getUserUuid());
            session.setAttribute("user", user);
        } catch (ServiceException e) {
            result = "dbError";
        }
        return result;
    }

    @Autowired
    public void setServiceUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }
}
