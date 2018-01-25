package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.services.client.AddTicketToCartService;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AddTicketToCartServiceTest {

    private static Logger log = Logger.getLogger(AddTicketToCartServiceTest.class.getName());

    private AddTicketToCartService addTicketToCartService = AddTicketToCartService.getAddTicketToCartService();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private int userId = 6;

    @Test
    public void addTest() {
        CartDao cartDao = DaoFactory.getCartDao();
        try {
            /**
             * Getting user cart from db
             */
            Cart cart = cartDao.getUserCart(userId);
            assertEquals(0, cart.getTickets().size());

            /**
             * Add new ticket into array list
             */
            tickets.add(new Ticket(35));

            /**
             * Add new ticket cart through AddTicketToCartService
             */
            addTicketToCartService.add(userId, tickets.get(0).getTicketId());

            /**
             * Getting updated user cart from db
             */
            cart = cartDao.getUserCart(userId);

            /**
             * Expecting cart size increasing after previous operations
             */
            assertEquals(1, cart.getTickets().size());

            /**
             * Restoring db state
             */

            TransactionManager.beginTransaction();
            cartDao.cleanCart(tickets, userId);
            TransactionManager.endTransaction();

        } catch (SQLException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
