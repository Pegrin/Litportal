package org.wtiger.inno.litportal.web.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.wtiger.inno.litportal.models.rows.TableRowGroups;
import org.wtiger.inno.litportal.models.rows.TableRowPosts;
import org.wtiger.inno.litportal.services.ServiceGroups;
import org.wtiger.inno.litportal.services.ServicePosts;
import org.wtiger.inno.litportal.services.exceptions.serviceException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@WebServlet(urlPatterns = "/posts", loadOnStartup = 1, name = "posts")
public class PostsServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(PostsServlet.class);
    private ServiceGroups serviceGroups;
    private ServicePosts servicePosts;

    @Autowired
    public void setServiceGroups(ServiceGroups serviceGroups) {
        this.serviceGroups = serviceGroups;
    }

    @Autowired
    public void setServicePosts(ServicePosts servicePosts) {
        this.servicePosts = servicePosts;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String group_uuid = (String) req.getParameter("group_uuid");
        try {
            ArrayList<TableRowGroups> groups = serviceGroups.getListOfGroupsByParentID(group_uuid);
            TableRowGroups group = serviceGroups.getObjectById(group_uuid);
            ArrayList<TableRowPosts> posts = servicePosts.getPostsByGroupID(group_uuid);
            req.setAttribute("group", group);
            req.setAttribute("groups", groups);
            req.setAttribute("posts", posts);
        } catch (serviceException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/posts.jsp").forward(req, resp);
    }
}