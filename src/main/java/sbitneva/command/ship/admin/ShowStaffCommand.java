package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.entity.Staff;
import sbitneva.services.ship.admin.ShowStaffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static sbitneva.command.CommandsHelper.*;

public class ShowStaffCommand implements Command {

    private static Logger log = Logger.getLogger(ShowStaffCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("show staff command execution");
        int shipId = getShipId(request);
        log.debug("shipId = " + shipId);
        if (shipId > 0) {
            ShowStaffService showStaffService = ShowStaffService.getShowStaffService();
            ArrayList<Staff> staff = showStaffService.getStaff(shipId);
            if (staff != null) {
                log.debug("staff number" + staff.size());
                request.setAttribute(STAFF, staff);
                request.getRequestDispatcher(SHIP_STAFF_PAGE).forward(request, response);
            }
        }
    }

    private int getShipId(HttpServletRequest request) {
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
}
