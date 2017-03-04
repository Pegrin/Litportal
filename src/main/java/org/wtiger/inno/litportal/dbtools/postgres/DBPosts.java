package org.wtiger.inno.litportal.dbtools.postgres;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOPosts;
import org.wtiger.inno.litportal.dbtools.DAOUsers;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.dbtools.exceptions.DBException;
import org.wtiger.inno.litportal.models.rows.TableRowPosts;
import org.wtiger.inno.litportal.models.rows.TableRowUsers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Repository
public class DBPosts extends DBTable<TableRowPosts> implements DAOPosts {
    DAOUsers daoUsers;
    private Logger logger = Logger.getLogger(DBPosts.class);

    public DBPosts() {
        super("posts");
    }

    @Autowired
    public DBPosts(PoolConnections poolConnections) {
        super("posts", poolConnections);
        this.tName = "posts";
        this.poolConnections = poolConnections;
    }

    @Autowired
    public void setDaoUsers(DAOUsers daoUsers) {
        this.daoUsers = daoUsers;
    }

    @Override
    public String createRow(String group_uuid, Date date, String head, String new_body_request, String user_uuid) throws SQLException {
        try (Connection connection = poolConnections.getConnection()) {
            try (PreparedStatement q = connection.prepareStatement("INSERT INTO " + tName + " " +
                    "(group_uuid, date, head, new_body_request, user_uuid) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING post_uuid")) {
                q.setObject(1, UUID.fromString(group_uuid));
                q.setTimestamp(2, new Timestamp(date.getTime()));
                q.setString(3, head);
                q.setString(4, new_body_request);
                q.setObject(5, UUID.fromString(user_uuid));
                try (ResultSet set = q.executeQuery()) {
                    if (set.next()) return set.getString("post_uuid");
                    else return null;
                }
            }
        }
    }

    @Override
    protected ResultSet getRowByID(String post_uuid, Connection connection) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM " + tName + " " +
                "WHERE post_uuid = ?");
        UUID uuid = UUID.fromString(post_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    protected ResultSet getRowsByGroupID(String group, Connection connection) throws SQLException {
        PreparedStatement q;
        if (group != null) {
            q = connection.prepareStatement("SELECT * FROM posts " +
                    "WHERE group_uuid = ?");
            UUID uuid = UUID.fromString(group);
            q.setObject(1, uuid);
        } else
            q = connection.prepareStatement("SELECT * FROM posts " +
                    "WHERE group_uuid IS NULL");
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    public ArrayList<TableRowPosts> getPostsByGroupID(String group_uuid) throws DBException {
        ArrayList<TableRowPosts> posts;
        try (Connection connection = poolConnections.getConnection()) {
            posts = new ArrayList<TableRowPosts>();
            try (ResultSet set = getRowsByGroupID(group_uuid, connection)) {
                while (set.next()) {
                    TableRowPosts post = getObjectFromRS(set);
                    posts.add(post);
                    {
                        TableRowUsers user = daoUsers.getObjectByID(post.getUser_uuid());
                        post.setAuthor(user);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Ошибка при получении списка публикаций по ID группы.", e);
            throw new DBException();
        }
        return posts;
    }


    @Override
    protected TableRowPosts getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowPosts post = new TableRowPosts();
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
    protected PreparedStatement getFullInsertStatement(Connection connection) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO posts" +
                " (post_uuid, group_uuid, date, head, body, " +
                "new_body_request, commit, user_uuid)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (post_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TableRowPosts post, PreparedStatement q) throws SQLException {
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
    public ArrayList<TableRowPosts> loadObjsFromDB() throws DBException {
        ArrayList<TableRowPosts> tPosts;
        try (Connection connection = poolConnections.getConnection()) {
            tPosts = new ArrayList<TableRowPosts>();
            try (ResultSet resultSet = getRows(connection)) {
                while (resultSet.next()) {
                    TableRowPosts post = getObjectFromRS(resultSet);
                    {
                        DAOUsers daoUsers = new DBUsers();
                        TableRowUsers user;
                        user = daoUsers.getObjectByID(post.getUser_uuid());
                        post.setAuthor(user);
                    }
                    tPosts.add(post);
                }
            }
        } catch (SQLException e) {
            logger.error("Ошибка при получении списка публикаций", e);
            throw new DBException();
        }
        return tPosts;
    }
}
