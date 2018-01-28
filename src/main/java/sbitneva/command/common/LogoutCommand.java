package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command: logout.
 */
public class LogoutCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("LogoutCommand execution started");
        HttpSession session = request.getSession();
        removeSessionAttribute(USER_ID_SESSION_ATTRIBUTE, session);
        removeSessionAttribute(USER_TYPE_SESSION_ATTRIBUTE, session);
        response.sendRedirect(MAIN_PAGE);
    }

    /**
     * Remove session attribute.
     *
     * @param attributeName Attribute name
     * @param session HTTP session
     */
    public void removeSessionAttribute(final String attributeName, final HttpSession session) {
        if (session.getAttribute(attributeName) != null) {
            session.removeAttribute(attributeName);
        }
    }
}
