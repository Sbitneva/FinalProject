package sbitneva.services.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

public class ApplyDiscountService {

    private static Logger log = Logger.getLogger(ApplyDiscountService.class.getName());

    private static ApplyDiscountService applyDiscountService = new ApplyDiscountService();

    private ApplyDiscountService() {

    }

    public static ApplyDiscountService getApplyDiscountService() {
        return applyDiscountService;
    }

    public boolean setDiscount(int ticketId, int discount, int shipId) {
        boolean result = false;
        if ((discount >= 0) && (discount <= 99)) {
            TicketDao ticketDao = DaoFactory.getTicketDao();
            try {
                TransactionManager.beginTransaction();
                int n = ticketDao.updateDiscount(ticketId, discount, shipId);
                TransactionManager.endTransaction();
                if (n == 1) {
                    result = true;
                }

            } catch (SQLException | TransactionException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                try {
                    TransactionManager.endTransaction();
                } catch (TransactionException | SQLException e1) {
                    log.error(e1.getClass().getSimpleName() + " : " + e1.getMessage());
                }
            }
        }
        return result;
    }
}
