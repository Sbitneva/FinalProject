package sbitneva.configaration;

import sbitneva.command.factory.FactoryCommand;

import java.util.HashMap;
import java.util.Map;

public class SecurityConfiguration {
    private static final SecurityConfiguration config = new SecurityConfiguration();

    Map<String, Integer> grant = new HashMap<>();

    private static final int ALL_ACCESS = 4;
    private static final int GENERAL_LOG_ACCESS = 3;
    private static final int CLIENT_ACCESS = 1;
    private static final int SHIP_ADMIN_ACCESS = 2;

    public static final int CLIENT_TYPE = 1;
    public static final int SHIP_ADMIN_TYPE = 2;

    private SecurityConfiguration() {
        grant.put(FactoryCommand.LOGIN, ALL_ACCESS);
        grant.put(FactoryCommand.REGISTRATION, ALL_ACCESS);
        grant.put(FactoryCommand.LOGOUT, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.CLIENT, CLIENT_ACCESS);
        grant.put(FactoryCommand.SHOW_SHIP_COMMAND, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.BUY_TICKET, CLIENT_ACCESS);
        grant.put(FactoryCommand.SHOW_AVAILABLE_TICKETS, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.SHOW_AVAILABLE_CRUISES, CLIENT_ACCESS);
    }

    public static SecurityConfiguration getConfig() {
        return config;
    }

    public boolean hasCommand(String command){
        boolean result = false;
        if(this.grant.containsKey(command)) {
            result = true;
        }
        return result;
    }

    public boolean verifyRights(int userTypeId, String command){
        boolean result = true;
        int commandAccessType = grant.get(command);
        if ((commandAccessType == CLIENT_TYPE) || (commandAccessType == SHIP_ADMIN_ACCESS) || commandAccessType == GENERAL_LOG_ACCESS) {
            if (commandAccessType != GENERAL_LOG_ACCESS) {
                if (userTypeId != commandAccessType) {
                    result = false;
                }
            }

        }

        return result;
    }


}
