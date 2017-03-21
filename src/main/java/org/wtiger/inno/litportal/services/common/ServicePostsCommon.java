package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOPosts;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.hibernate.PostsEntity;
import org.wtiger.inno.litportal.models.pojo.PostPojo;
import org.wtiger.inno.litportal.models.utils.Transformer;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для рабооты с
 */

@Service
public class ServicePostsCommon implements ServicePosts {
    private static Logger logger = Logger.getLogger(ServicePostsCommon.class);
    private Transformer<PostsEntity, PostPojo> postPojoTransformer;
    private DAOPosts<PostsEntity, UUID, UUID, UUID> daoPosts;

    @Autowired
    public void setDaoPosts(DAOPosts daoPosts) {
        this.daoPosts = daoPosts;
    }

    @Override
    public List<PostPojo> getPostsByGroupID(UUID group_uuid) throws ServiceException {
        List<PostPojo> posts = null;
        try {
            posts = daoPosts.findByGroupID(group_uuid).stream()
                    .map(postPojoTransformer::transformFromEntityToPojo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            String msg = "Ошибка при получении списка публикаций по ID группы";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
        return posts;
    }

    @Override
    public PostPojo getPostByID(UUID postUuid) throws ServiceException {
        try {
            PostPojo post = postPojoTransformer.transformFromEntityToPojo(daoPosts.findByID(postUuid));
            return post;
        } catch (DBException e) {
            String msg = "Ошибка уровня сервиса при получении публикации по ID";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
    }

    @Override
    public String TextToHTML(String string) {
        String result = string.replace("\n", "<br>");
        return result;
    }

    @Override
    public List<PostPojo> getPostsByUser(UUID userUuid) throws ServiceException {
        List<PostPojo> list = null;
        try {
            list = daoPosts.findByUserID(userUuid).stream()
                    .map(postPojoTransformer::transformFromEntityToPojo)
                    .collect(Collectors.toList());
        } catch (DBException e) {
            String msg = "Ошибка уровня сервиса при получении списка публикаций по ID автора";
            logger.error(msg, e);
            throw new ServiceException(msg);
        }
        return list;
    }

    @Autowired
    public void setPostPojoTransformer(Transformer<PostsEntity, PostPojo> postPojoTransformer) {
        this.postPojoTransformer = postPojoTransformer;
    }
}
