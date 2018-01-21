package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.entity.Excursion;
import sbitneva.entity.Port;
import sbitneva.services.client.ShowExcutsionsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static sbitneva.command.CommandsHelper.*;

public class ShowExcursionsCommand implements Command {
    private static Logger log = Logger.getLogger(ShowExcursionsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowExcursionsCommand execution started");
        int ticketId = getTicketId(request);
        if (ticketId > 0) {
            ShowExcutsionsService showExcutsionsService = ShowExcutsionsService.getShowExcutsionsService();
            ArrayList<Port> ports = showExcutsionsService.getExcursions(ticketId);
            if(ports != null){
                request.setAttribute(PORTS, ports);
                request.getRequestDispatcher(EXCURSIONS_PAGE).forward(request, response);
            }
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
