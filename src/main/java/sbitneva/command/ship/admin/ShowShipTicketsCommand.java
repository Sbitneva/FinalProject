package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.entity.Ship;
import sbitneva.services.common.ShowTicketsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static sbitneva.command.CommandsHelper.*;
import static sbitneva.configaration.SecurityConfiguration.*;

public class ShowShipTicketsCommand implements Command {

    private static Logger log = Logger.getLogger(ShowShipTicketsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution " + request.getQueryString());
        int userType = Integer.parseInt(request.getSession().getAttribute(USER_TYPE_SESSION_ATTRIBUTE).toString());
        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();

        int currentPage = getCurrentPage(request);

        if(userType == SHIP_ADMIN_TYPE){
            try {
                int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE).toString());
                Ship ship = showTicketsService.getShip(userId, currentPage);
                if(ship != null) {
                    sendData(request, response, ship, SHIP_INFO_PAGE);
                }
            } catch (Exception e){
                log.error(e.getMessage());
            }

        } else if(userType == CLIENT_TYPE) {
            int shipId = getShipIdFromClient(request);
            try{
                Ship ship = showTicketsService.getShipForClient(shipId, currentPage);
                if(ship != null) {
                    sendData(request, response, ship, TICKETS_PAGE);
                }
            } catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }

    private void sendData(HttpServletRequest request, HttpServletResponse response, Ship ship, String page){
        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();
        try{
            int pages = showTicketsService.getTicketsPages(ship.getShipId());
            request.setAttribute(SHIP, ship);
            request.setAttribute(PAGES, pages);
            request.getRequestDispatcher(page).forward(request, response);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private int getCurrentPage(HttpServletRequest request){
        int currentPage = 1;
        if(request.getParameter(PAGE) != null) {
            try{
                currentPage = Integer.parseInt(request.getParameter(PAGE));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return currentPage;
    }

    private int getShipIdFromClient(HttpServletRequest request){
        int shipId = 0;
        if(request.getParameter(SHIP_ID) != null) {
            try{
                shipId = Integer.parseInt(request.getParameter(SHIP_ID));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return shipId;
    }

}
