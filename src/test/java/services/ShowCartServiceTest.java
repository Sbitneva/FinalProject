package services;

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
    private ShowCartService showCartService = ShowCartService.getShowCartService();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private final int USER_ID = 4;

    @Test
    public void getCartTest(){
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
            /**
             * Adding tickets to user cart
             */
            for(Ticket ticket : tickets){
                cartDao.addTicketToCart(USER_ID, ticket.getTicketId());
            }
            /**
             * Getting cart of the same user
             */
            cart = showCartService.getCart(USER_ID);
            assertEquals(3, cart.getTickets().size());
            assertEquals(2, cart.getDeletedTickets().size());

            /**
             * Restoring db state
             */
            TransactionManager.beginTransaction();
            cartDao.cleanCart(tickets, USER_ID);
            TransactionManager.endTransaction();

            assertEquals(0, showCartService.getCart(USER_ID).getTickets().size());

            cart = cartDao.getUserCart(USER_ID);
            assertEquals(0, cart.getTickets().size());

        } catch (SQLException | TransactionException e){

        }
    }
}
