package org.wtiger.inno.litportal.servlets;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DBPosts;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.tables.TPosts;
import org.wtiger.inno.litportal.servlets.utils.Authenticator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by olymp on 25.02.2017.
 */
@WebServlet("")
public class MainServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(MainServlet.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Authenticator.checkAuth(req, resp)) return;
        DBPosts dbPosts = new DBPosts(PoolConnections.getConnection());
        TPosts posts = new TPosts();
        try {
            dbPosts.loadObjsFromDB(posts);
        } catch (SQLException e) {
            logger.warn("Ошибка при выводе списка главной страницы", e);
            return;
            //тут будем извиняться перед пользователями
        }
        req.setAttribute("posts", posts.getListOfRows());
        req.getRequestDispatcher("/posts.jsp").forward(req, resp);

    }
}
