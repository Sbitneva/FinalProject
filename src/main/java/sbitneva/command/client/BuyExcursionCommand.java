package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.services.client.BuyExcursionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class BuyExcursionCommand implements Command {

    private static Logger log = Logger.getLogger(BuyExcursionCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        int excursionId = getParameter(request, EXCURSION_ID);
        if (excursionId > 0) {
            BuyExcursionService buyExcursionService = BuyExcursionService.getBuyTicketService();
            int ticketId = getParameter(request, TICKET_ID);
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


    private int getParameter(HttpServletRequest request, String parameter) {
        int value = 0;
        if (request.getParameter(parameter) != null) {
            value = Integer.parseInt(request.getParameter(parameter));
        }
        log.debug("value of " + parameter + " : " + value);

        return value;
    }
}


