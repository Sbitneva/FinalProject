package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;
import sbitneva.services.client.CheckoutService;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class CheckoutServiceTest {

    private static Logger log = Logger.getLogger(CheckoutServiceTest.class.getName());

    private final int USER_ID = 2;
    CheckoutService checkoutService = CheckoutService.getCheckoutService();

    @Test
    public void doCheckout() {
        CartDao cartDao = DaoFactory.getCartDao();
        try {
            /**
             * Users cart before checking out
             */
            Cart cartBeforeCheckout = cartDao.getUserCart(USER_ID);
            assertEquals(5, cartBeforeCheckout.getTickets().size());

            TicketDao ticketDao = DaoFactory.getTicketDao();
            int ticketsShip1Before = ticketDao.getAvailableTicketsNumber(1);
            int ticketsShip2Before = ticketDao.getAvailableTicketsNumber(2);

            checkoutService.doCheckout(USER_ID);

            int ticketsShip1After = ticketDao.getAvailableTicketsNumber(1);
            int ticketsShip2After = ticketDao.getAvailableTicketsNumber(2);

            assertEquals(ticketsShip1Before, ticketsShip1After);
            assertEquals(ticketsShip2Before, ticketsShip2After);

            Cart cartAfterCheckout = cartDao.getUserCart(USER_ID);
            assertEquals(cartBeforeCheckout.getTickets().size(), cartAfterCheckout.getTickets().size());

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }

    }
}
