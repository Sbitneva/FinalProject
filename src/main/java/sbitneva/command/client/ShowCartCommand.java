package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.BasicCommand;
import sbitneva.command.factory.Command;
import sbitneva.entity.Cart;
import sbitneva.services.client.ShowCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command: show cart.
 */
public class ShowCartCommand extends BasicCommand implements Command {
    private static Logger log = Logger.getLogger(ShowCartCommand.class.getName());

    @Override
    public void execute(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("ShowCartCommand execution started");
        int userId = getSessionAttribute(request, USER_ID_SESSION_ATTRIBUTE);
        boolean success = false;

        if (userId > 0) {
            ShowCartService showCartService = ShowCartService.getShowCartService();
            Cart cart = showCartService.getCart(userId);
            if (cart != null) {
                success = true;
                request.setAttribute(CART, cart);
                request.setAttribute("deleted", cart.getDeletedTickets().size());
                request.getRequestDispatcher(USER_CART_PAGE).forward(request, response);
            }
        }
        if (!success) {
            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
        }
    }
}
