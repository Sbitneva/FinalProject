package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketDao {
    private static Logger log = Logger.getLogger(TicketDao.class.getName());

    private final static String BUY_TICKET = "UPDATE tickets SET user_id_users=? WHERE ticket_id = ?";
    private final static String GET_USER_ID_BY_TICKET_ID = "select user_id_users from tickets where ticket_id = ?";
    private final static String GET_SHIP_ID_BY_TICKET_ID = "select ship_id_ships from tickets where ticket_id = ?";
    private final static String UPDATE_TICKET_DISCOUNT = "UPDATE tickets SET discount = ? WHERE ticket_id = ?";
    private static final String GET_ALL_CLIENT_TICKETS =
            "SELECT * FROM tickets INNER JOIN ships ON (tickets.user_id_users=? " +
                    "AND tickets.ship_id_ships = ships.ship_id);";
    private static final String GET_AVAILABLE_TICKETS_NUMBER =
            "SELECT count (*) FROM tickets WHERE (ship_id_ships = ? AND user_id_users IS NULL)";
    private static final String GET_LIMITED_NUMBER_AVAILABLE_TICKETS =
            "SELECT * FROM tickets WHERE (ship_id_ships = ? AND user_id_users IS NULL) ORDER BY ticket_id OFFSET ? LIMIT ?";


    public ArrayList<Ticket> getUserTickets(int userId) throws SQLException, DaoException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
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
            throw new DaoException();
        }
        connection.close();
        return tickets;
    }


    public int buySelectedItem(int userId, int ticketId) throws SQLException {
        return BasicDao.updateCell(BUY_TICKET, userId, ticketId);
    }

    public int getUserIdByTicketId(int ticketId) throws SQLException {
        return BasicDao.getId(GET_USER_ID_BY_TICKET_ID, ticketId);
    }

    public int getShipByTicketId(int ticketId) throws SQLException {
        return BasicDao.getId(GET_SHIP_ID_BY_TICKET_ID, ticketId);
    }

    public int updateDiscount(int ticketId, int discount) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET_DISCOUNT);
            statement.setInt(1, discount);
            statement.setInt(2, ticketId);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return result;
    }

    public int getAvailableTicketsNumber(int shipId) throws SQLException {
        int result = 0;
        Connection connection = ConnectionPool.getConnection();
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

    public ArrayList<Ticket> getTicketsForPage(int shipId, int offset, int itemsNumber) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
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
}
