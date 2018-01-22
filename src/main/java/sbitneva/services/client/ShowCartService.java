package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.ComfortLevelDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ShowCartService {

    private static Logger log = Logger.getLogger(ShowCartService.class.getName());
    private static ShowCartService showCartService = new ShowCartService();
    private ShowCartService() {

    }

    public static ShowCartService getShowCartService() {
        return showCartService;
    }

    public Cart getCart(int userId){
        Cart cart = null;
        CartDao cartDao = DaoFactory.getCartDao();
        try{
            cart = cartDao.getUserCart(userId);
            if(cart != null){
                boolean isVerified = false;
                while(!isVerified) {
                    setTicketsInfo(cart);
                    isVerified = verifyCart(cart, userId);
                }
                setCheckout(cart);
                setDiscountedCheckout(cart);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return cart;
    }

    private void setTicketsInfo(Cart cart) {
        ArrayList<Ticket> tickets = cart.getTickets();
        if(tickets.size() > 0) {
            try {
                ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
                Map<Integer, String> comfortLevels = comfortLevelDao.getComfortLevels();
                for(Ticket ticket : tickets){
                    TicketDao ticketDao = DaoFactory.getTicketDao();
                    ticketDao.setTicketProperties(ticket);
                    ticket.setComfortLevelName(comfortLevels.get(ticket.getComfortLevel()));
                    ticket.setDiscountedPrice();
                }
            }catch (SQLException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
    }

    private void setDiscountedCheckout(Cart cart){
        ArrayList<Ticket> tickets = cart.getTickets();
        if(tickets.size() > 0) {
            int sum = 0;
            for(Ticket ticket : tickets) {
                sum += ticket.getDiscountedPrice();
            }
            cart.setDiscountedCheckout(sum);
        }
    }

    private void setCheckout(Cart cart) {
        ArrayList<Ticket> tickets = cart.getTickets();
        if(tickets.size() > 0) {
            int sum = 0;
            for(Ticket ticket : tickets) {
                sum += ticket.getPrice();
            }
            cart.setCheckout(sum);
        }
    }

    private boolean verifyCart(Cart cart, int userId){

        for(Ticket ticket : cart.getTickets()) {
            if(ticket.getOwnerId() > 0) {
                try {
                    cart.addDeletedTicket((Ticket) ticket.clone());
                } catch (CloneNotSupportedException e) {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                }
            }
        }

        ArrayList<Ticket> tickets = cart.getTickets();
        for(int i = 0; i < tickets.size(); i++) {

            if(tickets.get(i).getOwnerId() > 0){
                tickets.remove(i);
                --i;
            }
            log.debug("size = "+ tickets.size() + " i = " + i);
        }

        CartDao cartDao = DaoFactory.getCartDao();
        boolean result = false;
        try {
           result = cartDao.cleanCart(cart, userId);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }

        return result;
    }
}
