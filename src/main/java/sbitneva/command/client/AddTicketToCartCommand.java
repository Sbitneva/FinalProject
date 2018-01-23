package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.services.client.AddTicketToCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class AddTicketToCartCommand implements Command {
    private static Logger log = Logger.getLogger(AddTicketToCartCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("AddTicketToCartCommand execution started " + request.getQueryString());
        int page = getPageAttribute(request);
        int shipId = getParameter(request, SHIP_ID);
        int ticketId = getParameter(request, TICKET_ID);
        int userId = CommandsHelper.getUserId(request);
        if ((shipId > 0) && (ticketId > 0)) {
            AddTicketToCartService addTicketToCartService = AddTicketToCartService.getAddTicketToCartService();
            addTicketToCartService.add(userId, ticketId);
            //TODO: add ticket to db
            request.setAttribute(PAGE, page);
            request.setAttribute(SHIP_ID, shipId);
            request.setAttribute(TICKET_ID, ticketId);
            request.getRequestDispatcher(SERVLET_NAME + SHOW_SHIP_COMMAND).forward(request, response);
        }
    }

    private int getPageAttribute(HttpServletRequest request) {
        int page = 1;


        if (request.getParameter(PAGE) != null) {
            try {
                page = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {

            }
        }
        return page;
    }

    private int getParameter(HttpServletRequest request, String parameter) {
        int value = 0;
        if (request.getParameter(parameter) != null) {
            try {
                value = Integer.parseInt(request.getParameter(parameter));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return value;
    }
}
