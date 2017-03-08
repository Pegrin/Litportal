package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.UsersEntity;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceUsers {
    UsersEntity getUserByLogin(String login) throws ServiceException;

    boolean RegisterNewUser(String login, String password, String email, String visible_name);
}
