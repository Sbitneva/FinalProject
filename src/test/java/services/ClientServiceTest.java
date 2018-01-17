package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.services.ShowClientInfoService;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ClientServiceTest {
    private static Logger log = Logger.getLogger(ClientServiceTest.class.getName());
    private ShowClientInfoService showClientInfoService = ShowClientInfoService.getShowClientInfoService();

    @Test
    public void testVerify(){
        int impossibleUserId = -3;
        int clientId = 2;
        int notClientId = 1;
        Client client = null;
        try {
            client = showClientInfoService.getClient(impossibleUserId);
        } catch (SQLException | DaoException e){
            log.debug(e.getClass().getSimpleName() + " " + DaoException.class.getSimpleName());
            assertEquals(e.getClass().getSimpleName(), DaoException.class.getSimpleName());
        }
        try{
            client = showClientInfoService.getClient(clientId);
            assertEquals(client.getClientId(), clientId);
        } catch (SQLException | DaoException e) {
            assert(true);
        }
        try{
            client = showClientInfoService.getClient(notClientId);
            assertEquals(client.getClientId(), 0);
        } catch (SQLException | DaoException e) {
            assert(true);
        }
    }
}
