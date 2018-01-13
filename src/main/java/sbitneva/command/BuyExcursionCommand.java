package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.Port;
import sbitneva.services.BuyExcursionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class BuyExcursionCommand implements Command {
    static Logger log = Logger.getLogger(BuyExcursionCommand.class.getName());

    private final String SELECT_PATH = "jsp/client/buy-excursion.jsp";
    private final String AFTER_BUY_PATH = "jsp/client/client-page.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution");
        int userId;
        int ticketId;
        if (request.getSession().getAttribute("userId") != null) {
            userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
            log.debug("userId = " + userId);
            if (request.getParameter("ticketId") == null) {
                return;
            } else {
                ticketId = Integer.parseInt(request.getParameter("ticketId"));
            }
        } else {
            return;
        }
        String actionCommand = request.getParameter("action");
        if (actionCommand.equals("select")) {
            selectCommand(userId, ticketId, request, response);
        } else if (actionCommand.equals("buy")) {
            buyCommand(userId, ticketId, request, response);
        }

    }

    private void selectCommand(int userId, int ticketId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("select command");
        BuyExcursionService excursionService = BuyExcursionService.getBuyTicketService();
        ArrayList<Port> ports = excursionService.getExcursionsForPurchase(ticketId);
        request.setAttribute("ports", ports);
        request.setAttribute("ticketId", ticketId);
        request.getSession().setAttribute("userId", userId);
        try {
            request.getRequestDispatcher(SELECT_PATH).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void buyCommand(int userId, int ticketId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("buy command");
        BuyExcursionService excursionService = BuyExcursionService.getBuyTicketService();
        int excursionId = Integer.parseInt(request.getParameter("excursionId"));

        if (excursionId > 0) {
            excursionService.buyExcursionForTicket(ticketId, excursionId);
            request.getSession().setAttribute("userId", userId);
            try {
                request.getRequestDispatcher("CruiseServlet?command=users&userId=" + userId).forward(request, response);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

    }
}
