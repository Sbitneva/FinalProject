package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.CartDao;
import sbitneva.dao.ComfortLevelDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ShowCartService {

    private static Logger log = Logger.getLogger(ShowCartService.class.getName());

    private static ShowCartService showCartService = new ShowCartService();

    private ShowCartService() {

    }

    public static ShowCartService getShowCartService() {
        return showCartService;
    }

    static void setTicketsInfo(Cart cart) {

        try {

            ArrayList<Ticket> tickets = cart.getTickets();
            if (tickets.size() > 0) {
                ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
                Map<Integer, String> comfortLevels = comfortLevelDao.getComfortLevels();
                for (Ticket ticket : tickets) {
                    TicketDao ticketDao = DaoFactory.getTicketDao();
                    ticketDao.setTicketProperties(ticket);
                    ticket.setComfortLevelName(comfortLevels.get(ticket.getComfortLevel()));
                    ticket.setDiscountedPrice();
                }
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

    public Cart getCart(int userId) {
        Cart cart = null;
        CartDao cartDao = DaoFactory.getCartDao();
        try {
            TransactionManager.beginTransaction();
            cart = cartDao.getUserCart(userId);
            if (cart != null) {
                boolean isVerified = false;
                while (!isVerified) {
                    setTicketsInfo(cart);
                    isVerified = verifyCart(cart, userId);
                }
                setCheckout(cart);
                setDiscountedCheckout(cart);
            }
            TransactionManager.endTransaction();
        } catch (SQLException | TransactionException e) {
            try {
                TransactionManager.endTransaction();
            } catch (TransactionException | SQLException e1) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            }
        }
        return cart;
    }

    private void setDiscountedCheckout(Cart cart) {
        ArrayList<Ticket> tickets = cart.getTickets();
        if (tickets.size() > 0) {
            int sum = 0;
            for (Ticket ticket : tickets) {
                sum += ticket.getDiscountedPrice();
            }
            cart.setDiscountedCheckout(sum);
        }
    }

    private void setCheckout(Cart cart) {
        ArrayList<Ticket> tickets = cart.getTickets();
        if (tickets.size() > 0) {
            int sum = 0;
            for (Ticket ticket : tickets) {
                sum += ticket.getPrice();
            }
            cart.setCheckout(sum);
        }
    }

    private boolean verifyCart(Cart cart, int userId) {

        for (Ticket ticket : cart.getTickets()) {
            if (ticket.getOwnerId() > 0) {
                try {
                    cart.addDeletedTicket((Ticket) ticket.clone());
                } catch (CloneNotSupportedException e) {
                    log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                }
            }
        }

        ArrayList<Ticket> tickets = cart.getTickets();
        for (int i = 0; i < tickets.size(); i++) {
            log.debug("size = " + tickets.size() + " i = " + i);
            if (tickets.get(i).getOwnerId() > 0) {
                tickets.remove(i);
                --i;
            }

        }

        CartDao cartDao = DaoFactory.getCartDao();
        boolean result = false;
        try {
            //TransactionManager.beginTransaction();
            result = cartDao.cleanCart(cart.getDeletedTickets(), userId);
            //TransactionManager.endTransaction();
        } catch (SQLException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            try {
                //TransactionManager.rollbackTransaction();
            } catch (Exception e1) {
                log.error(e1.getClass().getSimpleName() + " : " + e1.getMessage());
            }
        }

        return result;
    }
}
