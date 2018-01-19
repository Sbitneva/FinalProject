package sbitneva.services.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.ComfortLevel;
import sbitneva.entity.Ship;
import sbitneva.entity.Staff;
import sbitneva.exception.DaoException;
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

    public Ship getShip(int userId) {
        Ship ship = null;
        UserDao userDao = DaoFactory.getUserDao();
        try{
            int shipId = userDao.getUserShipId(userId);
            if(shipId > 0){
                ShipDao shipDao = DaoFactory.getShipDao();
                ship = shipDao.getBasicShipData(shipId);
            }
        } catch (SQLException | DaoException e){
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ship;
    }

}
