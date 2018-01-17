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
    private final static String GET_ALL_FREE_TICKETS = "select * from tickets where (ship_id_ships = ? and user_id_users is null)";
    private final static String BUY_TICKET = "update tickets set user_id_users=? where ticket_id = ?";
    private final static String GET_USER_ID_BY_TICKET_ID = "select user_id_users from tickets where ticket_id = ?";
    private final static String GET_SHIP_ID_BY_TICKET_ID = "select ship_id_ships from tickets where ticket_id = ?";
    private final static String GET_ALL_AVAILABLE_TICKETS =
            "select * from tickets inner join comfort_levels on " +
                    "(tickets.ship_id_ships = ? and tickets.user_id_users is null " +
                    "and tickets.comfort_level_id_comfort_levels = comfort_levels.comfort_level_id);";
    private final static String UPDATE_TICKET_DISCOUNT = "update tickets set discount = ? where ticket_id = ?";
    private static final String GET_ALL_CLIENT_TICKETS =
            "select * from tickets inner join ships on (tickets.user_id_users=? " +
                    "and tickets.ship_id_ships = ships.ship_id);";
    private static Logger log = Logger.getLogger(TicketDao.class.getName());

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

    public ArrayList<Ticket> getAllFreeTickets(int shipId) throws SQLException {
        ArrayList<Ticket> freeTickets = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_FREE_TICKETS);
            statement.setInt(1, shipId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setDiscount(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(5));
                ticket.setShipId(resultSet.getInt(6));
                freeTickets.add(ticket);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return freeTickets;
    }

    public ArrayList<Ticket> getAllAvailableTickets(int shipId) throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_AVAILABLE_TICKETS);
            statement.setInt(1, shipId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setShipId(resultSet.getInt(5));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(4));
                ticket.setComfortLevelName(resultSet.getString(8));
                ticket.setDiscount(resultSet.getInt(2));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return tickets;
    }

    public void buySelectedItem(int userId, int ticketId) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(BUY_TICKET);
            statement.setInt(1, userId);
            statement.setInt(2, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
    }

    public int getUserIdByTicketId(int ticketId) throws SQLException {
        int userId = getIdByTicket(ticketId, GET_USER_ID_BY_TICKET_ID);
        return userId;
    }

    public int getShipByTicketId(int ticketId) throws SQLException {
        int shipId = getIdByTicket(ticketId, GET_SHIP_ID_BY_TICKET_ID);
        return shipId;
    }

    private int getIdByTicket(int ticketId, String sql) throws SQLException{
        int id = 0;
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, ticketId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return id;
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

        }
        return result;
    }
}
