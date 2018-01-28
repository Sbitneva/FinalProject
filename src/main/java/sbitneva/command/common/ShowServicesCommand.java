package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.ComfortLevel;
import sbitneva.services.common.ShowServicesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: show services.
 */
public class ShowServicesCommand extends BasicCommand implements Command {
    private static Logger log = Logger.getLogger(RegistrationCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("ShowServicesCommand execution started");
        boolean success = false;
        int comfortLevelId = getIntParameter(request, COMFORT_ID);
        if (comfortLevelId > 0) {
            ShowServicesService showServicesService = ShowServicesService.getShowTicketsService();
            ComfortLevel comfortLevel = showServicesService.getServices(comfortLevelId);
            if (comfortLevel != null) {
                success = true;
                log.debug(comfortLevel.toString());
                request.setAttribute(SERVICES, comfortLevel);
                request.getRequestDispatcher(SERVICES_PAGE).forward(request, response);
            }
        }
        if (!success) {
            request.getRequestDispatcher(PAGE_NOT_FOUND_PAGE).forward(request, response);
        }
    }
}
