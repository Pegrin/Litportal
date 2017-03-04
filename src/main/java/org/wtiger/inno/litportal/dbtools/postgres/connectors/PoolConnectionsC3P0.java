package org.wtiger.inno.litportal.dbtools.postgres.connectors;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.wtiger.inno.litportal.dbtools.PoolConnections;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by olymp on 26.02.2017.
 */
@Component("poolConnections")
@Scope(value = "singleton")
public class PoolConnectionsC3P0 implements PoolConnections {
    private Logger logger = Logger.getLogger(PoolConnectionsC3P0.class);
    private String host = "localhost", port = "5432", db = "litportal";
    private ComboPooledDataSource cpds;

    @Override
    public synchronized void initialize() {
        if (cpds != null) return;
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + db + "");
            cpds.setUser("postgres");
            cpds.setPassword("password");
            cpds.setMinPoolSize(6);
            cpds.setAcquireIncrement(6);
            cpds.setMaxPoolSize(100);
        } catch (PropertyVetoException e) {
            logger.error("Не удалось установить соединение с базой, инициализировать пул подключений.", e);
            //Тут ругаемся
        }
    }

    private ComboPooledDataSource getPool() {
        if (cpds == null) {
            initialize();
        }
        return cpds;
    }

    @Override
    public Connection getConnection() {
        ComboPooledDataSource cpds = getPool();
        Connection result;
        if (cpds == null) result = null;
        else try {
            result = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    public void destroyPool() {
        try {
            cpds.close();
        } catch (SQLException e) {
            logger.warn("Не удалось закрыть пул подключений к базе. Теперь с этим жить.", e);
        }
    }
}
