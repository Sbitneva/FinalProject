package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.services.client.AddTicketToCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: add ticket to cart.
 */
public class AddTicketToCartCommand extends BasicCommand implements Command {
    private static Logger log = Logger.getLogger(AddTicketToCartCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("AddTicketToCartCommand execution started " + request.getQueryString());
        int page = getPage(request);
        int shipId = getIntParameter(request, SHIP_ID);
        int ticketId = getIntParameter(request, TICKET_ID);

        int userId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);

        if ((shipId > 0) && (ticketId > 0)) {
            AddTicketToCartService addTicketToCartService = AddTicketToCartService.getAddTicketToCartService();
            addTicketToCartService.add(userId, ticketId);
            request.setAttribute(PAGE, page);
            request.setAttribute(SHIP_ID, shipId);
            request.setAttribute(TICKET_ID, ticketId);
            request.getRequestDispatcher(SERVLET_NAME + SHOW_SHIP_COMMAND).forward(request, response);
        }
    }

}
