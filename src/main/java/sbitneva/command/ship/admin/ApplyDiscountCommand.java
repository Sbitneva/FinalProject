package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.services.ship.admin.ApplyDiscountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class ApplyDiscountCommand implements Command {

    private static Logger log = Logger.getLogger(ApplyDiscountCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ApplyDiscountCommand execution started");
        int ticketId = getTicketId(request);
        if (ticketId > 0) {
            ApplyDiscountService applyDiscountService = ApplyDiscountService.getApplyDiscountService();
            int discount = getDiscount(request);
            if (discount != -1) {
                int shipId = getShipIdFromSession(request);
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

    private int getTicketId(HttpServletRequest request) {
        int ticketId = 0;
        if (request.getParameter(TICKET_ID) != null) {
            try {
                ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            log.debug("ticketId = " + ticketId);
        }
        return ticketId;
    }

    private int getDiscount(HttpServletRequest request) {
        int discount = -1;
        if (request.getParameter(DISCOUNT) != null) {
            try {
                discount = Integer.parseInt(request.getParameter(DISCOUNT));
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            log.debug("discount = " + discount);
        }
        return discount;
    }

    private int getShipIdFromSession(HttpServletRequest request) {
        int shipId = 0;

        if (request.getSession().getAttribute(SHIP_ID) != null) {
            try {
                shipId = Integer.parseInt(request.getSession().getAttribute(SHIP_ID).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            }
        }
        log.debug("shipId = " + shipId);
        return shipId;
    }

    private int getPage(HttpServletRequest request) {
        int page = 1;
        if (request.getAttribute(PAGE) != null) {
            try {
                page = Integer.parseInt(request.getSession().getAttribute(PAGE).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            }
        }
        log.debug("page = " + page);
        return page;
    }
}
