package org.wtiger.inno.litportal.models.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wtiger.inno.litportal.models.hibernate.PostsEntity;
import org.wtiger.inno.litportal.models.pojo.PostPojo;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 18.03.2017.
 */
@Component
public class PostsHibernateTransformer implements Transformer<PostsEntity, PostPojo> {
    @Autowired
    private GroupsHibernateTransformer groupsHibernateTransformer;
    @Autowired
    private UsersHibernateTransformer usersHibernateTransformer;

    @Override
    public PostsEntity transformFromPojoToEntity(PostPojo pojo) {
        PostsEntity entity = null;
        if (pojo != null) {
            entity = new PostsEntity(pojo.getPostUuid(),
                    pojo.getGroupUuid(),
                    pojo.getDate(),
                    pojo.getHead(),
                    pojo.getBody(),
                    pojo.getNewBodyRequest(),
                    pojo.getCommit(),
                    pojo.getUserUuid());
            entity.setVersion(pojo.getVersion());
            entity.setUsersByUserUuid(usersHibernateTransformer.transformFromPojoToEntity(pojo.getUsersByUserUuid()));
            entity.setGroupsByGroupUuid(groupsHibernateTransformer.transformFromPojoToEntity(pojo.getGroupsByGroupUuid()));
        }
        return entity;
    }

    @Override
    public PostPojo transformFromEntityToPojo(PostsEntity entity) {
        PostPojo pojo = null;
        if (entity != null) {
            pojo = new PostPojo(entity.getPostUuid(),
                    entity.getGroupUuid(),
                    entity.getDate(),
                    entity.getHead(),
                    entity.getBody(),
                    entity.getNewBodyRequest(),
                    entity.getCommit(),
                    entity.getUserUuid());
            pojo.setVersion(entity.getVersion());
            pojo.setUsersByUserUuid(usersHibernateTransformer.transformFromEntityToPojo(entity.getUsersByUserUuid()));
            pojo.setGroupsByGroupUuid(groupsHibernateTransformer.transformFromEntityToPojo(entity.getGroupsByGroupUuid()));
        }
        return pojo;
    }

    public void setGroupsHibernateTransformer(GroupsHibernateTransformer groupsHibernateTransformer) {
        this.groupsHibernateTransformer = groupsHibernateTransformer;
    }

    public void setUsersHibernateTransformer(UsersHibernateTransformer usersHibernateTransformer) {
        this.usersHibernateTransformer = usersHibernateTransformer;
    }
}
