package sbitneva.services.common;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Port;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Service: show tickets.
 */
public final class ShowTicketsService {
    private static final int ITEMS_PER_PAGE = 9;
    private static Logger log = Logger.getLogger(LoginService.class.getName());
    private static ShowTicketsService showTicketsService = new ShowTicketsService();

    private ShowTicketsService() {
    }

    /**
     * Get ShowTicketsService instance.
     *
     * @return ShowTicketsService instance
     */
    public static ShowTicketsService getShowTicketsService() {
        return showTicketsService;
    }

    /**
     * Ship tickets requested by user.
     *
     * @param userId User ID
     * @param pageId Page ID
     * @return Filled ship
     */
    public Ship getShip(final int userId, final int pageId) {
        Ship ship = null;
        UserDao userDao = DaoFactory.getUserDao();
        try {
            int shipId = userDao.getUserShipId(userId);
            if (shipId > 0) {
                ShipDao shipDao = DaoFactory.getShipDao();
                ship = shipDao.getBasicShipData(shipId);
                if (ship != null) {
                    ArrayList<Ticket> tickets = getTickets(shipId, pageId);
                    if (tickets != null) {
                        ship.setTickets(tickets);
                        ship.setPorts(getShipPorts(shipId));
                    }
                }
            }
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ship;
    }

    /**
     * Get tickets pages number.
     *
     * @param shipId Ship ID
     * @return Tickets pages number
     */
    public int getTicketsPages(final int shipId) {
        int pagesNumber = 0;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int itemsNumber = ticketDao.getAvailableTicketsNumber(shipId);
            pagesNumber = itemsNumber / ITEMS_PER_PAGE;
            if (ITEMS_PER_PAGE * pagesNumber < itemsNumber) {
                pagesNumber = pagesNumber + 1;
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return pagesNumber;
    }

    /**
     * Get bunch of ship tickets.
     *
     * @param shipId Ship ID
     * @param pageId Page ID
     * @return Tickets list
     */
    public ArrayList<Ticket> getTickets(final int shipId, final int pageId) {
        ArrayList<Ticket> tickets = null;
        int offset = (pageId - 1) * ITEMS_PER_PAGE;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int itemsNumber = ticketDao.getAvailableTicketsNumber(shipId) - offset;
            if (itemsNumber > ITEMS_PER_PAGE) {
                itemsNumber = ITEMS_PER_PAGE;
            }
            tickets = ticketDao.getTicketsForPage(shipId, offset, itemsNumber);

            setComfortLevelNames(tickets);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return tickets;
    }

    /**
     * Get ship data for page.
     *
     * @param shipId Ship ID
     * @param currentPage Page ID
     * @return Ship data
     */
    public Ship getShipForClient(final int shipId, final int currentPage) {
        Ship ship = null;
        ShipDao shipDao = DaoFactory.getShipDao();
        try {
            ship = shipDao.getBasicShipData(shipId);
            if (ship != null) {
                ship.setTickets(getTickets(shipId, currentPage));
                ship.setPorts(getShipPorts(shipId));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ship;
    }

    private ArrayList<Port> getShipPorts(final int shipId) {

        ArrayList<Port> ports = new ArrayList<>();
        PortDao portDao = DaoFactory.getPortDao();
        try {
            ports = portDao.getPortsByShipId(shipId);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ports;
    }

    private void setComfortLevelNames(final ArrayList<Ticket> tickets) {
        Map<Integer, String> comfortLevels;
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        try {
            comfortLevels = comfortLevelDao.getComfortLevels();
            if (comfortLevels.size() > 0) {
                for (Ticket ticket : tickets) {
                    ticket.setComfortLevelName(comfortLevels.get(ticket.getComfortLevel()));
                }
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

    /**
     * Push tickets into cart.
     *
     * @param tickets Tickets list
     * @param userId User ID
     */
    public void setInCartProperty(final ArrayList<Ticket> tickets, final int userId) {
        CartDao cartDao = DaoFactory.getCartDao();

        for (Ticket ticket : tickets) {
            try {
                ticket.setCart(cartDao.isTicketInCart(userId, ticket.getTicketId()));
            } catch (SQLException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
    }
}
