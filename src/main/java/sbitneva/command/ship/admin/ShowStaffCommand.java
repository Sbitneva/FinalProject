package sbitneva.command.ship.admin;

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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int shipId = getShipId(request);
        if(shipId > 0){
            ShowStaffService showStaffService = ShowStaffService.getShowStaffService();
            ArrayList<Staff> staff = showStaffService.getStaff(shipId);
            if(staff != null) {
                request.setAttribute(STAFF, staff);
                request.getRequestDispatcher(SHIP_STAFF_PAGE).forward(request, response);
            }
        }
    }

    private int getShipId(HttpServletRequest request) {
        int shipId = 0;
        if(request.getParameter(SHIP_ID) != null) {
            shipId = Integer.parseInt(request.getParameter(SHIP_ID));
        }
        return shipId;
    }
}
