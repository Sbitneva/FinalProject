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
        boolean success = false;
        int ticketId = getTicketId(request);
        if(ticketId > 0) {
            ApplyDiscountService applyDiscountService = ApplyDiscountService.getApplyDiscountService();
            int discount = getDiscount(request);
            if(discount != -1) {
                boolean result = applyDiscountService.setDiscount(ticketId, discount);
                if(result){
                    int shipId = getShipIdFromSession(request);
                    if(shipId > 0) {
                        success = true;
                        log.debug(request.getContextPath());
                        int page = getPage(request);

                        request.setAttribute(PAGE, page);
                        request.getRequestDispatcher(SERVLET_NAME + SHOW_SHIP_COMMAND).forward(request, response);
                    }
                }
            }
        }
        if(!success) {
            //TODO:error page
        }
    }

    private int getTicketId(HttpServletRequest request){
        int ticketId = 0;
        if(request.getParameter(TICKET_ID) != null) {
            ticketId = Integer.parseInt(request.getParameter(TICKET_ID));
            log.debug("ticketId = " + ticketId);
        }
        return ticketId;
    }

    private int getDiscount(HttpServletRequest request){
        int discount = -1;
        if(request.getParameter(DISCOUNT) != null) {
            discount = Integer.parseInt(request.getParameter(DISCOUNT));
            log.debug("discount = " + discount);
        }
        return discount;
    }

    private int getShipIdFromSession(HttpServletRequest request) {
        int shipId = 0;
        if(request.getSession().getAttribute(SHIP_ID) != null) {
            shipId = Integer.parseInt(request.getSession().getAttribute(SHIP_ID).toString());

        }
        log.debug("shipId = " + shipId);
        return shipId;
    }

    private int getPage(HttpServletRequest request) {
        int page = 1;
        if(request.getAttribute(PAGE) != null) {
            page = Integer.parseInt(request.getSession().getAttribute(PAGE).toString());
        }
        log.debug("page = " + page);
        return page;
    }
}
