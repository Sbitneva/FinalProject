package sbitneva.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandsHelper {

    private static Logger log = Logger.getLogger(CommandsHelper.class.getName());

    private static CommandsHelper commandsHelper = new CommandsHelper();
    public static String ID_SESSION_ATTRIBUTE = "id";
    public static String USER_TYPE_SESSION_ATTRIBUTE = "type";

    private CommandsHelper(){

    }
    public static CommandsHelper getCommandsHelper() {
        return commandsHelper;
    }

    public boolean verifySession(HttpServletRequest request){
        boolean result = false;
        if(request.getSession().getAttribute(ID_SESSION_ATTRIBUTE) != null) {
            try {
                int clientId = Integer.parseInt(request.getSession().getAttribute("id").toString());
                log.debug("client id = " + clientId);
                if(clientId > 0) {
                    result = true;
                }
            } catch (NumberFormatException e){
                log.error("session attribute id must be a number;");
            }
        }
        return result;
    }

}
