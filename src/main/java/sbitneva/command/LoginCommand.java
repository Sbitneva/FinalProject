package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.exception.DAOException;
import sbitneva.exception.LoginException;
import sbitneva.services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand implements Command {

    static Logger log = Logger.getLogger(LoginCommand.class.getName());
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, LoginException {

        log.debug("execution");

        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        log.debug("email = " + email + " password = " + password);

        if (email.equals("") || password.equals("")) {
            response.sendRedirect("/index.jsp");
            log.debug("Empty email or password from user");
            return;
        }

        LoginService loginService = LoginService.getLoginService();

        try {
            if (loginService.verify(email, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", loginService.getUserId());
                log.debug(request.getContextPath());
                request.getRequestDispatcher("CruiseServlet?command=users&userId=" + loginService.getUserId()).forward(request, response);

            } else {
                throw new LoginException("Email or Password error");
            }
        } catch (DAOException e) {
            log.error(e.getMessage());
        } catch (SQLException e1) {
            log.error(e1.getMessage());
        }
        //request.setAttribute("errorMessageLogin", "Login incorrect");
        //request.getRequestDispatcher(FactoryCommand.LOGIN).forward(request, response);
    }
}
