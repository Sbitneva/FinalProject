package sbitneva.command.factory;

import org.apache.log4j.Logger;
import sbitneva.command.client.BuyExcursionCommand;
import sbitneva.command.client.ShowClientInfoCommand;
import sbitneva.command.client.ShowCruisesCommand;
import sbitneva.command.client.ShowExcursionsCommand;
import sbitneva.command.common.LoginCommand;
import sbitneva.command.common.LogoutCommand;
import sbitneva.command.common.RegistrationCommand;
import sbitneva.command.common.ShowServicesCommand;
import sbitneva.command.ship.admin.ApplyDiscountCommand;
import sbitneva.command.ship.admin.ShowShipTicketsCommand;
import sbitneva.command.ship.admin.ShowStaffCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Commands factory
 */

public class FactoryCommand {
    /**
     * Commands parameters :
     */

    public static final String REGISTRATION = "registration";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String GET_SERVICES = "getServices";
    public static final String SHOW_SHIP = "showShip";
    public static final String CLIENT = "client";
    public static final String GET_CRUISES = "getCruises";
    public static final String GET_EXCURSIONS = "getExcursions";
    public static final String BUY_EXCURSION = "buyExcursion";
    public static final String GET_STAFF = "getStaff";
    public static final String SET_DISCOUNT = "setDiscount";
    private static final String PARAM_NAME_COMMAND = "command";
    private static final FactoryCommand factoryCommand = new FactoryCommand();
    private static Logger log = Logger.getLogger(FactoryCommand.class.getName());
    private Map<String, Command> commandMap = new HashMap<>();

    private FactoryCommand() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(CLIENT, new ShowClientInfoCommand());
        commandMap.put(SET_DISCOUNT, new ApplyDiscountCommand());
        commandMap.put(GET_EXCURSIONS, new ShowExcursionsCommand());
        commandMap.put(GET_CRUISES, new ShowCruisesCommand());
        commandMap.put(GET_STAFF, new ShowStaffCommand());
        commandMap.put(SHOW_SHIP, new ShowShipTicketsCommand());
        commandMap.put(GET_SERVICES, new ShowServicesCommand());
        commandMap.put(BUY_EXCURSION, new BuyExcursionCommand());
    }

    public static FactoryCommand getInstance() {
        return factoryCommand;
    }

    public Command getCommand(HttpServletRequest request) {
        String requestCommand = request.getParameter(PARAM_NAME_COMMAND);
        log.debug(request.getRequestURI());
        if (requestCommand == null) {
            return null;
        } else {
            log.debug(commandMap.get(requestCommand));
        }

        return commandMap.get(requestCommand);
    }

}
