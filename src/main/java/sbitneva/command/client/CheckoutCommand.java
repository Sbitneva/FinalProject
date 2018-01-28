package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.services.client.CheckoutService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: checkout.
 */
public class CheckoutCommand extends BasicCommand implements Command {

    private static Logger log = Logger.getLogger(CheckoutCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        int userId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);
        if (userId > 0) {
            CheckoutService checkoutService = CheckoutService.getCheckoutService();
            boolean isSuccess = checkoutService.doCheckout(userId);
            if (isSuccess) {
                request.getRequestDispatcher(CHECKOUT_SUCCESS_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(SERVLET_NAME + CART_COMMAND).forward(request, response);
            }
        }
    }

}
