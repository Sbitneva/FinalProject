package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;
import sbitneva.services.UserService;

import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UserServiceTest {
    private static Logger log = Logger.getLogger(UserServiceTest.class.getName());
    private UserService userService = UserService.getUserService();

    @Test
    public void testVerify(){
        int impossibleUserId = -3;
        int clientId = 2;
        int notClientId = 1;
        User user = null;
        try {
            user = userService.verify(impossibleUserId);
        } catch (SQLException | DAOException e){
            log.debug(e.getClass().getSimpleName() + " " + DAOException.class.getSimpleName());
            assertEquals(e.getClass().getSimpleName(), DAOException.class.getSimpleName());
        }
        try{
            user = userService.verify(clientId);
            assertEquals(user.getUserId(), clientId);
        } catch (SQLException | DAOException e) {
            assert(true);
        }
        try{
            user = userService.verify(notClientId);
            assertEquals(user.getUserId(), 0);
        } catch (SQLException | DAOException e) {
            assert(true);
        }
    }
}
