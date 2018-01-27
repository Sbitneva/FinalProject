package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.common.ShowTicketsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;
import static sbitneva.configaration.SecurityConfiguration.CLIENT_TYPE;
import static sbitneva.configaration.SecurityConfiguration.SHIP_ADMIN_TYPE;

public class ShowShipTicketsCommand implements Command {

    private static Logger log = Logger.getLogger(ShowShipTicketsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("execution " + request.getQueryString());

        int userType = getSessionAttribute(request, USER_TYPE_SESSION_ATTRIBUTE);
        boolean success = false;

        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();

        int currentPage = getCurrentPage(request);

        if (userType == SHIP_ADMIN_TYPE) {
            try {
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
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        } else if (userType == CLIENT_TYPE) {
            int shipId = getShipIdFromClient(request);
            if (shipId > 0) {
                try {
                    Ship ship = showTicketsService.getShipForClient(shipId, currentPage);
                    if (ship != null) {
                        success = true;
                        showTicketsService.isInCart(ship.getTickets(), CommandsHelper.getUserId(request));
                        request.setAttribute(PAGE, currentPage);
                        sendData(request, response, ship, TICKETS_PAGE);
                    }
                } catch (Exception e) {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                }
            }
        }

        if (!success) {
            request.getRequestDispatcher(PAGE_NOT_FOUND_PAGE).forward(request, response);
        }
    }

    private void sendData(HttpServletRequest request, HttpServletResponse response, Ship ship, String page)
            throws ServletException, IOException {

        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();
        int pages = showTicketsService.getTicketsPages(ship.getShipId());
        request.setAttribute(SHIP, ship);
        request.setAttribute(PAGES, pages);
        request.getRequestDispatcher(page).forward(request, response);

    }

    private int getCurrentPage(HttpServletRequest request) {
        int currentPage = 1;
        if (request.getParameter(PAGE) != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return currentPage;
    }

    private int getShipIdFromClient(HttpServletRequest request) {
        int shipId = 0;
        if (request.getParameter(SHIP_ID) != null) {
            try {
                shipId = Integer.parseInt(request.getParameter(SHIP_ID));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return shipId;
    }

    private int getSessionAttribute(HttpServletRequest request, String attrName) {
        int value = 0;
        if (request.getSession().getAttribute(attrName) != null) {
            try {
                value = Integer.parseInt(request.getSession().getAttribute(attrName).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return value;
    }

}
