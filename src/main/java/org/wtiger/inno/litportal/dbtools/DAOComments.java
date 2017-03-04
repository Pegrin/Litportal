package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowComments;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOComments {
    String createRow(String post_uuid, String parent_comment_uuid,
                     String body, String user_uuid) throws SQLException;

    int deleteAll() throws DBException;

    void loadObjsToDB(ArrayList<TableRowComments> objList) throws DBException;

    void loadObjToDB(TableRowComments tr) throws DBException;

    ArrayList<TableRowComments> loadObjsFromDB() throws DBException;

    TableRowComments getObjectByID(String id) throws DBException;
}
