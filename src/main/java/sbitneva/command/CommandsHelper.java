package sbitneva.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandsHelper {
    public static final String SERVLET_NAME = "/Cruise";
    //session attributes names
    public static final String USER_ID_SESSION_ATTRIBUTE = "id";
    public static final String USER_TYPE_SESSION_ATTRIBUTE = "type";
    //request parameters names

    public static final String SHIP_ID = "shipId";
    public static final String CART = "cart";
    public static final String TICKET_ID = "ticketId";
    public static final String SHIP = "ship";
    public static final String SHIPS = "ships";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ERRORS = "errors";
    public static final String CLIENT = "client";
    public static final String PAGE = "page";
    public static final String PAGES = "pages";
    public static final String PORTS = "ports";
    public static final String COMFORT_ID = "comfortId";
    public static final String SERVICES = "comfortLevel";
    public static final String STAFF = "staff";
    public static final String DISCOUNT = "discount";
    public static final String EXCURSION_ID = "excursionId";
    // client pages
    public static final String CLIENT_INFO_PAGE = "jsp/client/client-page.jsp";
    public static final String CRUISES_LIST_PAGE = "jsp/client/cruises.jsp";
    public static final String MAIN_PAGE = "index.jsp";
    public static final String EXCURSIONS_PAGE = "jsp/client/excursions.jsp";
    public static final String USER_CART_PAGE = "jsp/client/cart.jsp";
    public static final String CHECKOUT_SUCCESS_PAGE = "jsp/client/checkout-success.jsp";

    // common pages
    public static final String SERVICES_PAGE = "comfort-level-services.jsp";
    public static final String TICKETS_PAGE = "jsp/client/tickets.jsp";
    public static final String REGISTRATION_PAGE = "jsp/registration/registration.jsp";
    // ship-admin pages
    public static final String SHIP_STAFF_PAGE = "jsp/ship-administrator/staff.jsp";
    public static final String SHIP_INFO_PAGE = "jsp/ship-administrator/ship-info.jsp";
    // client commands

    public static final String CLIENT_COMMAND = "?command=client";
    public static final String CART_COMMAND = "?command=cart";
    // common commands

    public static final String SHOW_SHIP_COMMAND = "?command=showShip";

    private static Logger log = Logger.getLogger(CommandsHelper.class.getName());
    // ship-admin commands
    private static CommandsHelper commandsHelper = new CommandsHelper();

    private CommandsHelper() {

    }

    public static int getUserId(HttpServletRequest request) {
        int userId = 0;
        if (request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE) != null) {
            try {
                userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return userId;
    }


}
