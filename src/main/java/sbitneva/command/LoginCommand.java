package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.User;
import sbitneva.exception.LoginException;
import sbitneva.services.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private final static String USER_COMMAND_PATH = "CruiseServlet?command=users&userId=";
    private final static String SHIP_ADMIN_COMMAND_PATH = "CruiseServlet?command=shipAdmin&action=show&userId=";
    static Logger log = Logger.getLogger(LoginCommand.class.getName());
    private final String EMAIL_PARAMETER = "email";
    private final String PASSWORD_PARAMETER = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws LoginException {

        log.debug("execution");

        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        log.debug("email = " + email + " password = " + password);

        LoginService loginService = LoginService.getLoginService();
        try {
            User user = loginService.verify(email, password);
            if (user == null) {
                log.debug("redirect to index.jsp because of wrong user input data");
                response.sendRedirect("/index.jsp");
            } else if (user.getShipId() > 0) {
                log.debug("user is admin of ship with id = " + user.getShipId());
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                request.getRequestDispatcher(
                        SHIP_ADMIN_COMMAND_PATH + user.getUserId() + "&shipId=" + user.getShipId())
                        .forward(request, response);
            } else {
                log.debug("user is service client");
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getUserId());
                request.getRequestDispatcher(
                        USER_COMMAND_PATH + user.getUserId()).forward(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
