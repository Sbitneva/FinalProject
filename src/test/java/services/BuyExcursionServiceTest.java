package services;

import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketsExcursionsDao;
import sbitneva.entity.Excursion;
import sbitneva.services.client.BuyExcursionService;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BuyExcursionServiceTest {
    BuyExcursionService buyExcursionService = BuyExcursionService.getBuyTicketService();

    private final int TICKET_WITH_USER_ID = 1;
    private final int EXCURSION_ID_EXISTED = 1;

    @Test
    public void buyExcursionForTicket(){
        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            ArrayList<Excursion> excursionsBefore = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_WITH_USER_ID);
            assertEquals(3, excursionsBefore.size());
            buyExcursionService.buyExcursionForTicket(TICKET_WITH_USER_ID, EXCURSION_ID_EXISTED);
            ArrayList<Excursion> excursionsAfter = ticketsExcursionsDao.getAllExcursionsByTicketId(TICKET_WITH_USER_ID);
            assertEquals(3, excursionsAfter.size());
        } catch (SQLException e){

        }
    }
}
