package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOComments;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.hibernate.CommentsEntity;
import org.wtiger.inno.litportal.models.pojo.CommentPojo;
import org.wtiger.inno.litportal.models.utils.Transformer;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by olymp on 28.02.2017.
 */
@Service
public class ServiceCommentsCommon implements ServiceComments {
    private Logger logger = Logger.getLogger(ServiceCommentsCommon.class);
    private DAOComments<CommentsEntity, UUID, UUID, UUID> daoComments;
    private Transformer<CommentsEntity, CommentPojo> commentPojoTransformer;

    @Autowired
    public void setDaoComments(DAOComments daoComments) {
        this.daoComments = daoComments;
    }

    @Override
    public List<CommentPojo> getCommentsByPostID(UUID postUuid) throws ServiceException {
        List<CommentPojo> list = null;
        try {
            list = daoComments.findByPostID(postUuid).stream()
                    .map(commentPojoTransformer::transformFromEntityToPojo)
                    .collect(Collectors.toList());
        } catch (DBException e) {
            logger.error("Ошибка при получении списка комментариев к публикации.", e);
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public void updateComment(CommentPojo comment) throws ServiceException {
        try {
            daoComments.update(commentPojoTransformer.transformFromPojoToEntity(comment));
        } catch (DBException e) {
            logger.error("Ошибка при сохранении комментария.", e);
            throw new ServiceException();
        }
    }

    @Override
    public CommentPojo getCommentByID(UUID commentUuid) throws ServiceException {
        CommentPojo comment = null;
        try {
            comment = commentPojoTransformer.transformFromEntityToPojo(daoComments.findByID(commentUuid));
        } catch (DBException e) {
            logger.error("Ошибка при поиске комментария по ID.", e);
            throw new ServiceException();
        }
        return comment;
    }

    @Override
    public void createNewComment(UUID postUuid, String body, UUID userUuid) throws ServiceException {
        CommentsEntity comment = null;
        comment = daoComments.getNewEntity(postUuid, null, body, userUuid);
        comment.setDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            daoComments.persist(comment);
        } catch (DBException e) {
            logger.error("Ошибка при создании нового комментария.", e);
            throw new ServiceException();
        }
        return;
    }

    @Override
    public String TextToHTML(String string) {
        String result = string.replace("\n", "<br>");
        return result;
    }

    @Override
    public void deleteComment(CommentPojo comment) throws ServiceException {
        try {
            daoComments.delete(commentPojoTransformer.transformFromPojoToEntity(comment));
        } catch (DBException e) {
            logger.error("Не удалось удалить комментарий", e);
            throw new ServiceException();
        }
    }

    @Autowired
    public void setCommentPojoTransformer(Transformer<CommentsEntity, CommentPojo> commentPojoTransformer) {
        this.commentPojoTransformer = commentPojoTransformer;
    }
}
