package org.wtiger.inno.litportal.dbtools;

import org.wtiger.inno.litportal.dbtools.exceptions.DBException;

import java.util.List;

/**
 * Created by olymp on 03.03.2017.
 */
public interface DAOUsers<TR, ID> {
    TR getNewEntity(String login, String password, short role, String email, String visibleName);

    TR getByLogin(String login) throws DBException;

    void deleteAll() throws DBException;

    void persist(TR tr) throws DBException;

    List<TR> findAll() throws DBException;

    TR findByID(ID id) throws DBException;

    void update(TR tr) throws DBException;

    void delete(TR tr) throws DBException;
}
