package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Customer cart DAO.
 */
public class CartDao extends BasicDao {

    private static Logger log = Logger.getLogger(CartDao.class.getName());

    private static final String GET_TICKETS_IDS_FROM_CART =
            "SELECT ticket_id FROM carts where (user_id_carts = ?)";
    private static final String DELETE_CART_ROW =
            "DELETE from carts WHERE (user_id_carts = ? and ticket_id = ?);";
    private static final String GET_TICKET_FROM_CART =
            "SELECT user_id_carts FROM carts where (user_id_carts = ? and ticket_id = ?)";
    private static final String ADD_IF_NOT_EXISTS =
            "INSERT INTO carts (user_id_carts, ticket_id) "
            + "SELECT ?, ? WHERE NOT EXISTS (SELECT user_id_carts, ticket_id "
            + "FROM carts WHERE(user_id_carts = ? AND ticket_id = ?))";

    /**
     * Get customer cart.
     *
     * @param userId User ID
     * @return Cart object
     * @throws SQLException DB access errors
     */
    public Cart getUserCart(final int userId)
            throws SQLException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        Cart cart = null;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TICKETS_IDS_FROM_CART);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            cart = new Cart();
            while (resultSet.next()) {
                cart.addTicketWithId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
        return cart;
    }

    /**
     * Clean-up customer cart.
     *
     * @param tickets Tickets list
     * @param userId User ID
     * @return True when cart is cleaned
     * @throws SQLException DB access errors
     * @throws TransactionException Non-atomic transaction
     */
    public boolean cleanCart(final ArrayList<Ticket> tickets, final int userId)
            throws SQLException, TransactionException {
        boolean result = true;
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {

            for (Ticket ticket : tickets) {
                PreparedStatement statement = connection.prepareStatement(DELETE_CART_ROW);
                statement.setInt(1, userId);
                statement.setInt(2, ticket.getTicketId());
                int row = statement.executeUpdate();
                if (row != 1) {
                    throw new TransactionException("Can't delete row from carts table");
                }
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return result;
    }

    /**
     * Check if ticket is in cart.
     *
     * @param userId User ID
     * @param ticketId Ticket ID
     * @return True if ticket in cart, false - otherwise
     * @throws SQLException DB access errors
     */
    public Boolean isTicketInCart(final int userId, final int ticketId) throws SQLException {
        Boolean isInCart = false;
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TICKET_FROM_CART);
            statement.setInt(1, userId);
            statement.setInt(2, ticketId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isInCart = true;
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
        return isInCart;
    }

    /**
     * Add ticket to cart.
     *
     * @param userId User ID
     * @param ticketId Ticket ID
     * @throws SQLException DB access errors
     */
    public void addTicketToCart(final int userId, final int ticketId) throws SQLException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(ADD_IF_NOT_EXISTS);
            statement.setInt(1, userId);
            statement.setInt(2, ticketId);
            statement.setInt(3, userId);
            statement.setInt(4, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
    }

}
