package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOGroups<TR, ID> {
    List<TR> findByParentID(UUID id) throws DBException;

    void deleteAll() throws DBException;

    TR getNewEntity(UUID parent_group_uuid, String head, String body);

    void persist(TR tr) throws DBException;

    List<TR> findAll() throws DBException;

    TR findByID(ID id) throws DBException;

    void update(TR tr) throws DBException;

    void delete(TR tr) throws DBException;
}
