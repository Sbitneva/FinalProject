package sbitneva.services.client;

import org.apache.log4j.Logger;

public class BuyTicketService {
    private static Logger log = Logger.getLogger(BuyTicketService.class.getName());
    private static BuyTicketService buyTicketService = new BuyTicketService();

    private BuyTicketService() {

    }

    public static BuyTicketService getBuyTicketService() {
        return buyTicketService;
    }


}
