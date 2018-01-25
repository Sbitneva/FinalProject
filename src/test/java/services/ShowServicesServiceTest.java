package services;

import org.junit.Test;
import sbitneva.dao.ComfortLevelDao;
import sbitneva.dao.DaoFactory;
import sbitneva.services.common.ShowServicesService;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ShowServicesServiceTest {


    private final int COMFORT_LEVEL_ID = 1;
    private ShowServicesService showServicesService = ShowServicesService.getShowTicketsService();

    @Test
    public void getServicesTest() {
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        try {
            int expectedServicesNumber = comfortLevelDao.getComfortLevelInfo(COMFORT_LEVEL_ID).size();
            int actualServicesNumber = showServicesService.getServices(COMFORT_LEVEL_ID).getServices().size();
            assertEquals(expectedServicesNumber, actualServicesNumber);
            assertEquals(7, actualServicesNumber);

        } catch (SQLException e) {

        }

    }
}
