package org.wtiger.inno.litportal.servlets;

import org.apache.log4j.Logger;
import org.wtiger.inno.litportal.servlets.utils.Authenticator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olymp on 25.02.2017.
 */
@WebServlet("")
public class MainServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(MainServlet.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!Authenticator.checkAuth(req, resp)) return;
        resp.sendRedirect("./posts");
    }
}
