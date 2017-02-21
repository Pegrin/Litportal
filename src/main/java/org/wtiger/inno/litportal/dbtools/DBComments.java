package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.models.rows.TRComments;
import org.wtiger.inno.litportal.models.tables.TComments;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DBComments extends DBTable<TComments, TRComments> {

    public DBComments(Connection con) {
        super(con, "comments");
    }

    public String createRow(String post_uuid, String parent_comment_uuid,
                            String body, String user_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO comments " +
                "(post_uuid, parent_comment_uuid, body, user_uuid)  " +
                "VALUES (?, ?, ?, ?) RETURNING comment_uuid");
        q.setString(1, post_uuid);
        q.setString(2, parent_comment_uuid);
        q.setString(3, body);
        q.setString(4, user_uuid);
        ResultSet set = q.executeQuery();
        if (set.next()) return set.getString("comment_uuid");
        else return null;
    }

    @Override
    public ResultSet getRowByID(String comment_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE comment_uuid = ?");
        UUID uuid = UUID.fromString(comment_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    public TRComments getObjectFromRS(ResultSet resultSet) throws SQLException {
        TRComments comment = new TRComments();
        comment.setComment_uuid(resultSet.getString("comment_uuid"));
        comment.setPost_uuid(resultSet.getString("post_uuid"));
        comment.setParent_comment_uuid(resultSet.getString("parent_comment_uuid"));
        comment.setBody(resultSet.getString("body"));
        comment.setDate(resultSet.getTimestamp("date"));
        comment.setUser_uuid(resultSet.getString("user_uuid"));
        return comment;
    }

    @Override
    public PreparedStatement getFullInsertStatement() throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO comments" +
                " (comment_uuid, post_uuid, parent_comment_uuid, body, date, user_uuid) " +
                " VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (comment_uuid) DO NOTHING");
        return q;
    }

    @Override
    public void setParamsForFullInsertStatement(TRComments comment, PreparedStatement q) throws SQLException {
        q.setString(1, comment.getComment_uuid());
        q.setString(2, comment.getPost_uuid());
        q.setString(3, comment.getParent_comment_uuid());
        q.setString(4, comment.getBody());
        q.setTimestamp(5, (Timestamp) comment.getDate());
        q.setString(6, comment.getUser_uuid());
        q.addBatch();
    }

    @Override
    public TComments getObjects(PreparedStatement q) throws SQLException {
        TComments comments = new TComments();
        comments.setListOfRows(new ArrayList<TRComments>());
        ResultSet resultSet = getRows();
        while (resultSet.next()) {
            TRComments comment = getObjectFromRS(resultSet);
            comments.getListOfRows().add(comment);
        }
        return comments;
    }

}
