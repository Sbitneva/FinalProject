package sbitneva.command.common;

import sbitneva.command.Command;
import sbitneva.command.CommandsHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final String MAIN_PAGE = "/index.jsp";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        removeSessionAttribute(CommandsHelper.ID_SESSION_ATTRIBUTE, session);
        removeSessionAttribute(CommandsHelper.ID_SESSION_ATTRIBUTE, session);
        response.sendRedirect(MAIN_PAGE);
    }
    public void removeSessionAttribute(String attributeName, HttpSession session) {
        if(session.getAttribute(attributeName) != null) {
            session.removeAttribute(attributeName);
        }
    }
}
