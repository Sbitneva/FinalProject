package services.ship.admin;

import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.services.ship.admin.ApplyDiscountService;

import java.sql.SQLException;

public class ApplyDiscountServiceTest {

    private ApplyDiscountService applyDiscountService = ApplyDiscountService.getApplyDiscountService();

    private int ticketId;
    private int discount;

    @Test
    public void setDiscountTestCorrectDiscountAndTicketId(){
        ticketId = 23;
        discount = 10;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            //ticketDao.
            applyDiscountService.setDiscount(ticketId, discount);
        } catch(Exception e) {

        }
    }
}
