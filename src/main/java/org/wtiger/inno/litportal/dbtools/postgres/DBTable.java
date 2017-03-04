package org.wtiger.inno.litportal.dbtools.postgres;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRow;

import java.sql.*;
import java.util.ArrayList;

abstract public class DBTable<TR extends TableRow> {
    protected Logger logger = Logger.getLogger(this.getClass());
    protected PoolConnections poolConnections;
    protected String tName;

    DBTable(String tName) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext("org/wtiger/inno/litportal/");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans_clone.xml");
        PoolConnections poolConnections = (PoolConnections) context.getBean("poolConnections");
        this.poolConnections = poolConnections;
    }

    DBTable(String tName, PoolConnections poolConnections) {
        this.tName = tName;
        this.poolConnections = poolConnections;
    }

    protected ResultSet getRows(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM " + tName);
        return set;
    }

    public int deleteAll() throws DBException {
        try (Connection connection = poolConnections.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate("DELETE FROM " + tName);
        } catch (SQLException e) {
            logger.error("Ошибка при попытке удалить все строки из базы.", e);
            throw new DBException();
        }
    }

    public void loadObjsToDB(ArrayList<TR> objList) throws DBException {
        try (Connection connection = poolConnections.getConnection()) {
            connection.setAutoCommit(false);
            if (objList.size() == 0) return;
            try (PreparedStatement q = getFullInsertStatement(connection)) {
                for (TR tr :
                        objList) {
                    setParamsForFullInsertStatement(tr, q);
                    q.addBatch();
                }
                q.executeBatch();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("Ошибка при формировании списка объектов из базы.", e);
            throw new DBException();
        }
    }

    public void loadObjToDB(TR tr) throws DBException {
        try (Connection connection = poolConnections.getConnection();
             PreparedStatement q = getFullInsertStatement(connection)) {
            setParamsForFullInsertStatement(tr, q);
            q.executeUpdate();
        } catch (SQLException e) {
            logger.error("Ошибка при загрузке объекта в базу", e);
            throw new DBException();
        }
    }

    abstract protected ResultSet getRowByID(String id, Connection connection) throws SQLException;

    abstract protected TR getObjectFromRS(ResultSet resultSet) throws SQLException;

    public ArrayList<TR> loadObjsFromDB() throws DBException {
        ArrayList<TR> arrayList = new ArrayList<TR>(8);
        try (Connection connection = poolConnections.getConnection();
             ResultSet resultSet = getRows(connection)) {
            while (resultSet.next()) {
                arrayList.add(getObjectFromRS(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Ошибка при формировании списка объект из базы.", e);
            throw new DBException();
        }
        return arrayList;
    }

    public TR getObjectByID(String id) throws DBException {
        try (Connection connection = poolConnections.getConnection();
             ResultSet set = getRowByID(id, connection)) {
            if (set.next())
                return getObjectFromRS(set);
            else
                return null;
        } catch (SQLException e) {
            logger.error("Ошибка получения объекта из базы по ID.", e);
            throw new DBException();
        }
    }

    abstract protected void setParamsForFullInsertStatement(TR tr, PreparedStatement q) throws SQLException;

    abstract protected PreparedStatement getFullInsertStatement(Connection connection) throws SQLException;
}
