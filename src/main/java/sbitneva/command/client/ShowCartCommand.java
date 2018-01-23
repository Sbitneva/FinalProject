package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.CommandsHelper;
import sbitneva.command.factory.Command;
import sbitneva.entity.Cart;
import sbitneva.services.client.ShowCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static sbitneva.command.CommandsHelper.*;

public class ShowCartCommand implements Command {
    private static Logger log = Logger.getLogger(ShowCartCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("ShowCartCommand execution started");
        int userId = CommandsHelper.getUserId(request);
        boolean success = false;
        Cart cart = null;
        if(userId > 0) {
            ShowCartService showCartService = ShowCartService.getShowCartService();
            cart = showCartService.getCart(userId);
            if(cart != null){
                success = true;
                request.setAttribute(CART, cart);
                request.setAttribute("deleted", cart.getDeletedTickets().size());
                request.getRequestDispatcher(USER_CART_PAGE).forward(request, response);
            }
        }
        if(!success) {
            request.getRequestDispatcher(MAIN_PAGE).forward(request, response);
        }
    }
}
