package sbitneva.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RegistrationCommand implements Command {

    static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    private final String FIRST_NAME_PARAMETER = "first_name";
    private final String LAST_NAME_PARAMETER = "first_name";
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        if (Objects.isNull(firstName) || Objects.isNull(lastName) ||
                Objects.isNull(password) || Objects.isNull(email)) {
            request.setAttribute("errorMessage", "All fields must been not null");
            request.getRequestDispatcher(FactoryCommand.REGISTRATION).forward(request, response);
            return;
        }
        /*

        RegisterService registerService = RegisterService.getRegisterService();

        try {
            registerService.register(firstName, lastName, email, password);
            request.getRequestDispatcher("/").forward(request, response);
        } catch (RegistrationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(FactoryCommand.REGISTER).forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }
}
