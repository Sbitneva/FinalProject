package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

/**
 * Service: checkout.
 */
public final class CheckoutService {

    private static Logger log = Logger.getLogger(CheckoutService.class.getName());

    private static CheckoutService checkoutService = new CheckoutService();

    private CheckoutService() {

    }

    /**
     * Get CheckoutService instance.
     *
     * @return CheckoutService instance
     */
    public static CheckoutService getCheckoutService() {
        return checkoutService;
    }

    /**
     * Process user checkout.
     *
     * @param userId User ID
     * @return True is checkout was successful, false - otherwise
     */
    public boolean doCheckout(final int userId) {
        boolean isSuccessful = true;

        CartDao cartDao = DaoFactory.getCartDao();
        try {

            TicketDao ticketDao = DaoFactory.getTicketDao();

            TransactionManager.beginTransaction();
            Cart cart = cartDao.getUserCart(userId);
            TransactionManager.getConnection().getConnection().setSavepoint();

            ticketDao.buyTickets(userId, cart);
            cartDao.cleanCart(cart.getTickets(), userId);

            TransactionManager.endTransaction();

        } catch (SQLException | TransactionException e) {
            isSuccessful = false;
            try {
                TransactionManager.rollbackTransaction();
            } catch (TransactionException e1) {
                log.error(e1.getClass().getSimpleName() + " : " + e1.getMessage());
            }
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return isSuccessful;
    }
}
