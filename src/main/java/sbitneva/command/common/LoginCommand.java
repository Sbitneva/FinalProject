package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.entity.Client;
import sbitneva.exception.LoginException;
import sbitneva.services.common.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private static Logger log = Logger.getLogger(LoginCommand.class.getName());
    private StringBuffer errors = new StringBuffer();
    private boolean isFullRequest = false;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws LoginException {
        log.debug("Login command starts execution");
        String email = request.getParameter(CommandsHelper.EMAIL);
        String password = request.getParameter(CommandsHelper.PASSWORD);

        if(responseDataVerification(email, password)) {
            LoginService loginService = LoginService.getLoginService();
            try {
                Client user = loginService.getUser(email, password);
                int userType = getUserType(user);
                switch (userType) {
                    case SecurityConfiguration.CLIENT_TYPE :
                        isFullRequest = true;
                        request.getSession().setAttribute(CommandsHelper.USER_ID_SESSION_ATTRIBUTE, user.getClientId());
                        request.getSession().setAttribute(
                                CommandsHelper.USER_TYPE_SESSION_ATTRIBUTE, SecurityConfiguration.CLIENT_TYPE);
                        request.getRequestDispatcher(
                                CommandsHelper.SERVLET_NAME + CommandsHelper.CLIENT_COMMAND).
                                forward(request, response);
                        break;
                    case SecurityConfiguration.SHIP_ADMIN_TYPE:
                        isFullRequest = true;
                        request.getSession().setAttribute(CommandsHelper.USER_ID_SESSION_ATTRIBUTE, user.getClientId());
                        request.getSession().setAttribute(
                                CommandsHelper.USER_TYPE_SESSION_ATTRIBUTE, SecurityConfiguration.SHIP_ADMIN_TYPE);
                        request.getRequestDispatcher(
                                CommandsHelper.SERVLET_NAME +  CommandsHelper.SHIP_ADMIN_COMMAND).
                                forward(request, response);
                        break;
                    default :
                        errors.append("Service has no users with given login and password");
                }
            } catch (IOException | ServletException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        if(!isFullRequest) {
            request.setAttribute(CommandsHelper.ERRORS, errors.toString());
            try{
                request.getRequestDispatcher(CommandsHelper.MAIN_PAGE).forward(request, response);
            } catch (ServletException | IOException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
    }

    private boolean responseDataVerification(String email, String password){
        boolean result = false;
        if((email != null) && (password != null)) {
            if (email.isEmpty()) {
                errors.append("Enter email.\n");
                log.warn("email parameter has an empty value");
            }
            if (password.isEmpty()) {
                errors.append("Enter password.\n");
                log.warn("password parameter has an empty value");
            }
            if (!email.isEmpty() && !password.isEmpty()) {
                result = true;
            } else {
                errors.append("Enter email and password");
            }
        }
        return result;
    }

    private int getUserType(Client user) {
        int typeId = 0;
        if(user != null) {
            if(user.getShipId() > 0) {
                return SecurityConfiguration.SHIP_ADMIN_TYPE;
            } else if(user.getShipId() == 0) {
                return SecurityConfiguration.CLIENT_TYPE;
            }
        }
        return typeId;
    }
}
