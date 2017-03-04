package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOPosts<TR, ID, GROUPID, USERID> {
    void deleteAll() throws DBException;

    TR getNewEntity(UUID groupUuid, Timestamp date, String head, String newBodyRequest, UUID userUuid);

    void persist(TR tr) throws DBException;

    List<TR> findAll() throws DBException;

    TR findByID(ID id) throws DBException;

    List<TR> findByGroupID(GROUPID id) throws DBException;

    List<TR> findByUserID(USERID id) throws DBException;

    void update(TR tr) throws DBException;

    void delete(TR tr) throws DBException;
}
