package services;

import org.junit.Test;
import sbitneva.entity.Client;
import sbitneva.services.common.LoginService;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LoginServiceTest {
    private LoginService loginService = LoginService.getLoginService();

    private String email;
    private String password;

    @Test
    public void getUserTestWithCorrectData() {

        email = "alina.vasilenko@gmail.com";
        password = "1234";

        Client client = loginService.getUser(email, password);

        assertNotNull(client);
        if (client != null) {
            assertEquals(2, client.getClientId());
        }
    }

    @Test
    public void getUserTestWithWrongPassword() {

        email = "alina.vasilenko@gmail.com";
        password = "1567";

        Client client = loginService.getUser(email, password);
        assertNull(client);

    }
}
