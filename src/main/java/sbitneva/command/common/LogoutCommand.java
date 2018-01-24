package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class LogoutCommand implements Command {

    private static Logger log = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("LogoutCommand execution started");
        HttpSession session = request.getSession();
        removeSessionAttribute(USER_ID_SESSION_ATTRIBUTE, session);
        removeSessionAttribute(USER_TYPE_SESSION_ATTRIBUTE, session);
        response.sendRedirect(MAIN_PAGE);
    }

    public void removeSessionAttribute(String attributeName, HttpSession session) {
        if (session.getAttribute(attributeName) != null) {
            session.removeAttribute(attributeName);
        }
    }
}
