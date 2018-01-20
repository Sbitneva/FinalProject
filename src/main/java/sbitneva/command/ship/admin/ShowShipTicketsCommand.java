package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.common.ShowTicketsService;

import static sbitneva.command.CommandsHelper.PAGES;
import static sbitneva.command.CommandsHelper.PAGE;
import static sbitneva.command.CommandsHelper.SHIP;
import static sbitneva.command.CommandsHelper.SHIP_INFO_PAGE;
import static sbitneva.command.CommandsHelper.USER_ID_SESSION_ATTRIBUTE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowShipTicketsCommand implements Command {

    private static Logger log = Logger.getLogger(ShowShipTicketsCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution " + request.getQueryString());
        ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();
        int currentPage = getCurrentPage(request);
        try {
            int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE).toString());
            Ship ship = showTicketsService.getShip(userId, currentPage);
            if(ship != null) {
                int pages = showTicketsService.getTicketsPages(ship.getShipId());
                request.setAttribute(SHIP, ship);
                request.setAttribute(PAGES, pages);
                request.getRequestDispatcher(SHIP_INFO_PAGE).forward(request, response);
            }

        } catch (Exception e){

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
}
