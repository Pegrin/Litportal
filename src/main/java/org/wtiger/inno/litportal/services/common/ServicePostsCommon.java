package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOPosts;
import org.wtiger.inno.litportal.models.rows.TableRowPosts;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import java.util.ArrayList;

/**
 * Сервис для рабооты с
 */

@Service
public class ServicePostsCommon implements ServicePosts {
    private static Logger logger = Logger.getLogger(ServicePostsCommon.class);
    DAOPosts daoPosts;

    @Autowired
    public void setDaoPosts(DAOPosts daoPosts) {
        this.daoPosts = daoPosts;
    }

    @Override
    public ArrayList<TableRowPosts> getPostsByGroupID(String group_uuid) throws serviceException {
        ArrayList<TableRowPosts> posts = null;
        try {
            posts = daoPosts.getPostsByGroupID(group_uuid);
        } catch (Exception e) {
            String msg = "Ошибка при получении списка публикаций по ID группы";
            logger.error(msg, e);
            throw new serviceException(msg);
        }
        return posts;
    }
}
