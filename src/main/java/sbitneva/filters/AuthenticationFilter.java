package sbitneva.filters;

import org.apache.log4j.Logger;
import sbitneva.configaration.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter",
        urlPatterns = {"/*"}
)


public class AuthenticationFilter implements Filter {

    private static Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

    private static final String MAIN_PAGE = "/index.jsp";
    private static final String ERROR_404_PAGE = "jsp/errors/404-error.jsp";
    private static final String USER_ID_SESSION_ATTR_NAME = "id";
    private static final String ACCESS_SESSION_ATTR_NAME = "type";
    private static final String COMMAND_PARAMETER_NAME = "command";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.debug("auth filter work");
        boolean errorRedirect = false;
        HttpSession session = ((HttpServletRequest) request).getSession(true);
        if((!(hasAttributes(session)))){
            log.debug("session has no any attributes");
            filterChain.doFilter(request, response);
        }
        else if (isAttributesValid(session)) {
            log.debug("session attributes are full");
            int accessId = Integer.parseInt(session.getAttribute(ACCESS_SESSION_ATTR_NAME).toString());
            String command = getCommandFromRequest(request);
            if(command != null) {
                log.debug("request has command parameter");
                SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();
                if(securityConfiguration.hasCommand(command)) {
                    log.debug("request has right command parameter");
                    if(securityConfiguration.verifyRights(accessId, command)) {
                        log.debug("current response has access rights for requested command execution");
                        filterChain.doFilter(request, response);
                    }
                }
                else{
                    log.debug("request has no command with requested name");
                    errorRedirect = true;
                }
            } else{
                log.debug("request has no command parameter");
                errorRedirect = true;
            }

        } else {
            errorRedirect = true;
            log.debug("session attributes are wrong");
        }
        if(errorRedirect) {

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect(MAIN_PAGE);

        }

    }

    @Override
    public void destroy() {

    }

    private boolean isAttributesValid(HttpSession session) {
        boolean isValid = false;

        if ((session.getAttribute(USER_ID_SESSION_ATTR_NAME) != null) &&
                (session.getAttribute(ACCESS_SESSION_ATTR_NAME) != null)) {

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

    private String getCommandFromRequest(ServletRequest request){
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        if(httpRequest.getParameter(COMMAND_PARAMETER_NAME) != null) {
            return httpRequest.getParameter(COMMAND_PARAMETER_NAME);
        } else {
            return null;
        }
    }

    private boolean hasAttributes(HttpSession session) {
        boolean has = true;

        if ((session.getAttribute(USER_ID_SESSION_ATTR_NAME) == null) &&
                (session.getAttribute(ACCESS_SESSION_ATTR_NAME) == null)) {
            has = false;
        }
        return has;
    }

}
