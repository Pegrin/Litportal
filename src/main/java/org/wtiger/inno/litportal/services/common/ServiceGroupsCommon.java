package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOGroups;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.hibernate.GroupsEntity;
import org.wtiger.inno.litportal.models.pojo.GroupPojo;
import org.wtiger.inno.litportal.models.utils.Transformer;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceGroupsCommon implements ServiceGroups {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOGroups<GroupsEntity, UUID> daoGroups;
    private Transformer<GroupsEntity, GroupPojo> groupPojoTransformer;

    @Autowired
    public void setDaoGroups(DAOGroups daoGroups) {
        this.daoGroups = daoGroups;
    }

    @Override
    public GroupPojo getObjectById(UUID group_uuid) throws ServiceException {
        GroupPojo rowGroups = null;
        try {
            rowGroups = groupPojoTransformer.transformFromEntityToPojo(daoGroups.findByID(group_uuid));
        } catch (DBException e) {
            String msg = "Не удалось получить группу по ID";
            logger.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            logger.warn("Ошибка при закрытии DAO групп.", e);
        }

        return rowGroups;
    }


    @Override
    public List<GroupPojo> getListOfGroupsByParentID(UUID group_uuid) throws ServiceException {
        List<GroupPojo> groups = null;
        try {
            groups = daoGroups.findByParentID(group_uuid).stream()
                    .map(groupPojoTransformer::transformFromEntityToPojo)
                    .collect(Collectors.toList());
        } catch (DBException e) {
            String msg = "Не удалось получить список групп по родительскому ID";
            logger.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            logger.warn("Ошибка при закрытии DAO групп.");
        }
        return groups;
    }

    @Override
    public String TextToHTML(String string) {
        String result = string.replace("\n", "<br>");
        return result;
    }

    @Autowired
    public void setGroupPojoTransformer(Transformer<GroupsEntity, GroupPojo> groupPojoTransformer) {
        this.groupPojoTransformer = groupPojoTransformer;
    }
}
