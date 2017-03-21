package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.pojo.PostPojo;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServicePosts {
    List<PostPojo> getPostsByGroupID(UUID group_uuid) throws ServiceException;

    PostPojo getPostByID(UUID postUuid) throws ServiceException;

    String TextToHTML(String string);

    List<PostPojo> getPostsByUser(UUID userUuid) throws ServiceException;
}
