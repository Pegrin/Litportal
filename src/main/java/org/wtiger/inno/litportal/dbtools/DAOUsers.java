package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowUsers;

import java.util.ArrayList;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOUsers {
    String createRow(String login, String password, String email, String visible_name) throws DBException;

    TableRowUsers getObjectByLogin(String login) throws DBException;

    int deleteAll() throws DBException;

    void loadObjsToDB(ArrayList<TableRowUsers> objList) throws DBException;

    void loadObjToDB(TableRowUsers tr) throws DBException;

    ArrayList<TableRowUsers> loadObjsFromDB() throws DBException;

    TableRowUsers getObjectByID(String id) throws DBException;
}
