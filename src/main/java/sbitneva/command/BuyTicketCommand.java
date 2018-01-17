package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ShipDao;
import sbitneva.entity.Ship;
import sbitneva.services.BuyTicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class BuyTicketCommand implements Command {

    private final static String SHOW_SHIPS_ACTION = "showShips";
    private final static String SHOW_TICKETS_ACTION = "showTickets";
    private final static String BUY_TICKET_ACTION = "buyTicket";
    private final static String ACTION = "action";

    private final static String SHIP_ATTRIBUTE = "ship";
    private final static String SHIPS_ATTRIBUTE = "ships";
    private final static String SHIP_ID_ATTRIBUTE = "shipId";
    private final static String USER_ID_ATTRIBUTE = "userId";
    private final static String TICKET_ID_ATTRIBUTE = "ticketId";

    private static final String SHOW_SHIPS_PAGE= "jsp/client/ships.jsp";
    private final static String BUY_TICKETS_PAGE = "jsp/client/buy-ticket.jsp";
    private static final String CLIENT_PAGE = "jsp/client/client-page.jsp";
    static Logger log = Logger.getLogger(BuyTicketCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution");
        log.debug("userId = " + request.getSession().getAttribute(USER_ID_ATTRIBUTE));
        log.debug("action = " + request.getParameter(ACTION));

        if(!request.getParameter(ACTION).isEmpty()) {
            String action = request.getParameter(ACTION);
            switch (action) {
                case SHOW_SHIPS_ACTION :
                    showShipsAction(request, response);
                    break;
                case SHOW_TICKETS_ACTION :
                    showTicketsAction(request, response);
                    break;
                case BUY_TICKET_ACTION :
                    buyTicketAction(request, response);
                    break;
                default:
                    break;
            }


        } else {
            //TODO:redirect to error page with message
        }

    }
    //action for "Buy new Ticket" button on client-page
    private void showShipsAction(HttpServletRequest request, HttpServletResponse response){
        log.debug("showShipsAction");
        try {
            ArrayList<Ship> ships = getShips();
            request.setAttribute(SHIPS_ATTRIBUTE, ships);
            request.getRequestDispatcher(SHOW_SHIPS_PAGE).forward(request, response);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    //action for "choose cruise" on ships page
    private void showTicketsAction(HttpServletRequest request, HttpServletResponse response){
        log.debug("showTicketsAction");
        BuyTicketService buyTicketService = BuyTicketService.getBuyTicketService();
        try{
            if(!request.getParameter(SHIP_ID_ATTRIBUTE).isEmpty()) {
                int shipId = Integer.parseInt(request.getParameter(SHIP_ID_ATTRIBUTE));
                Ship ship = buyTicketService.getShipTicketsForPurchase(shipId);
                request.setAttribute(SHIP_ATTRIBUTE, ship);
                request.getRequestDispatcher(BUY_TICKETS_PAGE).forward(request, response);

            } else{
                response.sendRedirect(CLIENT_PAGE);
            }
        } catch( Exception e) {

        }
    }
    //action for "buy" button on tickets page
    private void buyTicketAction(HttpServletRequest request, HttpServletResponse response){
        log.debug("buy ticket action");
        try {
            BuyTicketService buyTicketService = BuyTicketService.getBuyTicketService();
            int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_ATTRIBUTE).toString());
            if (request.getParameter(TICKET_ID_ATTRIBUTE) != null) {
                int ticketId = Integer.parseInt(request.getParameter(TICKET_ID_ATTRIBUTE));
                if (buyTicketService.verify(userId)) {
                    buyTicketService.buySelectedTicket(userId, ticketId);
                    request.getRequestDispatcher("?command=users&userId=" + userId).
                            forward(request, response);
                    return;
                }
            }

            if (buyTicketService.verify(userId)) {
                ArrayList<Ship> ships = getShips();
                request.getSession().setAttribute(USER_ID_ATTRIBUTE, userId);
                request.setAttribute(SHIPS_ATTRIBUTE, ships);
                request.getRequestDispatcher(BUY_TICKETS_PAGE).forward(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    private ArrayList<Ship> getShips() {
        BuyTicketService buyTicketService = BuyTicketService.getBuyTicketService();
        return buyTicketService.getTicketsForPurchase();
    }


}
