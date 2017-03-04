package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.TableRowUsers;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceUsers {
    TableRowUsers getUserByLogin(String login) throws serviceException;

    boolean RegisterNewUser(String login, String password, String email, String visible_name);
}
