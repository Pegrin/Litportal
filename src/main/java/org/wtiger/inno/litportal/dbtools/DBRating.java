package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.models.rows.TRRating;
import org.wtiger.inno.litportal.models.tables.TRating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@Deprecated
public class DBRating extends DBTable<TRating, TRRating> {

    public DBRating(Connection con) {
        super(con, "rating");
    }

    public int createRow(String post_uuid, String user_uuid, int value) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO " + tName + " " +
                "(post_uuid, user_uuid, value) " +
                "VALUES (?, ?, ?)");
        q.setString(1, post_uuid);
        q.setString(2, user_uuid);
        q.setInt(3, value);
        int result = q.executeUpdate();
        return result;
    }

    @Deprecated
    @Override
    public ResultSet getRowByID(String id) throws SQLException {
        return null;
    }

    @Override
    public TRRating getObjectFromRS(ResultSet resultSet) throws SQLException {
        TRRating rating = new TRRating();
        rating.setUser_uuid(resultSet.getString("user_uuid"));
        rating.setPost_uuid(resultSet.getString("post_uuid"));
        rating.setValue(resultSet.getLong("value"));
        return rating;
    }

    public ResultSet getRowByID(String user_uuid, String post_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE user_uuid = ? AND post_uuid = ?");
        UUID userUuid = UUID.fromString(user_uuid);
        UUID postUuid = UUID.fromString(user_uuid);
        q.setObject(1, userUuid);
        q.setObject(2, postUuid);

        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    public PreparedStatement getFullInsertStatement() throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO rating" +
                " (post_uuid, value, user_uuid)" +
                " VALUES (?, ?, ?) ON CONFLICT (user_uuid, post_uuid) DO NOTHING");
        return q;
    }

    @Override
    public void setParamsForFullInsertStatement(TRRating rating, PreparedStatement q) throws SQLException {
        q.setString(1, rating.getPost_uuid());
        q.setLong(2, rating.getValue());
        q.setString(3, rating.getUser_uuid());
        q.addBatch();
    }

    @Override
    public TRating getObjects(PreparedStatement q) throws SQLException {
        TRating tRating = new TRating();
        tRating.setListOfRows(new ArrayList<TRRating>());
        ResultSet resultSet = getRows();
        while (resultSet.next()) {
            TRRating rating = getObjectFromRS(resultSet);
            tRating.getListOfRows().add(rating);
        }
        return tRating;
    }
}
