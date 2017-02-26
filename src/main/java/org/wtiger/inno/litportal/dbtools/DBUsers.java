package org.wtiger.inno.litportal.dbtools;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.models.rows.TRUsers;
import org.wtiger.inno.litportal.models.tables.TUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBUsers extends DBTable<TUsers, TRUsers> {
    private static Logger logger = Logger.getLogger(DBUsers.class);

    public DBUsers(Connection con) {
        super(con, "users");
    }

    public String createRow(String login, String password, String email, String visible_name) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO " + tName + " " +
                "(login, password, email, visible_name) " +
                "VALUES (?, ?, ?, ?) RETURNING user_uuid");
        q.setString(1, login);
        q.setString(2, password);
        q.setString(3, email);
        q.setString(4, visible_name);
        ResultSet set = q.executeQuery();
        if (set.next()) return set.getString("user_uuid");
        else return null;
    }

    @Override
    protected ResultSet getRowByID(String user_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE user_uuid = ?");
        UUID uuid = UUID.fromString(user_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    protected TRUsers getObjectFromRS(ResultSet resultSet) throws SQLException {
        TRUsers user = new TRUsers();
        user.setUser_uuid(resultSet.getString("user_uuid"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(resultSet.getLong("role"));
        user.setEmail(resultSet.getString("email"));
        user.setVisible_name(resultSet.getString("visible_name"));
        return user;
    }

    @Override
    protected PreparedStatement getFullInsertStatement() throws SQLException {
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
    protected void setParamsForFullInsertStatement(TRUsers user, PreparedStatement q) throws SQLException {
        String useruuid = user.getUser_uuid();
        q.setObject(1, (useruuid == null) ? UUID.randomUUID() : UUID.fromString(useruuid));
        q.setString(2, user.getLogin());
        q.setString(3, user.getPassword());
        q.setLong(4, user.getRole());
        q.setString(5, user.getEmail());
        q.setString(6, user.getVisible_name());
    }

    public ResultSet getRowByLogin(String login) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE login = ?");
        q.setString(1, login);
        ResultSet set = q.executeQuery();
        return set;
    }

    public TRUsers getObjectByLogin(String login) {
        TRUsers result = null;
        try {
            ResultSet set = getRowByLogin(login);
            if (set.next()) result = getObjectFromRS(set);
        } catch (SQLException e) {
            logger.error("Не удалось получить пользователя по логину.", e);
        }
        return result;
    }

}
