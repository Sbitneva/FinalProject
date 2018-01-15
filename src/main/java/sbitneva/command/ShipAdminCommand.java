package sbitneva.command;

import org.apache.log4j.Logger;
import sbitneva.entity.ComfortLevel;
import sbitneva.entity.Ship;
import sbitneva.entity.Staff;
import sbitneva.services.ShipAdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ShipAdminCommand implements Command {

    private static final String SHIP_ADMIN_PAGE = "jsp/ship-administrator/ship-info.jsp";
    private static final String COMFORT_LEVEL_SERVICES_PAGE = "comfort-level-services.jsp";
    private static final String STAFF_PAGE = "jsp/ship-administrator/staff.jsp";
    private static final String SHOW_ACTION = "show";
    private static final String APPLY_DISCOUNT = "apply";
    private static final String SHOW_SERVICES = "services";
    private static final String SHOW_STAFF = "staff";
    static Logger log = Logger.getLogger(ShipAdminCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("execution " + request.getQueryString());
        int shipId = 0;
        String action = new String();
        if (request.getParameter("action") == null) {
            log.debug("request data not full");
            return;
        } else {
            action = request.getParameter("action");
            switch (action) {
                case SHOW_ACTION:
                    if (request.getParameter("shipId") != null) {
                        shipId = Integer.parseInt(request.getParameter("shipId"));
                        showTickets(shipId, request, response);
                    }
                    break;
                case APPLY_DISCOUNT:
                    if (request.getParameter("shipId") != null) {
                        shipId = Integer.parseInt(request.getParameter("shipId"));
                        applyDiscount(shipId, request, response);
                    }
                    break;
                case SHOW_SERVICES:
                    showServices(request, response);
                    break;
                case SHOW_STAFF:
                    if (request.getParameter("shipId") != null) {
                        shipId = Integer.parseInt(request.getParameter("shipId"));
                        showStaff(shipId, request, response);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showTickets(int shipId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("show tickets action");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
            log.debug("userId = " + userId);
            Ship ship = shipAdminService.getShipBaseInfoAndTickets(userId, shipId);
            request.getSession().setAttribute("userId", userId);
            request.setAttribute("ship", ship);
            request.setAttribute("userId", userId);
            request.getRequestDispatcher(SHIP_ADMIN_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void applyDiscount(int shipId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("apply discount action");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            int discount = 0;
            int ticketId = 0;
            if ((request.getParameter("discount") != null) && (request.getParameter("ticketId") != null)) {
                discount = Integer.parseInt(request.getParameter("discount"));
                ticketId = Integer.parseInt(request.getParameter("ticketId"));
                log.debug("discount = " + discount + " ticketId = " + ticketId);
                boolean result = shipAdminService.setDiscount(discount, ticketId);
                showTickets(shipId, request, response);
            } else {
                log.warn("ticketId or discountIs parameters is empty");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void showServices(HttpServletRequest request, HttpServletResponse response) {
        log.debug("show services action");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            int comfortLevelId = 0;
            if (request.getParameter("comfortId") != null) {
                comfortLevelId = Integer.parseInt(request.getParameter("comfortId"));
                if (comfortLevelId > 0) {
                    ComfortLevel comfortLevel = shipAdminService.getComfortLevelServices(comfortLevelId);
                    if (comfortLevel.getServices().size() > 0) {
                        request.setAttribute("comfortLevel", comfortLevel);
                        request.getRequestDispatcher(COMFORT_LEVEL_SERVICES_PAGE).forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void showStaff(int shipId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("show ship's staff");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            ArrayList<Staff> staff = shipAdminService.getStaff(shipId);
            request.setAttribute("staff", staff);
            request.getRequestDispatcher(STAFF_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
