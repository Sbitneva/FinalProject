package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ticket;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {

    private static Logger log = Logger.getLogger(UserDao.class.getName());

    private static final String GET_CLIENT_BY_ID = "select * from users where (user_id = ? and ship_id_ships is null);";
    private static final String GET_CLIENT_BY_EMAIL_AND_PASS = "select * from users where email = ? and password = ?";
    private static final String GET_ALL_CLIENT_TICKETS =
            "select * from tickets inner join ships on (tickets.user_id_users=? " +
                    "and tickets.ship_id_ships = ships.ship_id);";

    public User getClientByEmailAndPassword(String email, String password) throws SQLException, DAOException {

        ConnectionWrapper con = TransactionManager.getConnection();

        try {
            PreparedStatement statement = con.preparedStatement(GET_CLIENT_BY_EMAIL_AND_PASS);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setShipId(resultSet.getString(6));
                System.out.println(user.toString());
                return user;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException("There is no user with the specified email and password");
        }
        con.close();
        return null;
    }

    public User getUserById(int userId) throws SQLException, DAOException {
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_CLIENT_BY_ID);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setShipId(resultSet.getString(6));
                user.setTickets(getUserTickets(userId));
                return user;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException("There is no user with the specified email and password");
        }
        con.close();
        return null;
    }

    public void register(String name, String login, String password) {
    }

    public void insert(User user) {
    }

    public ArrayList<Ticket> getUserTickets(int userId) throws SQLException, DAOException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_ALL_CLIENT_TICKETS);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setDiscount(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(5));
                ticket.setShipId(resultSet.getInt(6));
                ticket.setShipName(resultSet.getString(9));
                ticket.setCruiseDuration(resultSet.getInt(10));
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException();
        }
        con.close();
        return tickets;
    }
}
