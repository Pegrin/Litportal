package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowGroups;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOGroups {
    String createRow(String parent_group_uuid, String head, String body) throws SQLException;

    ArrayList<TableRowGroups> getGroupsByParentID(String group_uuid) throws SQLException;

    int deleteAll() throws DBException;

    void loadObjsToDB(ArrayList<TableRowGroups> objList) throws DBException;

    void loadObjToDB(TableRowGroups tr) throws DBException;

    ArrayList<TableRowGroups> loadObjsFromDB() throws DBException;

    TableRowGroups getObjectByID(String id) throws DBException;
}
