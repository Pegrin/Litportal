package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowUsers;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

@Service
public class ServiceUsersCommon implements ServiceUsers {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOUsers daoUsers;

    @Autowired
    public void setDaoUsers(DAOUsers daoUsers) {
        this.daoUsers = daoUsers;
    }

    @Override
    public TableRowUsers getUserByLogin(String login) throws serviceException {
        TableRowUsers user = null;
        try {
            user = daoUsers.getObjectByLogin(login);
        } catch (DBException e) {
            String msg = "Ошибка получения пользователя по логину.";
            logger.error(msg, e);
            throw new serviceException(msg);
        }
        return user;
    }


    @Override
    public boolean RegisterNewUser(String login, String password, String email, String visible_name) {
        boolean result = false;
        TableRowUsers user = new TableRowUsers(null, login, password, 0L, email, visible_name);
        try {
            daoUsers.loadObjToDB(user);
            result = true;
        } catch (DBException e) {
            logger.error("Не удалось зарегистрировать пользователя: "
                    + user.getLogin() + ", email: " + user.getEmail(), e);
        }
        return result;
    }
}
