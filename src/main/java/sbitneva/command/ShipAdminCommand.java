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

    private static final String SHIP_ID_ATTRIBUTE = "shipId";
    private static final String ACTION_ATTRIBUTE = "action";
    private final static String TICKET_ID_ATTRIBUTE = "ticketId";
    private final static String USER_ID_ATTRIBUTE = "userId";
    private final static String COMFORT_ID_ATTRIBUTE = "comfortId";
    private final static String COMFORT_LEVEL_ATTRIBUTE = "comfortLevel";
    private final static String SHIP_ATTRIBUTE = "ship";
    private final static String DISCOUNT_ATTRIBUTE = "discount";

    static Logger log = Logger.getLogger(ShipAdminCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution " + request.getQueryString());
        try {
            int shipId;
            String action;
            if (request.getParameter(ACTION_ATTRIBUTE) == null) {
                log.debug("request data not full");
                response.sendRedirect("/index.jsp");
            } else {
                action = request.getParameter(ACTION_ATTRIBUTE);
                switch (action) {
                    case SHOW_ACTION:
                        if (request.getParameter(SHIP_ID_ATTRIBUTE) != null) {
                            shipId = Integer.parseInt(request.getParameter(SHIP_ID_ATTRIBUTE));
                            showTickets(shipId, request, response);
                        }
                        break;
                    case APPLY_DISCOUNT:
                        if (request.getParameter(SHIP_ID_ATTRIBUTE) != null) {
                            shipId = Integer.parseInt(request.getParameter(SHIP_ID_ATTRIBUTE));
                            applyDiscount(shipId, request, response);
                        }
                        break;
                    case SHOW_SERVICES:
                        showServices(request, response);
                        break;
                    case SHOW_STAFF:
                        if (request.getParameter(SHIP_ID_ATTRIBUTE) != null) {
                            shipId = Integer.parseInt(request.getParameter(SHIP_ID_ATTRIBUTE));
                            showStaff(shipId, request, response);
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch(Exception e){
            log.error(e.getMessage());
        }
    }

    private void showTickets(int shipId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("show tickets action");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            int userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_ATTRIBUTE).toString());
            log.debug("userId = " + userId);
            Ship ship = shipAdminService.getShipBaseInfoAndTickets(userId, shipId);
            request.getSession().setAttribute(USER_ID_ATTRIBUTE, userId);
            request.setAttribute(SHIP_ATTRIBUTE, ship);
            request.setAttribute(USER_ID_ATTRIBUTE, userId);
            request.getRequestDispatcher(SHIP_ADMIN_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void applyDiscount(int shipId, HttpServletRequest request, HttpServletResponse response) {
        log.debug("apply discount action");
        try {
            ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();
            int discount;
            int ticketId;
            if ((request.getParameter(DISCOUNT_ATTRIBUTE) != null) && (request.getParameter(TICKET_ID_ATTRIBUTE) != null)) {
                discount = Integer.parseInt(request.getParameter(DISCOUNT_ATTRIBUTE));
                ticketId = Integer.parseInt(request.getParameter(TICKET_ID_ATTRIBUTE));
                log.debug("discount = " + discount + " ticketId = " + ticketId);
                shipAdminService.setDiscount(discount, ticketId);
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
            if (request.getParameter(COMFORT_ID_ATTRIBUTE) != null) {
                comfortLevelId = Integer.parseInt(request.getParameter(COMFORT_ID_ATTRIBUTE));
                if (comfortLevelId > 0) {
                    ComfortLevel comfortLevel = shipAdminService.getComfortLevelServices(comfortLevelId);
                    if (comfortLevel.getServices().size() > 0) {
                        request.setAttribute(COMFORT_LEVEL_ATTRIBUTE, comfortLevel);
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
            request.setAttribute(SHOW_STAFF, staff);
            request.getRequestDispatcher(STAFF_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
