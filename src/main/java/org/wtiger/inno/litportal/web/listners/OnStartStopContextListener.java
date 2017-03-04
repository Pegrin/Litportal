package org.wtiger.inno.litportal.web.listners;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Controller
public class OnStartStopContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
