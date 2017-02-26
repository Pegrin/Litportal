package org.wtiger.inno.litportal.dbtools;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by olymp on 26.02.2017.
 */
public class PoolConnections {
    private static Logger logger = Logger.getLogger(PoolConnections.class);
    private static String host = "localhost", port = "5432", db = "litportal";
    private static ComboPooledDataSource cpds;

    public static void initialize() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + db + "");
            cpds.setUser("postgres");
            cpds.setPassword("password");
            cpds.setMinPoolSize(4);
            cpds.setAcquireIncrement(2);
            cpds.setMaxPoolSize(100);
        } catch (PropertyVetoException e) {
            logger.error("Не удалось установить соединение с базой, инициализировать пул подключений.", e);
            //Тут ругаемся
        }
    }

    private static ComboPooledDataSource getPool() {
        if (cpds == null) {
            initialize();
        }
        return cpds;
    }

    public static Connection getConnection() {
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

    public static void destroyPool() {
        try {
            cpds.close();
        } catch (SQLException e) {
            logger.warn("Не удалось закрыть пул подключений к базе. Теперь с этим жить.", e);
        }
    }
}
