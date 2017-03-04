package org.wtiger.inno.litportal.web.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.wtiger.inno.litportal.services.Authenticator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@WebFilter(servletNames = {"AdminServlet", "PersonalCabinetServlet", "MainServlet"})
public class MainFilter implements Filter {
    private Authenticator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Autowired
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (authenticator.checkAuth(false, (HttpServletRequest) req, (HttpServletResponse) resp)) {
            chain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).sendRedirect("./posts");
        }
    }

    @Override
    public void destroy() {

    }
}
