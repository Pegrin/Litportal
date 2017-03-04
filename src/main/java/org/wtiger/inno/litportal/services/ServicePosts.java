package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.rows.PostsEntity;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServicePosts {
    List<PostsEntity> getPostsByGroupID(UUID group_uuid) throws serviceException;
}
