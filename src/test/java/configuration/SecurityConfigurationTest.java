package configuration;

import org.junit.Test;
import sbitneva.command.factory.FactoryCommand;
import sbitneva.configaration.SecurityConfiguration;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import static sbitneva.configaration.SecurityConfiguration.*;
import static sbitneva.command.factory.FactoryCommand.*;

public class SecurityConfigurationTest {

    @Test
    public void hasCommandTest(){
        SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();

        boolean test1 = securityConfiguration.hasCommand("euefhdhf");
        assertFalse(test1);

        boolean test2 = securityConfiguration.hasCommand(null);
        assertFalse(test2);

        boolean test3 = securityConfiguration.hasCommand("login");
        assertTrue(test3);
    }

    @Test
    public void verifyRightsTest(){

        SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();

        boolean test1 = securityConfiguration.verifyRights(SHIP_ADMIN_TYPE, LOGIN);
        assertTrue(test1);

        boolean test2 = securityConfiguration.verifyRights(SHIP_ADMIN_TYPE, ADD_TO_CART);
        assertFalse(test2);

        boolean test3 = securityConfiguration.verifyRights(SHIP_ADMIN_TYPE, CHECKOUT);
        assertFalse(test3);

        boolean test4 = securityConfiguration.verifyRights(SHIP_ADMIN_TYPE, "kjdkijjfj");
        assertFalse(test4);

        boolean test5 = securityConfiguration.verifyRights(CLIENT_TYPE, SHOW_CART);
        assertTrue(test5);
    }

    @Test
    public void isCommandForNotAuthTest(){

        SecurityConfiguration securityConfiguration = SecurityConfiguration.getConfig();

        boolean test1 = securityConfiguration.isCommandForNotAuth(LOGIN);
        assertTrue(test1);

        boolean test2 = securityConfiguration.isCommandForNotAuth(ADD_TO_CART);
        assertFalse(test2);

        boolean test3 = securityConfiguration.isCommandForNotAuth(CHECKOUT);
        assertFalse(test3);

        boolean test4 = securityConfiguration.isCommandForNotAuth("kjdkijjfj");
        assertFalse(test4);

        boolean test5 = securityConfiguration.isCommandForNotAuth(SHOW_CART);
        assertFalse(test5);
    }
}
