package sbitneva.configaration;

import org.apache.log4j.Logger;
import sbitneva.command.factory.FactoryCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Security configuration filter.
 */
public final class SecurityConfiguration {

    /**
     * Usual customers or clients.
     */
    public static final int CLIENT_TYPE = 1;
    /**
     * Ship administrators.
     */
    public static final int SHIP_ADMIN_TYPE = 2;
    private static final int ALL_ACCESS = 4;
    private static final int GENERAL_LOG_ACCESS = 3;
    private static final int CLIENT_ACCESS = 1;
    private static final int SHIP_ADMIN_ACCESS = 2;
    private static final SecurityConfiguration CONFIG = new SecurityConfiguration();
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

    /**
     * Security configuration getter.
     *
     * @return Security configuration descriptor
     */
    public static SecurityConfiguration getConfig() {
        return CONFIG;
    }

    /**
     * Does such command exists?
     *
     * @param command Command name
     * @return Exists or not
     */
    public boolean hasCommand(final String command) {

        boolean result = false;

        if (grant.containsKey(command)) {
            result = true;
        }

        return result;
    }

    /**
     * Is command permitted for concrete type user?
     *
     * @param userTypeId User type identifier
     * @param command    Command name
     * @return Authorized or not
     */
    public boolean verifyRights(final int userTypeId, final String command) {

        boolean result = true;
        int commandAccessType = 0;
        if (grant.get(command) != null) {
            commandAccessType = grant.get(command);
            if ((commandAccessType < 1) || (commandAccessType > 4)) {
                return false;
            }
        }
        if (commandAccessType != ALL_ACCESS) {
            if (commandAccessType != GENERAL_LOG_ACCESS) {
                if (userTypeId != commandAccessType) {
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * If command permitted for non-authorized user?
     *
     * @param command Command name
     * @return Authorized or not
     */
    public boolean isCommandForNotAuth(final String command) {

        boolean result = false;
        if (grant.get(command) != null) {
            if (grant.get(command) == ALL_ACCESS) {
                result = true;
            }
        }

        return result;
    }
}
