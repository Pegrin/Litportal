package org.wtiger.inno.litportal.models.utils;

import org.springframework.stereotype.Component;
import org.wtiger.inno.litportal.models.hibernate.GroupsEntity;
import org.wtiger.inno.litportal.models.pojo.GroupPojo;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 18.03.2017.
 */
@Component
public class GroupsHibernateTransformer implements Transformer<GroupsEntity, GroupPojo> {
    @Override
    public GroupsEntity transformFromPojoToEntity(GroupPojo pojo) {
        GroupsEntity entity = null;
        if (pojo != null) {
            entity = new GroupsEntity(pojo.getGroupUuid(),
                    pojo.getParentGroupUuid(),
                    pojo.getHead(),
                    pojo.getBody());
            entity.setVersion(pojo.getVersion());
            entity.setGroupsByParentGroupUuid(transformFromPojoToEntity(pojo.getGroupsByParentGroupUuid()));
        }
        return entity;
    }

    @Override
    public GroupPojo transformFromEntityToPojo(GroupsEntity entity) {
        GroupPojo pojo = null;
        if (entity != null) {
            pojo = new GroupPojo(entity.getGroupUuid(),
                    entity.getParentGroupUuid(),
                    entity.getHead(),
                    entity.getBody());
            pojo.setVersion(entity.getVersion());
            pojo.setGroupsByParentGroupUuid(transformFromEntityToPojo(entity.getGroupsByParentGroupUuid()));
        }
        return pojo;
    }
}
