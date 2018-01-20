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


}
