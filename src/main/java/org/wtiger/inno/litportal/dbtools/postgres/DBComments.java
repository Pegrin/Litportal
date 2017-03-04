package org.wtiger.inno.litportal.dbtools.postgres;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOComments;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.rows.TableRowComments;

import java.sql.*;
import java.util.UUID;

@Repository
public class DBComments extends DBTable<TableRowComments> implements DAOComments {
    private Logger logger = Logger.getLogger(DBComments.class);

    public DBComments() {
        super("comments");
    }

    @Autowired
    public DBComments(PoolConnections poolConnections) {
        super("comments", poolConnections);
    }

    @Override
    public String createRow(String post_uuid, String parent_comment_uuid,
                            String body, String user_uuid) throws SQLException {
        try (Connection connection = poolConnections.getConnection()) {
            try (PreparedStatement q = connection.prepareStatement("INSERT INTO comments " +
                    "(post_uuid, parent_comment_uuid, body, user_uuid)  " +
                    "VALUES (?, ?, ?, ?) RETURNING comment_uuid")) {
                q.setObject(1, UUID.fromString(post_uuid));
                String s = parent_comment_uuid;
                q.setObject(2, (s != null) ? UUID.fromString(s) : null);
                q.setString(3, body);
                q.setObject(4, UUID.fromString(user_uuid));
                try (ResultSet set = q.executeQuery()) {
                    if (set.next()) return set.getString("comment_uuid");
                    else return null;
                }
            }
        }
    }

    @Override
    protected ResultSet getRowByID(String comment_uuid, Connection connection) throws SQLException {

        PreparedStatement q = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE comment_uuid = ?");
        UUID uuid = UUID.fromString(comment_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;

    }

    @Override
    protected TableRowComments getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowComments comment = new TableRowComments(
                resultSet.getString("comment_uuid"),
                resultSet.getString("post_uuid"),
                resultSet.getString("parent_comment_uuid"),
                resultSet.getString("body"),
                resultSet.getTimestamp("date"),
                resultSet.getString("user_uuid"));
        return comment;
    }

    @Override
    protected PreparedStatement getFullInsertStatement(Connection connection) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO comments" +
                " (comment_uuid, post_uuid, parent_comment_uuid, body, date, user_uuid) " +
                " VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (comment_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TableRowComments comment, PreparedStatement q) throws SQLException {
        q.setObject(1, UUID.fromString(comment.getComment_uuid()));
        q.setObject(2, UUID.fromString(comment.getPost_uuid()));
        String s = comment.getParent_comment_uuid();
        q.setObject(3, (s != null) ? UUID.fromString(s) : null);
        q.setString(4, comment.getBody());
        q.setTimestamp(5, new Timestamp(comment.getDate().getTime()));
        q.setObject(6, UUID.fromString(comment.getUser_uuid()));
        q.addBatch();
    }
}
