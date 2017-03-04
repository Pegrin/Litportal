package org.wtiger.inno.litportal.dbtools.postgres;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class DBUsers extends DBTable<TableRowUsers> implements DAOUsers {
    private Logger logger = Logger.getLogger(DBUsers.class);

    public DBUsers() {
        super("users");
    }

    @Autowired
    public DBUsers(PoolConnections poolConnections) {
        super("users", poolConnections);
    }

    @Override
    public String createRow(String login, String password, String email, String visible_name) throws DBException {
        try (Connection connection = poolConnections.getConnection();
             PreparedStatement q = connection.prepareStatement("INSERT INTO " + tName + " " +
                     "(login, password, email, visible_name) " +
                     "VALUES (?, ?, ?, ?) RETURNING user_uuid")) {
            q.setString(1, login);
            q.setString(2, password);
            q.setString(3, email);
            q.setString(4, visible_name);
            ResultSet set = q.executeQuery();
            if (set.next()) return set.getString("user_uuid");
            else return null;

        } catch (SQLException e) {
            logger.error("Ошибка при создании нового пользователя.", e);
            throw new DBException();
        }
    }

    @Override
    protected ResultSet getRowByID(String user_uuid, Connection connection) throws SQLException {
        ResultSet set;
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE user_uuid = ?");
        UUID uuid = UUID.fromString(user_uuid);
        q.setObject(1, uuid);
        set = q.executeQuery();
        return set;
    }

    @Override
    protected TableRowUsers getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowUsers user = new TableRowUsers();
        user.setUser_uuid(resultSet.getString("user_uuid"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getLong("role"));
        user.setEmail(resultSet.getString("email"));
        user.setVisible_name(resultSet.getString("visible_name"));
        return user;
    }

    @Override
    protected PreparedStatement getFullInsertStatement(Connection connection) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO users" +
                " (user_uuid, login, password, role, email, visible_name)" +
                " VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (user_uuid) DO UPDATE SET " +
                "login = EXCLUDED.login, " +
                "password = EXCLUDED.password, " +
                "role = EXCLUDED.role, " +
                "email = EXCLUDED.email, " +
                "visible_name = EXCLUDED.visible_name");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TableRowUsers user, PreparedStatement q) throws SQLException {
        String useruuid = user.getUser_uuid();
        q.setObject(1, (useruuid == null) ? UUID.randomUUID() : UUID.fromString(useruuid));
        q.setString(2, user.getLogin());
        q.setString(3, user.getPassword());
        q.setLong(4, user.getRole());
        q.setString(5, user.getEmail());
        q.setString(6, user.getVisible_name());
    }

    private ResultSet getRowByLogin(String login, Connection connection) throws SQLException {
        ResultSet set;
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE login = ?");
        q.setString(1, login);
        set = q.executeQuery();

        return set;
    }

    @Override
    public TableRowUsers getObjectByLogin(String login) throws DBException {
        TableRowUsers result = null;
        try (Connection connection = poolConnections.getConnection();
             ResultSet set = getRowByLogin(login, connection)) {
            if (set.next()) result = getObjectFromRS(set);
        } catch (SQLException e) {
            logger.error("Ошибка при получении пользователя по логину.", e);
            throw new DBException();
        }
        return result;
    }

}
