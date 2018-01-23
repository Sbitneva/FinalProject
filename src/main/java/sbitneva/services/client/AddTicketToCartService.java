package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;

import java.sql.SQLException;

public class AddTicketToCartService {

    private static Logger log = Logger.getLogger(AddTicketToCartService.class.getName());

    private static AddTicketToCartService addTicketToCartService = new AddTicketToCartService();

    private AddTicketToCartService(){

    }

    public static AddTicketToCartService getAddTicketToCartService() {
        return addTicketToCartService;
    }

    public void add(int userId, int ticketId) {
        CartDao cartDao = DaoFactory.getCartDao();
        try {
            cartDao.addTicketToCart(userId, ticketId);
        }catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
