package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;
import sbitneva.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserCommand implements Command {

    private static Logger log = Logger.getLogger(UserCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution");

        final String USER_COMMAND_PATH = "jsp/client/client-page.jsp";
        final String USER_ATTRIBUTE = "user";
        final String USER_PARAMETER = "userId";

        int userId = Integer.parseInt(request.getParameter(USER_PARAMETER));
        UserService userService = UserService.getUserService();

        try {
            User user = userService.verify(userId);
            if (user != null) {
                request.setAttribute(USER_ATTRIBUTE, user);
                request.getRequestDispatcher(USER_COMMAND_PATH).forward(request, response);
            }

        } catch (SQLException | DAOException e) {
            log.error(e.getMessage());
        }
    }
}
