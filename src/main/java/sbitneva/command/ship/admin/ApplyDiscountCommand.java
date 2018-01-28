package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.services.ship.admin.ApplyDiscountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: apply discount.
 */
public class ApplyDiscountCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(ApplyDiscountCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("ApplyDiscountCommand execution started");
        int ticketId = getIntParameter(request, TICKET_ID);
        if (ticketId > 0) {
            ApplyDiscountService applyDiscountService = ApplyDiscountService.getApplyDiscountService();
            int discount = getIntParameter(request, DISCOUNT);
            if (discount != -1) {
                int shipId = getSessionAttribute(request, SHIP_ID);
                if (shipId > 0) {
                    applyDiscountService.setDiscount(ticketId, discount, shipId);
                    log.debug(request.getContextPath());
                    int page = getPage(request);
                    request.setAttribute(PAGE, page);
                    request.getRequestDispatcher(SERVLET_NAME + SHOW_SHIP_COMMAND).forward(request, response);
                }
            }
        }
    }
}
