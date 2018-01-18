package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.Command;
import sbitneva.command.factory.FactoryCommand;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.entity.Client;
import sbitneva.exception.LoginException;
import sbitneva.services.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static Logger log = Logger.getLogger(LoginCommand.class.getName());

    private final static String USER_COMMAND_PATH = "?command=client";
    private final static String SHIP_ADMIN_COMMAND_PATH = "?command=shipAdmin";
    private final static String EMAIL_PARAMETER = "email";
    private final static String PASSWORD_PARAMETER = "password";
    private final static String USER_ID_ATTRIBUTE = "id";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws LoginException {
        log.debug("Login command starts execution");

        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        log.debug("email = " + email + " password = " + password);

        LoginService loginService = LoginService.getLoginService();
        try {
            Client client = loginService.verify(email, password);
            if (client == null) {
                //TODO: add response into the page about wrong input data
                log.debug("redirect to index.jsp because of wrong client input data");
                response.sendRedirect("/index.jsp");
            } else if (client.getShipId() > 0) {
                /*
                log.debug("client is admin of ship with id = " + client.getShipId());
                HttpSession session = request.getSession();
                session.setAttribute("userId", client.getClientId());
                request.getRequestDispatcher(
                        SHIP_ADMIN_COMMAND_PATH + client.getClientId() + "&shipId=" + client.getShipId())
                        .forward(request, response);
                        */
            } else {
                log.debug("client is a service client");
                HttpSession session = request.getSession();
                session.setAttribute(USER_ID_ATTRIBUTE, client.getClientId());
                session.setAttribute("type", SecurityConfiguration.CLIENT_TYPE);
                request.getRequestDispatcher(
                        FactoryCommand.SERVLET_PATH + USER_COMMAND_PATH).forward(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
