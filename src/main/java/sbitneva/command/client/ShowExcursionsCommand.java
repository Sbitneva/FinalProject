package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Port;
import sbitneva.services.client.ShowExcursionsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Command: show excursions.
 */
public class ShowExcursionsCommand extends BasicCommand implements Command {
    private static Logger log = Logger.getLogger(ShowExcursionsCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("ShowExcursionsCommand execution started");

        int ticketId = getIntParameter(request, TICKET_ID);
        if (ticketId > 0) {
            ShowExcursionsService showExcursionsService = ShowExcursionsService.getShowExcursionsService();
            ArrayList<Port> ports = showExcursionsService.getExcursions(ticketId);
            if (ports != null) {
                request.setAttribute(PORTS, ports);
                request.setAttribute(TICKET_ID, ticketId);
                request.getRequestDispatcher(EXCURSIONS_PAGE).forward(request, response);
            }
        }
    }
}
