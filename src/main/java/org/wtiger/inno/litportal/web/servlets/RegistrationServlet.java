package org.wtiger.inno.litportal.web.servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.wtiger.inno.litportal.services.Authenticator;
import org.wtiger.inno.litportal.services.ServiceUsers;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@WebServlet(urlPatterns = "/registration", loadOnStartup = 1, name = "registration")
public class RegistrationServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(RegistrationServlet.class);
    private Authenticator authenticator;
    private ServiceUsers serviceUsers;

    @Autowired
    public void setServiceUsers(ServiceUsers serviceUsers) {
        this.serviceUsers = serviceUsers;
    }

    @Autowired
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (serviceUsers.RegisterNewUser((String) req.getParameter("login"),
                (String) authenticator.sha256PlusSalt(req.getParameter("password")),
                (String) req.getParameter("email"),
                (String) req.getParameter("visible_name"))) {
            resp.sendRedirect("./login");
        } else {
            resp.sendRedirect("./error");
        }
    }
}
