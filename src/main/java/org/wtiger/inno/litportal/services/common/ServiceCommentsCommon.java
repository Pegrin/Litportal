package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOComments;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.CommentsEntity;
import org.wtiger.inno.litportal.services.ServiceComments;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 28.02.2017.
 */
@Service
public class ServiceCommentsCommon implements ServiceComments {
    private Logger logger = Logger.getLogger(ServiceCommentsCommon.class);
    private DAOComments daoComments;

    @Autowired
    public void setDaoComments(DAOComments daoComments) {
        this.daoComments = daoComments;
    }

    @Override
    public List<CommentsEntity> getCommentsByID(UUID postUuid) throws ServiceException {
        List<CommentsEntity> list = null;
        try {
            list = daoComments.findByPostID(postUuid);
        } catch (DBException e) {
            logger.error("Ошибка при получении списка комментариев к публикации.", e);
            throw new ServiceException();
        }
        return list;
    }

    @Override
    public void CreateNewComment(CommentsEntity comment) throws ServiceException {
        try {
            daoComments.update(comment);
        } catch (DBException e) {
            logger.error("Ошибка при сохранении комментария.", e);
            throw new ServiceException();
        }
    }


}
