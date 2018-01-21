package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.exception.RegistrationException;
import sbitneva.services.common.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class RegistrationCommand implements Command {

    static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    public static StringBuffer errors = new StringBuffer();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("RegistrationCommand execution started");
        String firstName = getParameter(request, FIRST_NAME);
        String lastName = getParameter(request, LAST_NAME);
        String email = getParameter(request,EMAIL);
        String password = getParameter(request,PASSWORD);

        boolean isValid = validateParameters(firstName, lastName, email, password);
        boolean success = false;
        if(isValid) {

            RegistrationService registrationService = RegistrationService.getRegistrationService();

            try {
                int userId = registrationService.register(firstName, lastName, email, password);
                if (userId > 0) {
                    success = true;
                    request.getSession().setAttribute(USER_ID_SESSION_ATTRIBUTE, userId);
                    request.getSession().setAttribute(USER_TYPE_SESSION_ATTRIBUTE, SecurityConfiguration.CLIENT_TYPE);
                    request.getRequestDispatcher(SERVLET_NAME + CLIENT_COMMAND).forward(request, response);
                }
            } catch (RegistrationException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                errors.append(e.getMessage());
            }
        }
        if(!success) {
            request.setAttribute(ERRORS, errors.toString());
            request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
        }

    }

    private String getParameter(HttpServletRequest request, String parameter) {
        String value = new String();
        if(request.getParameter(parameter) != null) {
            value = request.getParameter(parameter);
            if(value.isEmpty()){
                errors.append(parameter + " field must contain a value\n");
            }
        }
        return value;
    }

    private boolean validateParameters(String...params){
        boolean isValid = true;
        for(int i = 0; i < params.length; i++) {
            if(params[i].isEmpty()){
                isValid = false;
            }
        }
        return isValid;
    }
}
