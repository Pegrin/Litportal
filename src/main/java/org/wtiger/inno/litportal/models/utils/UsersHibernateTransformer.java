package org.wtiger.inno.litportal.models.utils;

import org.springframework.stereotype.Component;
import org.wtiger.inno.litportal.models.hibernate.UsersEntity;
import org.wtiger.inno.litportal.models.pojo.UserPojo;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 18.03.2017.
 */
@Component
public class UsersHibernateTransformer implements Transformer<UsersEntity, UserPojo> {
    @Override
    public UsersEntity transformFromPojoToEntity(UserPojo pojo) {
        UsersEntity entity = null;
        if (pojo != null) {
            entity = new UsersEntity(pojo.getUserUuid(),
                    pojo.getLogin(),
                    pojo.getPassword(),
                    pojo.getRole(),
                    pojo.getEmail(),
                    pojo.getVisibleName(),
                    pojo.getEnabled());
            entity.setVersion(pojo.getVersion());
        }
        return entity;
    }

    @Override
    public UserPojo transformFromEntityToPojo(UsersEntity entity) {
        UserPojo pojo = null;
        if (entity != null) {
            pojo = new UserPojo(entity.getUserUuid(),
                    entity.getLogin(),
                    entity.getPassword(),
                    entity.getRole(),
                    entity.getEmail(),
                    entity.getVisibleName(),
                    entity.getEnabled());
            pojo.setVersion(entity.getVersion());
        }
        return pojo;
    }
}
