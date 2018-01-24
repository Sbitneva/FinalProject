package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

public class CheckoutService {

    private static Logger log = Logger.getLogger(CheckoutService.class.getName());

    private static CheckoutService checkoutService = new CheckoutService();

    private CheckoutService() {

    }

    public static CheckoutService getCheckoutService() {
        return checkoutService;
    }

    public boolean doCheckout(int userId) {
        boolean isSuccessful = true;

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            Cart cart = cartDao.getUserCart(userId);
            TicketDao ticketDao = DaoFactory.getTicketDao();

            TransactionManager.beginTransaction();

            ticketDao.buyTickets(userId, cart);
            cartDao.cleanCart(cart.getTickets(), userId);

            TransactionManager.endTransaction();

        } catch (SQLException | TransactionException e) {
            isSuccessful = false;
            try {
                TransactionManager.rollbackTransaction();
            } catch (TransactionException e1){
                log.error(e1.getClass().getSimpleName() + " : " + e.getMessage());
            }
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return isSuccessful;
    }
}
