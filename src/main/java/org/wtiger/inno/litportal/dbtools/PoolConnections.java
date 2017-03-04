package org.wtiger.inno.litportal.dbtools;

import java.sql.Connection;

/**
 * Created by olymp on 03.03.2017.
 */
public interface PoolConnections {
    void initialize();

    Connection getConnection();

    void destroyPool();
}
