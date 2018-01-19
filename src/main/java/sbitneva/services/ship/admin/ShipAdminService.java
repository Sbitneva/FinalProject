package sbitneva.services.ship.admin;

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


}
