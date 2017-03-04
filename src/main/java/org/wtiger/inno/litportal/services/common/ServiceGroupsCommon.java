package org.wtiger.inno.litportal.services.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtiger.inno.litportal.dbtools.DAOGroups;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowGroups;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class ServiceGroupsCommon implements ServiceGroups {
    private static Logger logger = Logger.getLogger(ServiceUsersCommon.class);
    private DAOGroups daoGroups;

    @Autowired
    public void setDaoGroups(DAOGroups daoGroups) {
        this.daoGroups = daoGroups;
    }

    @Override
    public TableRowGroups getObjectById(String group_uuid) throws serviceException {
        TableRowGroups rowGroups = null;
        try {
            rowGroups = daoGroups.getObjectByID(group_uuid);
        } catch (DBException e) {
            String msg = "Не удалось получить группу по ID";
            logger.error(msg, e);
            throw new serviceException(msg);
        } catch (Exception e) {
            logger.warn("Ошибка при закрытии DAO групп.", e);
        }

        return rowGroups;
    }


    @Override
    public ArrayList<TableRowGroups> getListOfGroupsByParentID(String group_uuid) throws serviceException {
        ArrayList<TableRowGroups> groups = null;
        try {
            groups = daoGroups.getGroupsByParentID(group_uuid);
        } catch (SQLException e) {
            String msg = "Не удалось получить список групп по родительскому ID";
            logger.error(msg, e);
            throw new serviceException(msg);
        } catch (Exception e) {
            logger.warn("Ошибка при закрытии DAO групп.");
        }

        return groups;
    }
}
