package services;

import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.services.ship.admin.ApplyDiscountService;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ApplyDiscountServiceTest {

    private ApplyDiscountService applyDiscountService = ApplyDiscountService.getApplyDiscountService();

    private int ticketId;
    private int discount;

    @Test
    public void setDiscountTestCorrectDiscountAndTicketId() throws TransactionException{
        ticketId = 23;
        discount = 10;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            Ticket ticket = new Ticket(ticketId);
            ticketDao.setTicketProperties(ticket);
            int discountBefore = ticket.getDiscount();
            applyDiscountService.setDiscount(ticketId, discount, 1);

            ticketDao.setTicketProperties(ticket);
            int discountAfter = ticket.getDiscount();

            assertEquals(0, discountBefore);

            assertEquals(discount, discountAfter);
        } catch(Exception e) {

        }
    }
}
