package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.services.client.BuyExcursionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: buy excursion.
 */
public class BuyExcursionCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(BuyExcursionCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response) {

        int excursionId = getIntParameter(request, EXCURSION_ID);
        if (excursionId > 0) {
            BuyExcursionService buyExcursionService = BuyExcursionService.getBuyTicketService();
            int ticketId = getIntParameter(request, TICKET_ID);
            if (ticketId > 0) {
                try {
                    buyExcursionService.buyExcursionForTicket(ticketId, excursionId);
                    request.getRequestDispatcher(SERVLET_NAME + CLIENT_COMMAND).forward(request, response);
                } catch (ServletException | IOException e) {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                }
            }
        }
    }
}


