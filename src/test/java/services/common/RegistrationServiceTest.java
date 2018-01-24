package services.common;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.services.client.ShowCruisesService;
import sbitneva.services.common.RegistrationService;

import java.sql.SQLException;

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

        UserDao userDao = DaoFactory.getUserDao();
        try {
            userDao.addNewUser(firstName, lastName, email, password);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }

    }


}
