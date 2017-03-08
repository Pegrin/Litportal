package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOPosts;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.PostsEntity;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для рабооты с
 */

@Service
public class ServicePostsCommon implements ServicePosts {
    private static Logger logger = Logger.getLogger(ServicePostsCommon.class);
    DAOPosts<PostsEntity, UUID, UUID, UUID> daoPosts;

    @Autowired
    public void setDaoPosts(DAOPosts daoPosts) {
        this.daoPosts = daoPosts;
    }

    @Override
    public List<PostsEntity> getPostsByGroupID(UUID group_uuid) throws ServiceException {
        List<PostsEntity> posts = null;
        try {
            posts = daoPosts.findByGroupID(group_uuid);
        } catch (Exception e) {
            String msg = "Ошибка при получении списка публикаций по ID группы";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
        return posts;
    }

    @Override
    public PostsEntity getPostByID(UUID postUuid) throws ServiceException {
        try {
            PostsEntity post = daoPosts.findByID(postUuid);
            return post;
        } catch (DBException e) {
            String msg = "Ошибка уровня сервиса при получении публикации по ID";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
    }
}
