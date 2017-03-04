package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowPosts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOPosts {
    String createRow(String group_uuid, Date date, String head, String new_body_request, String user_uuid) throws SQLException;

    ArrayList<TableRowPosts> getPostsByGroupID(String group_uuid) throws DBException;

    ArrayList<TableRowPosts> loadObjsFromDB() throws DBException;

    int deleteAll() throws DBException;

    void loadObjsToDB(ArrayList<TableRowPosts> objList) throws DBException;

    void loadObjToDB(TableRowPosts tr) throws DBException;

    TableRowPosts getObjectByID(String id) throws DBException;
}
