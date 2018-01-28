package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.services.client.ShowClientInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Command: show client info.
 */
public class ShowClientInfoCommand extends BasicCommand implements Command {
    private static Logger log = Logger.getLogger(ShowClientInfoCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        boolean success = false;

        ShowClientInfoService showClientInfoService = ShowClientInfoService.getShowClientInfoService();
        int clientId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);
        try {
            Client client = showClientInfoService.getClient(clientId);
            if (client != null) {
                success = true;
                request.setAttribute(CLIENT, client);
                request.getRequestDispatcher(CLIENT_INFO_PAGE).forward(request, response);
            }
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        if (!success) {
            request.setAttribute(ERRORS, "No user with given email and password");
            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
        }
    }
}
