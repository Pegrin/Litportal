package org.wtiger.inno.litportal.dbtools.Jaxb;

import org.wtiger.inno.litportal.models.jaxb.rows.TableRowJaxbGroups;
import org.wtiger.inno.litportal.models.jaxb.tables.TableJaxbGroups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.UUID;

public class DBJaxbGroups extends DBJaxbTable<TableJaxbGroups, TableRowJaxbGroups> {

    public DBJaxbGroups(Connection con) {
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

    protected ResultSet getRowsByParentID(String group_uuid) throws SQLException {
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

    public TableJaxbGroups getGroupsByParentID(String group_uuid) throws SQLException {
        TableJaxbGroups groups = new TableJaxbGroups();
        groups.setListOfRows(new ArrayDeque<>());
        ResultSet set = getRowsByParentID(group_uuid);
        while (set.next()) {
            groups.getListOfRows().add(getObjectFromRS(set));
        }
        return groups;
    }

    @Override
    protected TableRowJaxbGroups getObjectFromRS(ResultSet resultSet) throws SQLException {
        TableRowJaxbGroups group = new TableRowJaxbGroups();
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
    protected void setParamsForFullInsertStatement(TableRowJaxbGroups group, PreparedStatement q) throws SQLException {
        q.setObject(1, UUID.fromString(group.getGroup_uuid()));
        String s = group.getParent_group_uuid();
        q.setObject(2, (s != null) ? UUID.fromString(s) : null);
        q.setString(3, group.getHead());
        q.setString(4, group.getBody());
        q.addBatch();
    }
}
