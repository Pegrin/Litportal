package org.wtiger.inno.litportal.dbtools.postgres;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.wtiger.inno.litportal.dbtools.DAOGroups;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.rows.TableRowGroups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class DBGroups extends DBTable<TableRowGroups> implements DAOGroups {
    private static Logger logger = Logger.getLogger(DBGroups.class);

    public DBGroups() {
        super("groups");
    }

    @Autowired
    public DBGroups(PoolConnections poolConnections) {
        super("groups", poolConnections);
    }

    @Override
    public String createRow(String parent_group_uuid, String head, String body) throws SQLException {
        PreparedStatement q;
        ResultSet set;
        try (Connection connection = poolConnections.getConnection()) {
            q = connection.prepareStatement("INSERT INTO groups " +
                    "(parent_group_uuid, head, body) " +
                    "VALUES (?, ?, ?) RETURNING group_uuid");
        }
        q.setObject(1, UUID.fromString(parent_group_uuid));
        q.setString(2, head);
        q.setString(3, body);
        set = q.executeQuery();
        if (set.next()) return set.getString("group_uuid");
        else return null;
    }

    @Override
    protected ResultSet getRowByID(String group_uuid, Connection connection) throws SQLException {
        PreparedStatement q;
        if (group_uuid != null) {
            q = connection.prepareStatement("SELECT * FROM groups " +
                    "WHERE group_uuid = ?");

            UUID uuid = UUID.fromString(group_uuid);
            q.setObject(1, uuid);
        } else
            q = connection.prepareStatement("SELECT * FROM groups " +
                    "WHERE group_uuid IS NULL");
        ResultSet set = q.executeQuery();
        return set;
    }

    protected ResultSet getRowsByParentID(String group_uuid, Connection connection) throws SQLException {
        PreparedStatement q;
        if (group_uuid != null) {
            q = connection.prepareStatement("SELECT * FROM groups " +
                    "WHERE parent_group_uuid = ?");
            UUID uuid = UUID.fromString(group_uuid);
            q.setObject(1, uuid);
        } else
            q = connection.prepareStatement("SELECT * FROM groups " +
                    "WHERE parent_group_uuid IS NULL");
        ResultSet set = q.executeQuery();
        return set;
    }

    @Override
    public ArrayList<TableRowGroups> getGroupsByParentID(String group_uuid) throws SQLException {
        ArrayList<TableRowGroups> groups = new ArrayList<>();
        try (Connection connection = poolConnections.getConnection()) {
            try (ResultSet set = getRowsByParentID(group_uuid, connection)) {
                while (set.next()) {
                    groups.add(getObjectFromRS(set));
                }
            }
        }
        return groups;
    }

    @Override
    protected TableRowGroups getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowGroups group = new TableRowGroups();
        group.setGroup_uuid(resultSet.getString("group_uuid"));
        group.setParent_group_uuid(resultSet.getString("parent_group_uuid"));
        group.setHead(resultSet.getString("head"));
        group.setBody(resultSet.getString("body"));
        return group;
    }

    @Override
    protected PreparedStatement getFullInsertStatement(Connection connection) throws SQLException {
        PreparedStatement q = connection.prepareStatement("INSERT INTO groups" +
                " (group_uuid, parent_group_uuid, head, body)" +
                " VALUES (?, ?, ?, ?) ON CONFLICT (group_uuid) DO NOTHING");
        return q;
    }

    @Override
    protected void setParamsForFullInsertStatement(TableRowGroups group, PreparedStatement q) throws SQLException {
        q.setObject(1, UUID.fromString(group.getGroup_uuid()));
        String s = group.getParent_group_uuid();
        q.setObject(2, (s != null) ? UUID.fromString(s) : null);
        q.setString(3, group.getHead());
        q.setString(4, group.getBody());
        q.addBatch();
    }
}
