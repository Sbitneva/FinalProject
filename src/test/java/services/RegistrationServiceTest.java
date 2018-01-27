package services;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.services.common.RegistrationService;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RegistrationServiceTest {

    private static Logger log = Logger.getLogger(RegistrationServiceTest.class.getName());

    private RegistrationService registrationService = RegistrationService.getRegistrationService();

    /**
     * Data for user registration with already existed email
     */
    private String firstName = "Катя";
    private String lastName = "Боброва";
    private String email;
    private String password;

    @Test
    public void registerTestAlreadyExistedEmail() {
        /**
         * Init data for user registration with already existed email
         */

        email = "alina.vasilenko@gmail.com";
        password = "5678";

        try {
            int userId = registrationService.register(firstName, lastName, email, password);
            assertEquals(0, userId);
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }


    }

    /**
     * Register user with wrong email string
     */
    @Test
    public void registerTestNotEmailString() {
        email = "alina.vasilenko@gmail";
        password = "5678";

        try {
            int userId = registrationService.register(firstName, lastName, email, password);
            assertEquals(0, userId);
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }

    }

    /**
     * Register user with right data
     */
    @Test
    public void registerTestRightData() {
        email = "katya@gmail.com";
        password = "5678";
        try {
            int userId = registrationService.register(firstName, lastName, email, password);
            assertTrue(userId > 0);
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }
}
