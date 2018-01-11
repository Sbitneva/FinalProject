package sbitneva.filters;

import sbitneva.authentication.Authentication;
import sbitneva.configaration.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SecirityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SecurityConfiguration configuration = SecurityConfiguration.getInstance();
        String command = getStringCommand(request.getRequestURI(), configuration.getEndPoints());
        String role = configuration.security(command);
        if ("ALL".equals(role)) {
            request.setAttribute("command", command);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (("AUTH".equals(role))
                && Authentication.isUserLogIn(request.getSession())) {
            request.setAttribute("command", command);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (("AUTH".equals(role)
                && !Authentication.isUserLogIn(request.getSession()))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    public void destroy() {
    }

    private String getStringCommand(String URI, Set<String> endPoints) {
        for (String endPoint : endPoints) {
            if (URI.contains(endPoint))
                return endPoint;
        }
        return null;
    }
}
