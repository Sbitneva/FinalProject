package services;

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
    private AddTicketToCartService addTicketToCartService = AddTicketToCartService.getAddTicketToCartService();
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private int userId = 2;

    @Test
    public void addTest(){
        CartDao cartDao = DaoFactory.getCartDao();
        try{
            /**
             * Getting user cart from db
             */
            Cart cart = cartDao.getUserCart(userId);
            assertEquals(4, cart.getTickets().size());

            /**
             * Add new ticket into array list
             */
            tickets.add(new Ticket(35));

            /**
             * Add new ticket cart through AddTicketToCartService
             */
            addTicketToCartService.add(2, tickets.get(0).getTicketId());

            /**
             * Getting updated user cart from db
             */
            cart = cartDao.getUserCart(2);

            /**
             * Expecting cart size increasing after previous operations
             */
            assertEquals(5, cart.getTickets().size());

            /**
             * Restoring db state
             */

            TransactionManager.beginTransaction();
            cartDao.cleanCart(tickets, 2);
            TransactionManager.endTransaction();

        }catch (SQLException | TransactionException e){

        }
    }
}
