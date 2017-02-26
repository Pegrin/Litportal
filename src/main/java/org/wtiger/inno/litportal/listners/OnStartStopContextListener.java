package org.wtiger.inno.litportal.listners;

import org.wtiger.inno.litportal.dbtools.PoolConnections;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OnStartStopContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        PoolConnections.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        PoolConnections.destroyPool();
    }
}
