package org.wtiger.inno.litportal.servlets;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.dbtools.DBUsers;
import org.wtiger.inno.litportal.dbtools.PoolConnections;
import org.wtiger.inno.litportal.models.rows.TRUsers;
import org.wtiger.inno.litportal.servlets.utils.Authenticator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    public static Logger logger = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBUsers dbUsers = new DBUsers(PoolConnections.getConnection());
        TRUsers user = new TRUsers();
        user.setUser_uuid(null);
        user.setLogin(req.getParameter("login"));
        user.setPassword(Authenticator.sha256PlusSalt(req.getParameter("password")));
        user.setEmail(req.getParameter("email"));
        user.setRole(0L);
        user.setVisible_name(req.getParameter("visible_name"));
        try {
            dbUsers.loadObjToDB(user);
        } catch (SQLException e) {
            logger.error("Не удалось зарегистрировать пользователя: "
                    + user.getLogin() + ", email: " + user.getEmail(), e);
            return;
            //Вывести сообщение пользователю
        }
        resp.sendRedirect("./login");
    }
}
