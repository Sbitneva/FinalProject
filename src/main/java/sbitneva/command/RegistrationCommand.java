package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.services.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private final static String REGISTRATION_COMMAND_PATH = "jsp/registration/registration.jsp";
    private final static String AFTER_REGISTRATION_COMMAND_PATH = "/?command=users&userId=";
    private final static String FIRST_NAME_PARAMETER = "first_name";
    private final static String LAST_NAME_PARAMETER = "last_name";
    private final static String EMAIL_PARAMETER = "email";
    private final static String PASSWORD_PARAMETER = "password";
    static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        RegistrationService registrationService = RegistrationService.getRegistrationService();
        try {
            int userId = registrationService.register(firstName, lastName, email, password);
            if (userId > 0) {
                request.setAttribute("userId", userId);
                request.getSession().setAttribute("userId", userId);
                request.getRequestDispatcher(AFTER_REGISTRATION_COMMAND_PATH + userId).forward(request, response);
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        request.getRequestDispatcher(REGISTRATION_COMMAND_PATH).forward(request, response);

    }
}
