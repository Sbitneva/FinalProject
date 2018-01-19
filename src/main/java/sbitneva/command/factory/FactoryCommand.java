package sbitneva.command.factory;

import org.apache.log4j.Logger;
import sbitneva.command.*;
import sbitneva.command.client.ShowClientInfoCommand;
import sbitneva.command.client.ShowCruisesCommand;
import sbitneva.command.common.LoginCommand;
import sbitneva.command.common.LogoutCommand;
import sbitneva.command.common.RegistrationCommand;
import sbitneva.command.common.ShowTicketsCommand;
import sbitneva.command.ship.admin.ShipAdminCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class FactoryCommand {

    private static Logger log = Logger.getLogger(FactoryCommand.class.getName());

    public static final String SERVLET_PATH = "/Cruise";

    private static final String PARAM_NAME_COMMAND = "command";

    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";
    public static final String LOGOUT = "logout";
    public static final String CLIENT = "client";
    public static final String SHOW_AVAILABLE_TICKETS = "getTickets";
    public static final String SHOW_AVAILABLE_CRUISES = "getCruises";
    //public static final String USERS = "users";
    public static final String SHIP_ADMIN = "shipAdmin";
        //private static final String COMFORT = "comfortInfo";

    public static final String BUY_TICKET = "buyTicket";
    public static final String BUY_EXCURSION = "buyExcursion";
    public static final FactoryCommand factoryCommand = new FactoryCommand();

    private Map<String, Command> commandMap = new HashMap<>();

    private FactoryCommand() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(CLIENT, new ShowClientInfoCommand());
       // commandMap.put(BUY_TICKET, new AddTicketToCartCommand());
        commandMap.put(SHOW_AVAILABLE_TICKETS, new ShowTicketsCommand());
        commandMap.put(SHOW_AVAILABLE_CRUISES, new ShowCruisesCommand());
        //commandMap.put(BUY_EXCURSION, new BuyExcursionCommand());
        commandMap.put(SHIP_ADMIN, new ShipAdminCommand());
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
