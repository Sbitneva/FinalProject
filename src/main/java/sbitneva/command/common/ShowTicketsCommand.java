package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.Command;
import sbitneva.command.CommandsHelper;
import sbitneva.dao.UserDao;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.services.ShowClientInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ShowTicketsCommand implements Command {

    private static Logger log = Logger.getLogger(UserDao.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandsHelper commandsHelper = CommandsHelper.getCommandsHelper();
        if( commandsHelper.verifySession(request)) {

        } else{
            //TODO:redirect to error page
        }
        //TODO:redirect to error page
        /*if(commandsHelper.verifySession(request)) {
            ShowClientInfoService showClientInfoService = ShowClientInfoService.getShowClientInfoService();
            int clientId = Integer.parseInt(request.getSession().getAttribute(CLIENT_ID_ATTRIBUTE).toString());
            try{
                Client client = showClientInfoService.getClient(clientId);
                if(client != null) {
                    request.setAttribute(CLIENT_ATTRIBUTE, client);
                    request.getRequestDispatcher(CLIENT_PAGE_PATH).forward(request, response);
                }
            } catch (SQLException | DaoException e) {
                log.error(e.getMessage());
            }

        }*/
    }
}
