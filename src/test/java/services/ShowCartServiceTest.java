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
    private final int USER_ID = 2;
    private final int TICKET_ID = 35;
    private final int TICKET_ID_DEL1 = 2;
    private final int TICKET_ID_DEL2 = 41;

    @Test
    public void getCartTest(){
        tickets.add(new Ticket(TICKET_ID));
        //getting cart by userId
        Cart cart = showCartService.getCart(USER_ID);
        assertEquals(2, cart.getTickets().size());
        assertEquals(2, cart.getDeletedTickets().size());

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            /**
             * Adding new ticket to cart
             */
            cartDao.addTicketToCart(USER_ID, tickets.get(0).getTicketId());

            /**
             * Getting cart of the same user
             */
            cart = showCartService.getCart(USER_ID);
            assertEquals(3, cart.getTickets().size());
            assertEquals(0, cart.getDeletedTickets().size());

            /**
             * Restoring db state
             */
            TransactionManager.beginTransaction();
            cartDao.cleanCart(tickets, USER_ID);
            TransactionManager.endTransaction();
            assertEquals(2, showCartService.getCart(USER_ID).getTickets().size());
            cartDao.addTicketToCart(USER_ID, TICKET_ID_DEL1);
            cartDao.addTicketToCart(USER_ID, TICKET_ID_DEL2);
            cart = cartDao.getUserCart(USER_ID);
            assertEquals(4, cart.getTickets().size());
        } catch (SQLException | TransactionException e){

        }
    }
}
