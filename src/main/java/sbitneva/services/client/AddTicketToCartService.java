package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

/**
 * Service: add ticket to cart.
 */
public final class AddTicketToCartService {

    private static Logger log = Logger.getLogger(AddTicketToCartService.class.getName());

    private static AddTicketToCartService addTicketToCartService = new AddTicketToCartService();

    private AddTicketToCartService() {

    }

    /**
     * Get service instance.
     *
     * @return AddTicketToCartService instance
     */
    public static AddTicketToCartService getAddTicketToCartService() {
        return addTicketToCartService;
    }

    /**
     * Add ticket to user's cart.
     *
     * @param userId User ID
     * @param ticketId Ticket ID
     */
    public void add(final int userId, final int ticketId) {
        CartDao cartDao = DaoFactory.getCartDao();
        try {
            TransactionManager.beginTransaction();
            cartDao.addTicketToCart(userId, ticketId);
            TransactionManager.endTransaction();
        } catch (SQLException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
