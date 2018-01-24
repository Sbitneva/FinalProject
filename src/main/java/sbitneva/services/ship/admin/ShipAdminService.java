package sbitneva.services.ship.admin;

import org.apache.log4j.Logger;

public class ShipAdminService {
    private static final int ITEMS_PER_PAGE = 10;
    private static Logger log = Logger.getLogger(ShipAdminService.class.getName());
    private static ShipAdminService shipAdminService = new ShipAdminService();


    private ShipAdminService() {

    }

    public static ShipAdminService getShipAdminService() {
        return shipAdminService;
    }


}
