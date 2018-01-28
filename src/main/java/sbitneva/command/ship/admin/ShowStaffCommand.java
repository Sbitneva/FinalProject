package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Staff;
import sbitneva.services.ship.admin.ShowStaffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Command: show staff.
 */
public class ShowStaffCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(ShowStaffCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("show staff command execution");

        int shipId = getIntParameter(request, SHIP_ID);
        if (shipId > 0) {
            ShowStaffService showStaffService = ShowStaffService.getShowStaffService();
            ArrayList<Staff> staff = showStaffService.getStaff(shipId);
            if (staff != null) {
                request.setAttribute(STAFF, staff);
                request.getRequestDispatcher(SHIP_STAFF_PAGE).forward(request, response);
            }
        }
    }
}
