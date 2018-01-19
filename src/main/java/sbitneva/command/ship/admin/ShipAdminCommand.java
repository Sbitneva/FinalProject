package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.ship.admin.ShipAdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShipAdminCommand implements Command {

    static Logger log = Logger.getLogger(ShipAdminCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution " + request.getQueryString());
        ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
        try {
            int userId = Integer.parseInt(request.getSession().getAttribute(CommandsHelper.USER_ID_SESSION_ATTRIBUTE).toString());
            Ship ship = shipAdminService.getShip(userId);
            if(ship != null) {
                request.setAttribute(CommandsHelper.SHIP, ship);
                request.getRequestDispatcher(CommandsHelper.SHIP_INFO_PAGE).forward(request, response);
            }

        } catch (Exception e){

        }

    }
}
