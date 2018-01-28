package sbitneva.filters;

import org.apache.log4j.Logger;
import sbitneva.configaration.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication filter.
 */
@WebFilter(filterName = "AuthenticationFilter",
        urlPatterns = {"/*"}
)

public class AuthenticationFilter implements Filter {

    private static Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

    public static final String MAIN_PAGE = "/index.jsp";
    public static final String USER_ID_SESSION_ATTR_NAME = "id";
    public static final String ACCESS_SESSION_ATTR_NAME = "type";
    public static final String COMMAND_PARAMETER_NAME = "command";

    private SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();

    @Override
    public void init(final FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {

        log.debug("auth filter starts");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession session = httpServletRequest.getSession(true);

        String command = getCommandFromRequest(request);

        if (securityConfiguration.isCommandForNotAuth(command) || command == null) {
            if ((request).getParameter("language") != null) {
                httpServletRequest.getRequestDispatcher(MAIN_PAGE).forward(request, response);
            }
            filterChain.doFilter(request, response);
        } else {
            if (isAttributesValid(session)) {
                log.debug("session attributes are full");
                int accessId = getAccessAttributeFromSession(session);
                SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();

                if (securityConfiguration.hasCommand(command)) {
                    if (securityConfiguration.verifyRights(accessId, command)) {
                        log.debug("current response has access rights for requested command execution");
                        filterChain.doFilter(request, response);
                        log.debug("command goes through the filter");
                    } else {
                        httpServletRequest.getRequestDispatcher(MAIN_PAGE).forward(request, response);
                    }
                }
            } else {
                httpServletRequest.getRequestDispatcher(MAIN_PAGE).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isAttributesValid(final HttpSession session) {
        boolean isValid = false;

        if ((session.getAttribute(USER_ID_SESSION_ATTR_NAME) != null)
                && (session.getAttribute(ACCESS_SESSION_ATTR_NAME) != null)) {
            try {
                int id = Integer.parseInt(session.getAttribute(USER_ID_SESSION_ATTR_NAME).toString());
                int access = Integer.parseInt(session.getAttribute(ACCESS_SESSION_ATTR_NAME).toString());
                if ((id > 0) && (access > 0) && (access < 4)) {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                log.error("session parameters has wrong data");
            }
        }
        return isValid;
    }

    private String getCommandFromRequest(final ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getParameter(COMMAND_PARAMETER_NAME) != null) {
            return httpRequest.getParameter(COMMAND_PARAMETER_NAME);
        } else {
            return null;
        }
    }

    private int getAccessAttributeFromSession(final HttpSession session) {
        int access = 0;
        if (session.getAttribute(ACCESS_SESSION_ATTR_NAME) != null) {
            try {
                access = Integer.parseInt(session.getAttribute(ACCESS_SESSION_ATTR_NAME).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            }
        }
        return access;
    }

}
