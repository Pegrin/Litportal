package org.wtiger.inno.litportal.services;

import org.wtiger.inno.litportal.models.pojo.GroupPojo;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface ServiceGroups {
    GroupPojo getObjectById(UUID group_uuid) throws ServiceException;

    List<GroupPojo> getListOfGroupsByParentID(UUID group_uuid) throws ServiceException;

    String TextToHTML(String string);
}
