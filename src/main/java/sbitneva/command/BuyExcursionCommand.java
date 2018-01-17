package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.Port;
import sbitneva.services.BuyExcursionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class BuyExcursionCommand implements Command {
    private final static String USER_COMMAND_PATH = "?command=users&userId=";
    private final static String SELECT_PATH = "jsp/client/buy-excursion.jsp";
    private final static String TICKET_ID_ATTRIBUTE = "ticketId";
    private final static String USER_ID_ATTRIBUTE = "userId";
    private final static String PORTS_ATTRIBUTE = "ports";
    private final static String ACTION_ATTRIBUTE = "action";
    private final static String EXCURSION_ATTRIBUTE = "excursionId";
    private final static String SELECT_ACTION = "select";
    private final static String BUY_ACTION = "buy";
    private static Logger log = Logger.getLogger(BuyExcursionCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution");
        int userId;
        int ticketId;
        if (request.getSession().getAttribute(USER_ID_ATTRIBUTE) != null) {
            userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_ATTRIBUTE).toString());
            log.debug("userId = " + userId);
            if (request.getParameter(TICKET_ID_ATTRIBUTE) == null) {
                return;
            } else {
                ticketId = Integer.parseInt(request.getParameter(TICKET_ID_ATTRIBUTE));
            }
        } else {
            return;
        }
        String actionCommand = request.getParameter(ACTION_ATTRIBUTE);
        if (actionCommand.equals(SELECT_ACTION)) {
            selectCommand(userId, ticketId, request, response);
        } else if (actionCommand.equals(BUY_ACTION)) {
            buyCommand(userId, ticketId, request, response);
        }

    }

    private void selectCommand(int userId, int ticketId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("select command");
        BuyExcursionService excursionService = BuyExcursionService.getBuyTicketService();
        ArrayList<Port> ports = excursionService.getExcursionsForPurchase(ticketId);
        request.setAttribute(PORTS_ATTRIBUTE, ports);
        request.setAttribute(TICKET_ID_ATTRIBUTE, ticketId);
        request.getSession().setAttribute(USER_ID_ATTRIBUTE, userId);
        try {
            request.getRequestDispatcher(SELECT_PATH).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void buyCommand(int userId, int ticketId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("buy command");
        BuyExcursionService excursionService = BuyExcursionService.getBuyTicketService();
        int excursionId = Integer.parseInt(request.getParameter(EXCURSION_ATTRIBUTE));

        if (excursionId > 0) {
            excursionService.buyExcursionForTicket(ticketId, excursionId);
            request.getSession().setAttribute(USER_ID_ATTRIBUTE, userId);
            try {
                request.getRequestDispatcher(USER_COMMAND_PATH + userId).forward(request, response);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
