package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.client.ShowCruisesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ShowCruisesCommand implements Command{

    private static Logger log = Logger.getLogger(ShowCruisesCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowCruisesCommand execution started");
        try{
            ShowCruisesService showCruisesService = ShowCruisesService.getShowCruisesService();
            ArrayList<Ship> ships = showCruisesService.getCruiseShips();
            request.setAttribute(CommandsHelper.SHIPS, ships);
            request.getRequestDispatcher(CommandsHelper.CRUISES_LIST_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
