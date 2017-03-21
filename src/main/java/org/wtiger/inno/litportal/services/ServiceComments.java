package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.pojo.CommentPojo;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceComments {
    List<CommentPojo> getCommentsByPostID(UUID postUuid) throws ServiceException;

    void updateComment(CommentPojo comment) throws ServiceException;

    CommentPojo getCommentByID(UUID commentUuid) throws ServiceException;

    void createNewComment(UUID postUuid, String body, UUID userUuid) throws ServiceException;

    String TextToHTML(String string);

    void deleteComment(CommentPojo comment) throws ServiceException;
}
