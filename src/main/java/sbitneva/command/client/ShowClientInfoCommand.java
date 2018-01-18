package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.command.CommandsHelper;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.services.ShowClientInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ShowClientInfoCommand implements Command {
    private static Logger log = Logger.getLogger(ShowClientInfoCommand.class.getName());

    private static final String CLIENT_PAGE_PATH = "jsp/client/client-page.jsp";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String CLIENT_ID_ATTRIBUTE = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandsHelper commandsHelper = CommandsHelper.getCommandsHelper();

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


    }
}
