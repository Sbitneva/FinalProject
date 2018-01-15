package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.services.BuyTicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class BuyTicketCommand implements Command {

    private final static String SHIPS_ATTRIBUTE = "ships";
    private final static String BUY_TICKETS_PAGE = "jsp/client/buy-ticket.jsp";
    static Logger log = Logger.getLogger(BuyTicketCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution");
        log.debug(request.getSession().getAttribute("userId"));

        BuyTicketService buyTicketService = BuyTicketService.getBuyTicketService();
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        if (request.getParameter("ticketId") != null) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            if (buyTicketService.verify(userId)) {
                buyTicketService.buySelectedTicket(userId, ticketId);
                request.getRequestDispatcher("CruiseServlet?command=users&userId=" + userId).
                        forward(request, response);
                return;
            }
        }

        if (buyTicketService.verify(userId)) {
            ArrayList<Ship> ships = new ArrayList<>();
            ships = buyTicketService.getTicketsForPurchase();
            request.setAttribute(SHIPS_ATTRIBUTE, ships);
            request.getSession().setAttribute("userId", userId);
            request.getRequestDispatcher(BUY_TICKETS_PAGE).forward(request, response);
        }

    }
}
