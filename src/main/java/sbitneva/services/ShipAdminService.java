package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.ComfortLevel;
import sbitneva.entity.Ship;
import sbitneva.entity.Staff;
import sbitneva.exception.RequestedDataException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShipAdminService {
    private static Logger log = Logger.getLogger(ShipAdminService.class.getName());

    private static ShipAdminService shipAdminService = new ShipAdminService();

    private ShipAdminService() {

    }

    public static ShipAdminService getShipAdminService() {
        return shipAdminService;
    }

    public Ship getShipBaseInfoAndTickets(int userId, int shipId) throws RequestedDataException {
        Ship ship = new Ship();
        if(shipId <= 0) {
            throw new RequestedDataException ("ShipId parameter must be bigger then 0");
        }
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

    public boolean setDiscount(int discount, int ticketId) {
        boolean status = false;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            if ((discount >= 0) && (discount <= 100)) {
                int result = ticketDao.updateDiscount(ticketId, discount);
                if (result == 1) {
                    status = true;
                } else {
                    log.warn("Ticket discount is not updated");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return status;
    }

    public ComfortLevel getComfortLevelServices(int comfortLevelId) {
        ComfortLevel comfortLevel = new ComfortLevel();
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        try {
            comfortLevel.setServices(comfortLevelDao.getComfortLevelInfo(comfortLevelId));
            comfortLevel.setComfortLevelName(comfortLevelDao.getComfortLevelNameById(comfortLevelId));
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return comfortLevel;
    }

    public ArrayList<Staff> getStaff(int shipId) {
        ArrayList<Staff> staff = new ArrayList<>();
        try {
            StaffDao staffDao = DaoFactory.getStaffDao();
            staff = staffDao.getStaff(shipId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return staff;
    }
}
