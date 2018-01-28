package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketsExcursionsDao;
import sbitneva.exception.DaoException;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;

/**
 * Service: buy excursion.
 */
public final class BuyExcursionService {

    private static Logger log = Logger.getLogger(BuyExcursionService.class.getName());

    private static BuyExcursionService buyExcursionService = new BuyExcursionService();

    private BuyExcursionService() {

    }

    /**
     * Get BuyExcursionService instance.
     *
     * @return BuyExcursionService instance
     */
    public static BuyExcursionService getBuyTicketService() {
        return buyExcursionService;
    }

    /**
     * Buy excursion for ticket.
     *
     * @param ticketId Ticket ID
     * @param excursionId Excursion ID
     */
    public void buyExcursionForTicket(final int ticketId, final int excursionId) {
        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            TransactionManager.beginTransaction();
            int result = ticketsExcursionsDao.updateCell(ticketId, excursionId);
            TransactionManager.endTransaction();
            if (result == 1) {
                log.debug("Buy excursion query executed");
            } else {
                log.debug("Buy excursion query fault");
            }
        } catch (SQLException | DaoException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

}
