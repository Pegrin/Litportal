package org.wtiger.inno.litportal.models.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wtiger.inno.litportal.models.hibernate.CommentsEntity;
import org.wtiger.inno.litportal.models.pojo.CommentPojo;

/**
 * @author Khayrutdinov Marat
 *         Email: mail@wtiger.org
 *         Created on 18.03.2017.
 */
@Component
public class CommentsHibernateTransformer implements Transformer<CommentsEntity, CommentPojo> {
    @Autowired
    private PostsHibernateTransformer postsHibernateAdapter;
    @Autowired
    private UsersHibernateTransformer usersHibernateAdapter;

    @Override
    public CommentsEntity transformFromPojoToEntity(CommentPojo pojo) {
        CommentsEntity entity = null;
        if (pojo != null) {
            entity = new CommentsEntity(pojo.getCommentUuid(),
                    pojo.getPostUuid(),
                    pojo.getParentCommentUuid(),
                    pojo.getBody(),
                    pojo.getDate(),
                    pojo.getUserUuid());
            entity.setVersion(pojo.getVersion());
            entity.setPostsByPostUuid(postsHibernateAdapter.transformFromPojoToEntity(pojo.getPostsByPostUuid()));
            entity.setUsersByUserUuid(usersHibernateAdapter.transformFromPojoToEntity(pojo.getUsersByUserUuid()));
        }
        return entity;
    }

    @Override
    public CommentPojo transformFromEntityToPojo(CommentsEntity entity) {
        CommentPojo pojo = null;
        if (entity != null) {
            pojo = new CommentPojo(entity.getCommentUuid(),
                    entity.getPostUuid(),
                    entity.getParentCommentUuid(),
                    entity.getBody(),
                    entity.getDate(),
                    entity.getUserUuid());
            pojo.setVersion(entity.getVersion());
            pojo.setPostsByPostUuid(postsHibernateAdapter.transformFromEntityToPojo(entity.getPostsByPostUuid()));
            pojo.setUsersByUserUuid(usersHibernateAdapter.transformFromEntityToPojo(entity.getUsersByUserUuid()));
        }
        return pojo;
    }

    public void setUsersHibernateAdapter(UsersHibernateTransformer usersHibernateAdapter) {
        this.usersHibernateAdapter = usersHibernateAdapter;
    }

    public void setPostsHibernateAdapter(PostsHibernateTransformer postsHibernateAdapter) {
        this.postsHibernateAdapter = postsHibernateAdapter;
    }
}
