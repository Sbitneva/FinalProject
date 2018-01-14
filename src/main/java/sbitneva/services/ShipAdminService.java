package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ShipDao;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Ship;

import java.sql.SQLException;

public class ShipAdminService {
    private static Logger log = Logger.getLogger(ShipAdminService.class.getName());

    private static ShipAdminService shipAdminService = new ShipAdminService();

    private ShipAdminService() {

    }

    public static ShipAdminService getShipAdminService() {
        return shipAdminService;
    }

    public Ship getShipBaseInfoAndTickets(int userId, int shipId) {
        Ship ship = new Ship();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ShipDao shipDao = DaoFactory.getShipDao();
        try {
            ship.setShipId(shipId);
            ship.setShipName(shipDao.getShipNameById(shipId));
            ship.setTickets(ticketDao.getAllAvailableTickets(shipId));

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return ship;
    }
}
