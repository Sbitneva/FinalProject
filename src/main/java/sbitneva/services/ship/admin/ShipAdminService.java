package sbitneva.services.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShipAdminService {
    private static Logger log = Logger.getLogger(ShipAdminService.class.getName());

    private static ShipAdminService shipAdminService = new ShipAdminService();

    private static final int ITEMS_PER_PAGE = 10;


    private ShipAdminService() {

    }

    public static ShipAdminService getShipAdminService() {
        return shipAdminService;
    }

    public Ship getShip(int userId, int pageId) {
        Ship ship = null;
        UserDao userDao = DaoFactory.getUserDao();
        try{
            int shipId = userDao.getUserShipId(userId);
            if(shipId > 0){
                ShipDao shipDao = DaoFactory.getShipDao();
                ship = shipDao.getBasicShipData(shipId);
                if(ship != null) {
                    int offset = (pageId - 1) * ITEMS_PER_PAGE;
                    TicketDao ticketDao = DaoFactory.getTicketDao();
                    int itemsNumber = ticketDao.getAvailableTicketsNumber(shipId) - offset;
                    if(itemsNumber > ITEMS_PER_PAGE){
                        itemsNumber = ITEMS_PER_PAGE;
                    }
                    ArrayList<Ticket> tickets = getTickets(shipId, offset, itemsNumber);
                    ship.setTickets(tickets);
                }
            }
        } catch (SQLException | DaoException e){
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ship;
    }

    public int getTicketsPages(int shipId) {
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

    public ArrayList<Ticket> getTickets(int shipId, int offset, int itemsNumber) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ArrayList<Ticket> tickets = null;
        try {
            tickets = ticketDao.getTicketsForPage(shipId, offset, itemsNumber);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return tickets;
    }
}
