package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceUsers {
    UserPojo getUserByLogin(String login) throws ServiceException;

    boolean RegisterNewUser(String login, String password, String email, String visible_name);

    UserPojo getUserByID(UUID userUuid);

    void updateUserInfo(UserPojo userPojo) throws ServiceException;
}
