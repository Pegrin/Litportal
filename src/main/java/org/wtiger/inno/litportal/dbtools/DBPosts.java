package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.models.rows.TRPosts;
import org.wtiger.inno.litportal.models.rows.TRUsers;
import org.wtiger.inno.litportal.models.tables.TPosts;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.UUID;

public class DBPosts extends DBTable<TPosts, TRPosts> {

    public DBPosts(Connection con) {
        super(con, "posts");
    }

    public String createRow(String group_uuid, Date date, String head, String new_body_request, String user_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO " + tName + " " +
                "(group_uuid, date, head, new_body_request, user_uuid) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING post_uuid");
        q.setObject(1, UUID.fromString(group_uuid));
        q.setTimestamp(2, new Timestamp(date.getTime()));
        q.setString(3, head);
        q.setString(4, new_body_request);
        q.setObject(5, UUID.fromString(user_uuid));
        ResultSet set = q.executeQuery();
        if (set.next()) return set.getString("post_uuid");
        else return null;
    }

    @Override
    protected ResultSet getRowByID(String post_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE post_uuid = ?");
        UUID uuid = UUID.fromString(post_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    protected TRPosts getObjectFromRS(ResultSet resultSet) throws SQLException {
        TRPosts post = new TRPosts();
        post.setPost_uuid(resultSet.getString("post_uuid"));
        post.setGroup_uuid(resultSet.getString("group_uuid"));
        post.setDate(resultSet.getTimestamp("date"));
        post.setHead(resultSet.getString("head"));
        post.setBody(resultSet.getString("body"));
        post.setNew_body_request(resultSet.getString("new_body_request"));
        post.setCommit(resultSet.getString("commit"));
        post.setUser_uuid(resultSet.getString("user_uuid"));
        return post;
    }

    @Override
    protected PreparedStatement getFullInsertStatement() throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO posts" +
                " (post_uuid, group_uuid, date, head, body, " +
                "new_body_request, commit, user_uuid)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (post_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TRPosts post, PreparedStatement q) throws SQLException {
        q.setObject(1, UUID.fromString(post.getPost_uuid()));
        q.setObject(2, UUID.fromString(post.getGroup_uuid()));
        q.setTimestamp(3, new Timestamp(post.getDate().getTime()));
        q.setString(4, post.getHead());
        q.setString(5, post.getBody());
        q.setString(6, post.getNew_body_request());
        q.setBoolean(7, Boolean.valueOf(post.getCommit()));
        q.setObject(8, UUID.fromString(post.getUser_uuid()));
        q.addBatch();
    }

    @Override
    public void loadObjsFromDB(TPosts tPosts) throws SQLException {
        tPosts.setListOfRows(new ArrayDeque<>());
        ResultSet resultSet = getRows();
        while (resultSet.next()) {
            TRPosts post = getObjectFromRS(resultSet);
            {
                DBUsers dbUsers = new DBUsers(connection);
                ResultSet set = dbUsers.getRowByID(post.getUser_uuid());
                set.next();
                TRUsers user = new TRUsers();
                user.setUser_uuid(post.getUser_uuid());
                user.setLogin(set.getString("login"));
                user.setPassword(set.getString("password"));
                user.setRole(set.getLong("role"));
                user.setEmail(set.getString("email"));
                user.setVisible_name(set.getString("visible_name"));
                post.setAuthor(user);
            }
            tPosts.getListOfRows().add(post);
        }
    }
}
