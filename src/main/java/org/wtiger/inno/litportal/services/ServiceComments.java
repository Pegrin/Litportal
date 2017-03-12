package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.CommentsEntity;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceComments {
    List<CommentsEntity> getCommentsByID(UUID postUuid) throws ServiceException;

    void CreateNewComment(CommentsEntity comment) throws ServiceException;
}
