package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.common.ShowTicketsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.configaration.SecurityConfiguration.CLIENT_TYPE;
import static sbitneva.configaration.SecurityConfiguration.SHIP_ADMIN_TYPE;

/**
 * Command: show ship tickets.
 */
public class ShowShipTicketsCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(ShowShipTicketsCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException, ServletException {
        log.debug("execution " + request.getQueryString());

        int userType = getSessionAttribute(request, USER_TYPE_SESSION_ATTRIBUTE);
        boolean success = false;

        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();

        int currentPage = getPage(request);

        if (userType == SHIP_ADMIN_TYPE) {
            int userId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);
            if (userId > 0) {
                Ship ship = showTicketsService.getShip(userId, currentPage);
                if (ship != null) {
                    success = true;
                    request.getSession().setAttribute(SHIP_ID, ship.getShipId());
                    request.setAttribute(PAGE, currentPage);
                    sendData(request, response, ship, SHIP_INFO_PAGE);
                }
            }


        } else if (userType == CLIENT_TYPE) {
            int shipId = getIntParameter(request, SHIP_ID);
            if (shipId > 0) {
                Ship ship = showTicketsService.getShipForClient(shipId, currentPage);
                if (ship != null) {
                    success = true;
                    int clientId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);
                    showTicketsService.setInCartProperty(ship.getTickets(), clientId);
                    request.setAttribute(PAGE, currentPage);
                    sendData(request, response, ship, TICKETS_PAGE);
                }
            }
        }
        if (!success) {
            request.getRequestDispatcher(PAGE_NOT_FOUND_PAGE).forward(request, response);
        }
    }

    private void sendData(
            final HttpServletRequest request, final HttpServletResponse response, final Ship ship, final String page
    ) throws ServletException, IOException {

        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();
        int pages = showTicketsService.getTicketsPages(ship.getShipId());
        request.setAttribute(SHIP, ship);
        request.setAttribute(PAGES, pages);
        request.getRequestDispatcher(page).forward(request, response);

    }
}
