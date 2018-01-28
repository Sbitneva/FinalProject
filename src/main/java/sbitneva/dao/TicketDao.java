package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Ticket DAO.
 */
public class TicketDao extends BasicDao {

    private static Logger log = Logger.getLogger(TicketDao.class.getName());

    private static final String BUY_TICKET =
            "UPDATE tickets SET user_id_users=? WHERE "
            + "(ticket_id = ? and user_id_users is null)";
    private static final String GET_SHIP_ID_BY_TICKET_ID =
            "SELECT ship_id_ships FROM tickets WHERE ticket_id = ?";
    private static final String UPDATE_TICKET_DISCOUNT =
            "UPDATE tickets SET discount = ?"
            + " WHERE (ticket_id = ? AND ship_id_ships = ?)";
    private static final String GET_ALL_CLIENT_TICKETS =
            "SELECT * FROM tickets INNER JOIN ships ON (tickets.user_id_users=? "
            + "AND tickets.ship_id_ships = ships.ship_id)";
    private static final String GET_AVAILABLE_TICKETS_NUMBER =
            "SELECT COUNT (*) FROM tickets WHERE (ship_id_ships = ? AND user_id_users IS NULL)";
    private static final String GET_LIMITED_NUMBER_AVAILABLE_TICKETS =
            "SELECT * FROM tickets WHERE (ship_id_ships = ? AND user_id_users IS NULL) "
            + "ORDER BY ticket_id OFFSET ? LIMIT ?";
    private static final String GET_TICKET_PROPERTIES =
            "SELECT * FROM tickets INNER JOIN ships ON "
            + "(tickets.ship_id_ships = ships.ship_id and ticket_id = ?)";

    /**
     * Get user tickets.
     *
     * @param userId User ID
     * @return List of tickets
     * @throws SQLException DB access errors
     * @throws DaoException No such user exists
     */
    public ArrayList<Ticket> getUserTickets(final int userId) throws SQLException, DaoException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        ConnectionPoolWrapper connection = TransactionManager.getConnection();

        connection.getConnection().setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CLIENT_TICKETS);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setDiscount(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(4));
                ticket.setShipId(resultSet.getInt(5));
                ticket.setShipName(resultSet.getString(8));
                ticket.setCruiseDuration(resultSet.getInt(9));
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DaoException("User " + userId + " not exists");
        }
        connection.close();
        return tickets;
    }

    /**
     * Buy tickets.
     *
     * @param userId User ID
     * @param cart Cart object
     * @throws SQLException DB access errors
     * @throws TransactionException Transaction cannot be completed
     */
    public void buyTickets(final int userId, final Cart cart) throws SQLException, TransactionException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        connection.getConnection().setAutoCommit(false);

        try {
            for (Ticket ticket : cart.getTickets()) {
                PreparedStatement statement = connection.prepareStatement(BUY_TICKET);
                statement.setInt(1, userId);
                statement.setInt(2, ticket.getTicketId());
                int rows = statement.executeUpdate();
                if (rows != 1) {
                    throw new TransactionException("Can't buy ticket");
                }
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
    }

    /**
     * Get ship by ticket.
     *
     * @param ticketId Ticket ID
     * @return Ship ID
     * @throws SQLException DB access errors
     */
    public int getShipByTicketId(final int ticketId) throws SQLException {
        return super.getId(GET_SHIP_ID_BY_TICKET_ID, ticketId);
    }

    /**
     * Update ticket's discount.
     *
     * @param ticketId Ticket ID
     * @param discount Discount value
     * @param shipId Ship ID
     * @return 1 if successfully updated
     * @throws SQLException DB access errors
     */
    public int updateDiscount(final int ticketId, final int discount, final int shipId) throws SQLException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET_DISCOUNT);
            statement.setInt(1, discount);
            statement.setInt(2, ticketId);
            statement.setInt(3, shipId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return result;
    }

    /**
     * Get available tickets number for a ship.
     *
     * @param shipId Ship ID
     * @return Number of available tickets
     * @throws SQLException DB access errors
     */
    public int getAvailableTicketsNumber(final int shipId) throws SQLException {
        int result = 0;
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(GET_AVAILABLE_TICKETS_NUMBER);
            statement.setInt(1, shipId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            e.printStackTrace();
        }

        connection.close();
        return result;
    }

    /**
     * Get a bunch of tickets for a ship.
     *
     * @param shipId Ship ID
     * @param offset Offset (starting ticket)
     * @param itemsNumber Number of tickets to read
     * @return Tickets list
     * @throws SQLException DB access errors
     */
    public ArrayList<Ticket> getTicketsForPage(final int shipId, final int offset, final int itemsNumber)
            throws SQLException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        ArrayList<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_LIMITED_NUMBER_AVAILABLE_TICKETS);
            statement.setInt(1, shipId);
            statement.setInt(2, offset);
            statement.setInt(3, itemsNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setDiscount(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(4));
                ticket.setShipId(resultSet.getInt(5));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        connection.close();
        return tickets;
    }

    /**
     * Set ticket properties.
     *
     * @param ticket Ticket data
     * @throws SQLException DB access errors
     */
    public void setTicketProperties(final Ticket ticket) throws SQLException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_TICKET_PROPERTIES);
            statement.setInt(1, ticket.getTicketId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket.setDiscount(resultSet.getInt(2));
                log.debug(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(4));
                ticket.setShipId(resultSet.getInt(5));
                ticket.setOwnerId(resultSet.getInt(6));
                ticket.setShipName(resultSet.getString(8));
                ticket.setCruiseDuration(resultSet.getInt(9));
            }

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        connection.close();
    }
}
