package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.services.client.ShowCartService;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShowCartServiceTest {

    private static Logger log = Logger.getLogger(ShowCartServiceTest.class.getName());

    private final int USER_ID = 4;
    private ShowCartService showCartService = ShowCartService.getShowCartService();
    private ArrayList<Ticket> tickets = new ArrayList<>();

    @Test
    public void getCartTest() {

        /**
         * Init Not available tickets for adding to cart
         */
        tickets.add(new Ticket(1));
        tickets.add(new Ticket(8));
        /**
         * Init Available tickets for adding to cart
         */
        tickets.add(new Ticket(24));
        tickets.add(new Ticket(27));
        tickets.add(new Ticket(12));

        Cart cart;

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            TransactionManager.beginTransaction();
            /**
             * Adding tickets to user cart
             */
            for (Ticket ticket : tickets) {
                cartDao.addTicketToCart(USER_ID, ticket.getTicketId());
            }
            TransactionManager.endTransaction();
            /**
             * Getting cart of the same user
             */
            cart = showCartService.getCart(USER_ID);
            assertEquals(3, cart.getTickets().size());
            assertEquals(2, cart.getDeletedTickets().size());


        } catch (SQLException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            try{
                TransactionManager.endTransaction();
            }catch(SQLException | TransactionException e1) {
                log.error(e1.getClass().getSimpleName() + " : " + e1.getMessage());
            }
        }
    }
}
