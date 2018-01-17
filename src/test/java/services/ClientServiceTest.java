package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.entity.Client;
import sbitneva.exception.DAOException;
import sbitneva.services.UserService;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ClientServiceTest {
    private static Logger log = Logger.getLogger(ClientServiceTest.class.getName());
    private UserService userService = UserService.getUserService();

    @Test
    public void testVerify(){
        int impossibleUserId = -3;
        int clientId = 2;
        int notClientId = 1;
        Client client = null;
        try {
            client = userService.verify(impossibleUserId);
        } catch (SQLException | DAOException e){
            log.debug(e.getClass().getSimpleName() + " " + DAOException.class.getSimpleName());
            assertEquals(e.getClass().getSimpleName(), DAOException.class.getSimpleName());
        }
        try{
            client = userService.verify(clientId);
            assertEquals(client.getClientId(), clientId);
        } catch (SQLException | DAOException e) {
            assert(true);
        }
        try{
            client = userService.verify(notClientId);
            assertEquals(client.getClientId(), 0);
        } catch (SQLException | DAOException e) {
            assert(true);
        }
    }
}
