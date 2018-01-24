package sbitneva.configaration;

import org.apache.log4j.Logger;
import sbitneva.command.factory.FactoryCommand;

import java.util.HashMap;
import java.util.Map;

public class SecurityConfiguration {

    public static final int CLIENT_TYPE = 1;
    public static final int SHIP_ADMIN_TYPE = 2;
    private static final int ALL_ACCESS = 4;
    private static final int GENERAL_LOG_ACCESS = 3;
    private static final int CLIENT_ACCESS = 1;
    private static final int SHIP_ADMIN_ACCESS = 2;
    private static final SecurityConfiguration config = new SecurityConfiguration();
    private static Logger log = Logger.getLogger(SecurityConfiguration.class.getName());
    private Map<String, Integer> grant = new HashMap<>();

    private SecurityConfiguration() {
        grant.put(FactoryCommand.LOGIN, ALL_ACCESS);
        grant.put(FactoryCommand.REGISTRATION, ALL_ACCESS);
        grant.put(FactoryCommand.LOGOUT, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.CLIENT, CLIENT_ACCESS);
        grant.put(FactoryCommand.SHOW_SHIP, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.BUY_EXCURSION, CLIENT_ACCESS);
        grant.put(FactoryCommand.ADD_TO_CART, CLIENT_ACCESS);
        grant.put(FactoryCommand.GET_CRUISES, CLIENT_ACCESS);
        grant.put(FactoryCommand.GET_SERVICES, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.GET_STAFF, SHIP_ADMIN_ACCESS);
        grant.put(FactoryCommand.SET_DISCOUNT, SHIP_ADMIN_ACCESS);
        grant.put(FactoryCommand.GET_EXCURSIONS, CLIENT_ACCESS);
        grant.put(FactoryCommand.SHOW_CART, CLIENT_ACCESS);
        grant.put(FactoryCommand.CHECKOUT, CLIENT_ACCESS);
    }

    public static SecurityConfiguration getConfig() {
        return config;
    }

    public boolean hasCommand(String command) {

        boolean result = false;

        if (this.grant.containsKey(command)) {
            result = true;
        }

        return result;
    }

    public boolean verifyRights(int userTypeId, String command) {

        boolean result = true;
        int commandAccessType = grant.get(command);

        if (commandAccessType != ALL_ACCESS) {
            if (commandAccessType != GENERAL_LOG_ACCESS) {
                if (userTypeId != commandAccessType) {
                    result = false;
                }
            }
        }

        return result;
    }

    public boolean isCommandForNotAuth(String command) {
        boolean result = false;
        if (grant.get(command) != null) {
            if (grant.get(command) == ALL_ACCESS) {
                result = true;
            }
        }
        return result;
    }
}
