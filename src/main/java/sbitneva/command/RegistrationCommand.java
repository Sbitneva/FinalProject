package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.exception.TransactionException;
import sbitneva.services.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    private final String FIRST_NAME_PARAMETER = "first_name";
    private final String LAST_NAME_PARAMETER = "last_name";
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        RegistrationService registrationService = RegistrationService.getRegistrationService();
        try {
            registrationService.register(firstName, lastName, email, password);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
