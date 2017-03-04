package org.wtiger.inno.litportal.web.listners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.wtiger.inno.litportal.dbtools.PoolConnections;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Controller
public class OnStartStopContextListener implements ServletContextListener {
    private PoolConnections poolConnections;

    @Autowired
    public void setPoolConnections(PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        poolConnections.initialize();
        //System.out.println(poolConnections);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        poolConnections.destroyPool();
    }
}
