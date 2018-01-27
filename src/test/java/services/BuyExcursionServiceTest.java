package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketsExcursionsDao;
import sbitneva.entity.Excursion;
import sbitneva.services.client.BuyExcursionService;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BuyExcursionServiceTest {

    private static Logger log = Logger.getLogger(BuyExcursionServiceTest.class.getName());

    private final int TICKET_ID = 1;
    private final int EXCURSION_ID_EXISTED = 1;
    private final int EXCURSION_ID_NOT_EXISTED = 5;

    BuyExcursionService buyExcursionService = BuyExcursionService.getBuyTicketService();

    @Test
    public void buyRepeatExcursionForTicket() {
        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            ArrayList<Excursion> excursionsBefore = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_ID);
            buyExcursionService.buyExcursionForTicket(TICKET_ID, EXCURSION_ID_EXISTED);
            ArrayList<Excursion> excursionsAfter = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_ID);
            assertEquals(excursionsBefore.size(), excursionsAfter.size());
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

    @Test
    public void buyNewExcursionForTicket() {
        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            ArrayList<Excursion> excursionsBefore = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_ID);
            buyExcursionService.buyExcursionForTicket(TICKET_ID, EXCURSION_ID_NOT_EXISTED);
            ArrayList<Excursion> excursionsAfter = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_ID);
            assertEquals(excursionsBefore.size() + 1, excursionsAfter.size());
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
