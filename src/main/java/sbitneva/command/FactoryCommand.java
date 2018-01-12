package sbitneva.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class FactoryCommand {
    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";
    public static final String LOGOUT = "logout";
    public static final String USERS = "users";
    public static final String COMFORT = "comfortInfo";
    private static final String PARAM_NAME_COMMAND = "command";
    private static final String BUY_TICKET = "buyTicket";
    private static final String BUY_EXCURSION = "buyExcursion";
    private static final FactoryCommand factoryCommand = new FactoryCommand();
    static Logger log = Logger.getLogger(FactoryCommand.class.getName());
    private Map<String, Command> commandMap = new HashMap<>();

    private FactoryCommand() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(USERS, new UserCommand());
        commandMap.put(BUY_TICKET, new BuyTicketCommand());
        commandMap.put(COMFORT, new ComfortCommand());
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
        }

        return commandMap.get(requestCommand);
    }

}
