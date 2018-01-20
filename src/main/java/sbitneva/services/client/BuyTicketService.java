package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Client;
import sbitneva.entity.Ship;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyTicketService {
    private static Logger log = Logger.getLogger(BuyTicketService.class.getName());
    private static BuyTicketService buyTicketService = new BuyTicketService();

    private BuyTicketService() {

    }

    public static BuyTicketService getBuyTicketService() {
        return buyTicketService;
    }




}
