package sbitneva.command.client;

import org.apache.log4j.Logger;
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

    private static final String SHOW_CRUISES_PAGE = "jsp/client/cruises.jsp";
    private static final String SHIP_PARAMETER_NAME = "ships";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowCruisesCommand execution started");
        try{
            ShowCruisesService showCruisesService = ShowCruisesService.getShowCruisesService();
            ArrayList<Ship> ships = showCruisesService.getCruiseShips();
            request.setAttribute(SHIP_PARAMETER_NAME, ships);
            request.getRequestDispatcher(SHOW_CRUISES_PAGE).forward(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}