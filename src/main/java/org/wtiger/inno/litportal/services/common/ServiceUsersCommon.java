package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.hibernate.UsersEntity;
import org.wtiger.inno.litportal.models.pojo.UserPojo;
import org.wtiger.inno.litportal.models.utils.Transformer;
import org.wtiger.inno.litportal.services.ServiceUsers;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.UUID;

@Service
public class ServiceUsersCommon implements ServiceUsers {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOUsers<UsersEntity, UUID> daoUsers;
    private Transformer<UsersEntity, UserPojo> userPojoTransformer;

    @Autowired
    public void setDaoUsers(DAOUsers<UsersEntity, UUID> daoUsers) {
        this.daoUsers = daoUsers;
    }

    @Override
    public UserPojo getUserByLogin(String login) throws ServiceException {
        UserPojo user = null;
        try {
            user = userPojoTransformer.transformFromEntityToPojo(daoUsers.getByLogin(login));
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

    @Override
    public UserPojo getUserByID(UUID userUuid) {
        UserPojo userPojo = null;
        try {
            UsersEntity usersEntity = daoUsers.findByID(userUuid);
            userPojo = userPojoTransformer.transformFromEntityToPojo(usersEntity);
        } catch (DBException e) {
            logger.error("Ошибка при попытке найти пользователя с UUID: " + userUuid.toString());
        }
        return userPojo;
    }

    @Override
    public void updateUserInfo(UserPojo userPojo) throws ServiceException {
        if (userPojo != null && userPojo.getUserUuid() != null) {
            try {
                UsersEntity usersEntity = daoUsers.findByID(userPojo.getUserUuid());
                if (userPojo.getLogin() != null) {
                    usersEntity.setLogin(userPojo.getLogin());
                }
                if (userPojo.getEmail() != null) {
                    usersEntity.setEmail(userPojo.getEmail());
                }
                if (userPojo.getPassword() != null) {
                    usersEntity.setPassword(userPojo.getPassword());
                }
                if (userPojo.getRole() != null) {
                    usersEntity.setRole(userPojo.getRole());
                }
                if (userPojo.getEnabled() != null) {
                    usersEntity.setEnabled(userPojo.getEnabled());
                }
                if (userPojo.getVisibleName() != null) {
                    usersEntity.setVisibleName(userPojo.getVisibleName());
                }
                if (userPojo.getVersion() != null) {
                    usersEntity.setVersion(userPojo.getVersion());
                }
                daoUsers.update(usersEntity);
            } catch (DBException e) {
                logger.error("Ошибка при попытке сохранить пользователя с UUID: " + userPojo.getUserUuid().toString());
                throw new ServiceException();
            }
        }
    }

    @Autowired
    public void setUserPojoTransformer(Transformer<UsersEntity, UserPojo> userPojoTransformer) {
        this.userPojoTransformer = userPojoTransformer;
    }
}
