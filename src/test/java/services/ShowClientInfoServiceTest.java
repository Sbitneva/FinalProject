package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.exception.TransactionException;
import sbitneva.services.client.ShowClientInfoService;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ShowClientInfoServiceTest {

    private static Logger log = Logger.getLogger(ShowClientInfoServiceTest.class.getName());

    private final int USER_ID = 2;
    private final int ADMIN_ID = 1;
    private ShowClientInfoService showClientInfoService = ShowClientInfoService.getShowClientInfoService();

    @Test
    public void getClientTest() throws TransactionException {
        try {
            /**
             * Get client with client id
             */
            Client client = showClientInfoService.getClient(USER_ID);
            assertNotNull(client);

            /**
             * Get client with admin id
             */
            client = showClientInfoService.getClient(ADMIN_ID);
            assertNull(client);
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
