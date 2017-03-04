package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;

import java.util.List;
import java.util.UUID;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOComments<TR, ID, POSTID, USERID> {
    TR getNewEntity(UUID post_uuid, UUID parent_comment_uuid,
                    String body, UUID user_uuid);

    void deleteAll() throws DBException;

    void persist(TR tr) throws DBException;

    List<TR> findAll() throws DBException;

    TR findByID(ID id) throws DBException;

    List<TR> findByPostID(POSTID id) throws DBException;

    List<TR> findByUserID(USERID id) throws DBException;

    void update(TR tr) throws DBException;

    void delete(TR tr) throws DBException;
}
