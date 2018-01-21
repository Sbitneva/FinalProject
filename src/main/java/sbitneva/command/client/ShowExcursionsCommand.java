package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.TICKET_ID;

public class ShowExcursionsCommand implements Command {
    private static Logger log = Logger.getLogger(ShowExcursionsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowExcursionsCommand execution started");
        int ticketId = getTicketId(request);
        if (ticketId > 0) {

        }
    }

    private int getTicketId(HttpServletRequest request) {
        int ticketId = 0;
        if (request.getParameter(TICKET_ID) != null) {
            ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
        }
        log.debug(ticketId);
        return ticketId;
    }
}
