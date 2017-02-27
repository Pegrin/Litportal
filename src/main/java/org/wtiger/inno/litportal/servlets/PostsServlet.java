package org.wtiger.inno.litportal.servlets;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DBGroups;
import org.wtiger.inno.litportal.dbtools.DBPosts;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.rows.TRGroups;
import org.wtiger.inno.litportal.models.tables.TGroups;
import org.wtiger.inno.litportal.models.tables.TPosts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/posts")
public class PostsServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(PostsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String group_uuid = (String) req.getParameter("group_uuid");
            DBGroups dbGroups = new DBGroups(PoolConnections.getConnection());
            TGroups groups = dbGroups.getGroupsByParentID(group_uuid);
            TRGroups group = dbGroups.getObjectByID(group_uuid);
            dbGroups.close();
            DBPosts dbPosts = new DBPosts(PoolConnections.getConnection());
            TPosts posts = dbPosts.getPostsByGroupID(group_uuid);
            dbPosts.close();
            req.setAttribute("group", group);
            req.setAttribute("groups", groups.getListOfRows());
            req.setAttribute("posts", posts.getListOfRows());
            req.getRequestDispatcher("/posts.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.warn("Не удалось отобразить список групп и произведений.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
