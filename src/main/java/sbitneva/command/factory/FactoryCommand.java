package sbitneva.command.factory;

import org.apache.log4j.Logger;
import sbitneva.command.client.ShowClientInfoCommand;
import sbitneva.command.client.ShowCruisesCommand;
import sbitneva.command.common.LoginCommand;
import sbitneva.command.common.LogoutCommand;
import sbitneva.command.common.RegistrationCommand;
import sbitneva.command.common.ShowServicesCommand;
import sbitneva.command.ship.admin.ShowShipTicketsCommand;
import sbitneva.command.ship.admin.ShowStaffCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class FactoryCommand {

    private static Logger log = Logger.getLogger(FactoryCommand.class.getName());

    private static final String PARAM_NAME_COMMAND = "command";

    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";
    public static final String LOGOUT = "logout";
    public static final String CLIENT = "client";
    public static final String SHOW_AVAILABLE_TICKETS = "getTickets";
    public static final String SHOW_AVAILABLE_CRUISES = "getCruises";
    public static final String SHOW_SERVICES = "getServices";
    public static final String SHOW_SHIP_COMMAND = "showShip";
    public static final String BUY_TICKET = "buyTicket";
    public static final String SHOW_STAFF = "getStaff";
    public static final FactoryCommand factoryCommand = new FactoryCommand();

    private Map<String, Command> commandMap = new HashMap<>();

    private FactoryCommand() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(CLIENT, new ShowClientInfoCommand());
        //commandMap.put(BUY_TICKET, new AddTicketToCartCommand());
        //commandMap.put(SHOW_AVAILABLE_TICKETS, new ShowTicketsCommand());
        commandMap.put(SHOW_AVAILABLE_CRUISES, new ShowCruisesCommand());
        commandMap.put(SHOW_STAFF, new ShowStaffCommand());
        commandMap.put(SHOW_SHIP_COMMAND, new ShowShipTicketsCommand());
        commandMap.put(SHOW_SERVICES, new ShowServicesCommand());
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
