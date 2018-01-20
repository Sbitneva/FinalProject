package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.entity.ComfortLevel;
import sbitneva.services.common.ShowServicesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class ShowServicesCommand implements Command {
    private static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowServicesCommand execution started");
        int comfortLevelId = getComfortLevelId(request);
        if(comfortLevelId > 0) {
            ShowServicesService showServicesService = ShowServicesService.getShowTicketsService();
            ComfortLevel comfortLevel = showServicesService.getServices(comfortLevelId);
            if(comfortLevel != null) {
                log.debug(comfortLevel.toString());
                request.setAttribute(SERVICES, comfortLevel);
                request.getRequestDispatcher(SERVICES_PAGE).forward(request, response);
            }
        }
    }

    private int getComfortLevelId(HttpServletRequest request) {
        int id = 0;
        if(request.getParameter(COMFORT_ID) != null) {
            try{
                id = Integer.parseInt(request.getParameter(COMFORT_ID));
            } catch (NumberFormatException e){
                log.error(e.getClass().getSimpleName() + e.getMessage());
            }
        }
        return id;
    }
}
