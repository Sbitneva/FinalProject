package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.services.ShipAdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShipAdminCommand implements Command {
    private static final String SHIP_ADMIN_PAGE = "jsp/ship-administrator/ship-info.jsp";
    static Logger log = Logger.getLogger(ShipAdminCommand.class.getName());

    private static final String SHOW_ACTION = "show";
    private static final String APPLY_DISCOUNT = "apply";
    private static final String SHOW_SERVICES = "services";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution " + request.getQueryString());
        int shipId = 0;
        int userId = 0;
        String action = new String();
        if ((request.getParameter("shipId") == null) || (request.getParameter("userId") == null)
                || (request.getParameter("action") == null)) {
            return;
        } else {
            action = request.getParameter("action");
            shipId = Integer.parseInt(request.getParameter("shipId"));
            userId = Integer.parseInt(request.getParameter("userId"));
            switch (action){
                case SHOW_ACTION :
                    showTickets(shipId, userId);
                break;
                case APPLY_DISCOUNT :
                    applyDiscount(shipId, userId);
                    break;
                case SHOW_SERVICES :
                    showServices(shipId, userId);
                    break;
                    default:
                        break;
            }


            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            Ship ship = shipAdminService.getShipBaseInfoAndTickets(userId, shipId);
            request.setAttribute("ship", ship);
            request.getRequestDispatcher(SHIP_ADMIN_PAGE).forward(request, response);
        }
    }

    private void showTickets(int shipId, int userId) {

    }

    private void applyDiscount(int shipId, int userId) {

    }

    private void showServices(int shipId, int userId) {

    }
}
