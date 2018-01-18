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

    public static boolean isParameterAcceptableInteger(String parameter){
        boolean result = false;
        try {
            int intVal = Integer.parseInt(parameter);
            if(intVal > 0) {
                result = true;
            }
        } catch (NumberFormatException e){
            log.error("session attribute id must be a number;");
        }
        return result;
    }

}
