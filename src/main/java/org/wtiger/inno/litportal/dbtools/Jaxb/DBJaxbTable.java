package org.wtiger.inno.litportal.dbtools.Jaxb;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.models.rows.TableRow;
import org.wtiger.inno.litportal.models.tables.Table;

import java.sql.*;
import java.util.ArrayDeque;

abstract public class DBJaxbTable<T extends Table<TR>, TR extends TableRow> {
    public Logger logger = Logger.getLogger(this.getClass());
    protected Connection connection;
    protected String tName;

    DBJaxbTable(Connection con, String tName) {
        connection = con;
        this.tName = tName;
    }

    protected ResultSet getRows() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM " + tName);
    }

    public int deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate("DELETE FROM " + tName);
    }

    public void loadObjsToDB(T t) throws SQLException {
        connection.setAutoCommit(false);
        if (t.getListOfRows() == null || t.getListOfRows().size() == 0) return;
        PreparedStatement q = getFullInsertStatement();
        for (TR tr :
                t.getListOfRows()) {
            setParamsForFullInsertStatement(tr, q);
            q.addBatch();
        }
        q.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void loadObjToDB(TR tr) throws SQLException {
        PreparedStatement q = getFullInsertStatement();
        setParamsForFullInsertStatement(tr, q);
        q.executeUpdate();
    }

    abstract protected ResultSet getRowByID(String id) throws SQLException;

    abstract protected TR getObjectFromRS(ResultSet resultSet) throws SQLException;

    public void loadObjsFromDB(T t) throws SQLException {
        t.setListOfRows(new ArrayDeque<>());
        ResultSet resultSet = getRows();
        while (resultSet.next()) {
            TR tr = getObjectFromRS(resultSet);
            t.getListOfRows().add(tr);
        }
    }

    public TR getObjectByID(String id) throws SQLException {
        ResultSet set = getRowByID(id);
        if (set.next())
            return getObjectFromRS(set);
        else
            return null;
    }

    abstract protected void setParamsForFullInsertStatement(TR tr, PreparedStatement q) throws SQLException;

    abstract protected PreparedStatement getFullInsertStatement() throws SQLException;

    public void close() {
        if (connection != null) try {
            connection.close();
        } catch (SQLException e) {
            logger.warn("Не удалось завершить работу connection:", e);
        }
    }
}
