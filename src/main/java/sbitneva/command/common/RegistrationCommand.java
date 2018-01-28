package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.exception.RegistrationException;
import sbitneva.services.common.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: registration.
 */
public class RegistrationCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    private StringBuffer errors = new StringBuffer();

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("RegistrationCommand execution started");

        String firstName = getStringParameter(request, FIRST_NAME);
        String lastName = getStringParameter(request, LAST_NAME);
        String email = getStringParameter(request, EMAIL);
        String password = getStringParameter(request, PASSWORD);

        boolean isValid = validateParameters(firstName, lastName, email, password);

        boolean success = false;
        if (isValid) {

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
        if (!success) {
            errors.append("Please, fill all requirements fields");
            request.setAttribute(ERRORS, errors.toString());
            request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
        }

    }

    private boolean validateParameters(final String... params) {
        boolean isValid = true;
        for (String param : params) {
            if (param.isEmpty()) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
