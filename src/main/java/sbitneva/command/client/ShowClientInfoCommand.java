package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.command.CommandsHelper;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.services.client.ShowClientInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ShowClientInfoCommand implements Command {
    private static Logger log = Logger.getLogger(ShowClientInfoCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = false;

        ShowClientInfoService showClientInfoService = ShowClientInfoService.getShowClientInfoService();
        int clientId = Integer.parseInt(request.getSession().getAttribute(
                CommandsHelper.USER_ID_SESSION_ATTRIBUTE).toString());
        try{
            Client client = showClientInfoService.getClient(clientId);
            if(client != null) {
                success = true;
                request.setAttribute(CommandsHelper.CLIENT, client);
                request.getRequestDispatcher(CommandsHelper.CLIENT_INFO_PAGE).forward(request, response);
            }
        } catch (SQLException | DaoException e) {
            log.error(e.getMessage());
        }
        if(!success) {
            request.setAttribute(CommandsHelper.ERRORS, "No user with given email and password");
            request.getRequestDispatcher(CommandsHelper.MAIN_PAGE).forward(request, response);
        }
    }
}
