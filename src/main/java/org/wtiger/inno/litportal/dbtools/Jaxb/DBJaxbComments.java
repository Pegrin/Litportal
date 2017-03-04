package org.wtiger.inno.litportal.dbtools.Jaxb;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbComments;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbComments;

import java.sql.*;
import java.util.UUID;

public class DBJaxbComments extends DBJaxbTable<TableJaxbComments, TableRowJaxbComments> {

    public DBJaxbComments(Connection con) {
        super(con, "comments");
    }

    public String createRow(String post_uuid, String parent_comment_uuid,
                            String body, String user_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO comments " +
                "(post_uuid, parent_comment_uuid, body, user_uuid)  " +
                "VALUES (?, ?, ?, ?) RETURNING comment_uuid");
        q.setObject(1, UUID.fromString(post_uuid));
        String s = parent_comment_uuid;
        q.setObject(2, (s != null) ? UUID.fromString(s) : null);
        q.setString(3, body);
        q.setObject(4, UUID.fromString(user_uuid));
        ResultSet set = q.executeQuery();
        if (set.next()) return set.getString("comment_uuid");
        else return null;
    }

    @Override
    protected ResultSet getRowByID(String comment_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM comments " +
                "WHERE comment_uuid = ?");
        UUID uuid = UUID.fromString(comment_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    protected TableRowJaxbComments getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowJaxbComments comment = new TableRowJaxbComments();
        comment.setComment_uuid(resultSet.getString("comment_uuid"));
        comment.setPost_uuid(resultSet.getString("post_uuid"));
        comment.setParent_comment_uuid(resultSet.getString("parent_comment_uuid"));
        comment.setBody(resultSet.getString("body"));
        comment.setDate(resultSet.getTimestamp("date"));
        comment.setUser_uuid(resultSet.getString("user_uuid"));
        return comment;
    }

    @Override
    protected PreparedStatement getFullInsertStatement() throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO comments" +
                " (comment_uuid, post_uuid, parent_comment_uuid, body, date, user_uuid) " +
                " VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (comment_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TableRowJaxbComments comment, PreparedStatement q) throws SQLException {
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
