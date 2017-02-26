package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.models.rows.TRGroups;
import org.wtiger.inno.litportal.models.tables.TGroups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DBGroups extends DBTable<TGroups, TRGroups> {

    public DBGroups(Connection con) {
        super(con, "groups");
    }

    public String createRow(String parent_group_uuid, String head, String body) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO groups " +
                "(parent_group_uuid, head, body) " +
                "VALUES (?, ?, ?) RETURNING group_uuid");
        q.setObject(1, UUID.fromString(parent_group_uuid));
        q.setString(2, head);
        q.setString(3, body);
        ResultSet set = q.executeQuery();
        if (set.next()) return set.getString("group_uuid");
        else return null;
    }

    @Override
    protected ResultSet getRowByID(String group_uuid) throws SQLException {
        PreparedStatement q = connection.prepareStatement("SELECT * FROM groups " +
                "WHERE group_uuid = ?");
        UUID uuid = UUID.fromString(group_uuid);
        q.setObject(1, uuid);
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    protected TRGroups getObjectFromRS(ResultSet resultSet) throws SQLException {
        TRGroups group = new TRGroups();
        group.setGroup_uuid(resultSet.getString("group_uuid"));
        group.setParent_group_uuid(resultSet.getString("parent_group_uuid"));
        group.setHead(resultSet.getString("head"));
        group.setBody(resultSet.getString("body"));
        return group;
    }

    @Override
    protected PreparedStatement getFullInsertStatement() throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO groups" +
                " (group_uuid, parent_group_uuid, head, body)" +
                " VALUES (?, ?, ?, ?) ON CONFLICT (group_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TRGroups group, PreparedStatement q) throws SQLException {
        q.setObject(1, UUID.fromString(group.getGroup_uuid()));
        String s = group.getParent_group_uuid();
        q.setObject(2, (s != null) ? UUID.fromString(s) : null);
        q.setString(3, group.getHead());
        q.setString(4, group.getBody());
        q.addBatch();
    }
}