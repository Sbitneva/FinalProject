package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ship;
import sbitneva.services.client.ShowCruisesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Command: show cruises.
 */
public class ShowCruisesCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(ShowCruisesCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("ShowCruisesCommand execution started");

        ShowCruisesService showCruisesService = ShowCruisesService.getShowCruisesService();
        ArrayList<Ship> ships = showCruisesService.getCruiseShips();
        request.setAttribute(SHIPS, ships);
        request.getRequestDispatcher(CRUISES_LIST_PAGE).forward(request, response);
    }
}
