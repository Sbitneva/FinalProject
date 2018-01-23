package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;

import java.sql.SQLException;

public class CheckoutService {

    private static Logger log = Logger.getLogger(CheckoutService.class.getName());

    private static CheckoutService checkoutService = new CheckoutService();

    public static CheckoutService getCheckoutService() {
        return checkoutService;
    }

    private CheckoutService(){

    }

    public boolean doCheckout(int userId) {
        boolean isSuccessful = false;

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            Cart cart = cartDao.getUserCart(userId);

            TicketDao ticketDao = DaoFactory.getTicketDao();
            isSuccessful = ticketDao.buyTickets(userId, cart);
            if(isSuccessful) {
                isSuccessful = cartDao.cleanCart(cart.getTickets(), userId);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return isSuccessful;
    }
}
