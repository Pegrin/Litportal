package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.UsersEntity;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.UUID;

@Service
public class ServiceUsersCommon implements ServiceUsers {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOUsers<UsersEntity, UUID> daoUsers;

    @Autowired
    public void setDaoUsers(DAOUsers<UsersEntity, UUID> daoUsers) {
        this.daoUsers = daoUsers;
    }

    @Override
    public UsersEntity getUserByLogin(String login) throws ServiceException {
        UsersEntity user = null;
        try {
            user = daoUsers.getByLogin(login);
        } catch (DBException e) {
            String msg = "Ошибка получения пользователя по логину.";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
        return user;
    }


    @Override
    public boolean RegisterNewUser(String login, String password, String email, String visible_name) {
        boolean result = false;
        UsersEntity user = daoUsers.getNewEntity(login, password, (short) 0, email, visible_name);
        try {
            daoUsers.persist(user);
            result = true;
        } catch (DBException e) {
            logger.error("Не удалось зарегистрировать пользователя: "
                    + user.getLogin() + ", email: " + user.getEmail(), e);
        }
        return result;
    }
}
