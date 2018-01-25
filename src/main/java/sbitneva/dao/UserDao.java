package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static final String GET_CLIENT_BY_ID = "SELECT * FROM users WHERE (user_id = ? AND ship_id_ships IS NULL);";
    private static final String GET_CLIENT_BY_EMAIL_AND_PASS = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String ADD_USER = "INSERT INTO users VALUES ( default, ?, ?, ?, ?, default )";
    private static final String GET_USER_SHIPID = "SELECT ship_id_ships FROM users WHERE (user_id = ?)";
    private static Logger log = Logger.getLogger(UserDao.class.getName());

    public int getUserShipId(int userId) throws SQLException, DaoException {
        int shipId = 0;
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_USER_SHIPID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                shipId = resultSet.getInt(1);
            } else {
                throw new DaoException("User with user id = " + userId + "does not exist");
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return shipId;
    }

    public Client getClientByEmailAndPassword(String email, String password) throws SQLException, DaoException {

        Connection connection = ConnectionPool.getConnection();
        Client client;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_EMAIL_AND_PASS);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            client = getUser(resultSet);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            throw new DaoException("There is no client with the specified email and password");
        }
        connection.close();
        return client;
    }

    public Client getUserById(int userId) throws SQLException, DaoException {

        Connection connection = ConnectionPool.getConnection();
        Client client;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            client = getUser(resultSet);

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            throw new DaoException("There is no client with the specified email and password");
        }
        connection.close();
        return client;
    }

    private Client getUser(ResultSet resultSet) throws SQLException {
        Client client = null;
        if (resultSet.next()) {
            client = new Client();
            client.setClientId(resultSet.getInt(1));
            client.setFirstName(resultSet.getString(2).replaceAll(" ", ""));
            client.setLastName(resultSet.getString(3).replaceAll(" ", ""));
            client.setEmail(resultSet.getString(4).replaceAll(" ", ""));
            client.setPassword(resultSet.getString(5).replaceAll(" ", ""));
            client.setShipId(resultSet.getInt(6));
        }
        return client;
    }

    public int addNewUser(String firstName, String lastName, String email, String password) throws SQLException {
        int result = 0;

        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_USER);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);

            result = statement.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return result;
    }
}
