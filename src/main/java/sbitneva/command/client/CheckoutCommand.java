package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.entity.Ticket;
import sbitneva.services.client.CheckoutService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static sbitneva.command.CommandsHelper.*;

public class CheckoutCommand implements Command {
    private static Logger log = Logger.getLogger(CheckoutCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUserId(request);
        if(userId > 0) {
            CheckoutService checkoutService = CheckoutService.getCheckoutService();
            boolean isSuccess = checkoutService.doCheckout(userId);
            if(isSuccess) {
                request.getRequestDispatcher(CHECKOUT_SUCCESS_PAGE).forward(request,response);
            } else {
                request.getRequestDispatcher(SERVLET_NAME + CART_COMMAND).forward(request, response);
            }
        }
    }
    private int getUserId(HttpServletRequest request) {
        int userId = 0;
        if(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE) != null) {
            try {
                userId = Integer.parseInt(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE).toString());
            } catch (NumberFormatException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return userId;
    }
}
