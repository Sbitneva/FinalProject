package sbitneva.command;

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
    private static final FactoryCommand factoryCommand = new FactoryCommand();
    private Map<String, Command> commandMap = new HashMap<>();

    private FactoryCommand() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(REGISTRATION, new RegistrationCommand());
        commandMap.put(USERS, new UserCommand());
        commandMap.put(BUY_TICKET, new BuyTicketCommand());
        //commandMap.put(LOGOUT, new LogoutCommand());
        //commandMap.put(USERS, new UsersCommand());
    }

    public static FactoryCommand getInstance() {
        return factoryCommand;
    }

    public Command getCommand(HttpServletRequest request) {
        String requestCommand = request.getParameter(PARAM_NAME_COMMAND);
        if (requestCommand == null) {
            return null;
        }

        return commandMap.get(requestCommand);
    }

}
