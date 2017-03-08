package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOGroups;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.GroupsEntity;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.exceptions.ServiceException;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceGroupsCommon implements ServiceGroups {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOGroups<GroupsEntity, UUID> daoGroups;

    @Autowired
    public void setDaoGroups(DAOGroups daoGroups) {
        this.daoGroups = daoGroups;
    }

    @Override
    public GroupsEntity getObjectById(UUID group_uuid) throws ServiceException {
        GroupsEntity rowGroups = null;
        try {
            rowGroups = daoGroups.findByID(group_uuid);
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
    public List<GroupsEntity> getListOfGroupsByParentID(UUID group_uuid) throws ServiceException {
        List<GroupsEntity> groups = null;
        try {
            groups = daoGroups.findByParentID(group_uuid);
        } catch (DBException e) {
            String msg = "Не удалось получить список групп по родительскому ID";
            logger.error(msg, e);
            throw new ServiceException(msg);
        } catch (Exception e) {
            logger.warn("Ошибка при закрытии DAO групп.");
        }
        return groups;
    }
}
