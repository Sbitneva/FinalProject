package sbitneva.configaration;

import sbitneva.command.factory.FactoryCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecurityConfiguration {
    private static final SecurityConfiguration config = new SecurityConfiguration();

    Map<String, Integer> grant = new HashMap<>();

    private static final int ALL_ACCESS = 4;
    private static final int GENERAL_LOG_ACCESS = 3;
    private static final int CLIENT_ACCES = 1;
    private static final int SHIP_ADMIN_ACCES = 2;

    public static final int CLIENT_TYPE = 1;
    public static final int SHIP_ADMIN_TYPE = 2;

    private SecurityConfiguration() {
        grant.put(FactoryCommand.LOGIN, ALL_ACCESS);
        grant.put(FactoryCommand.REGISTRATION, ALL_ACCESS);
        grant.put(FactoryCommand.LOGOUT, GENERAL_LOG_ACCESS);
        grant.put(FactoryCommand.CLIENT, CLIENT_ACCES);
        grant.put(FactoryCommand.SHIP_ADMIN, SHIP_ADMIN_ACCES);
        grant.put(FactoryCommand.BUY_TICKET, CLIENT_ACCES);
        grant.put(FactoryCommand.SHOW_AVAILABLE_TICKETS, CLIENT_ACCES);
        grant.put(FactoryCommand.SHOW_AVAILABLE_CRUISES, CLIENT_ACCES);
    }

    public static SecurityConfiguration getConfig() {
        return config;
    }

    public int getAccess(String userType){
        int access = 0;
        Integer userTypeId = grant.get(userType);
        if(userTypeId == null) {
            return access;
        } else {
            return userTypeId;
        }
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
        if ((commandAccessType == CLIENT_TYPE) || (commandAccessType == SHIP_ADMIN_ACCES )) {
            if(userTypeId != commandAccessType) {
                result = false;
            }
        }

        return result;
    }


}
