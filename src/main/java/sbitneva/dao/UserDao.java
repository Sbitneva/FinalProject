package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User DAO.
 */
public class UserDao extends BasicDao {

    private static Logger log = Logger.getLogger(UserDao.class.getName());

    private static final String GET_CLIENT_BY_ID =
            "SELECT * FROM users WHERE (user_id = ? AND ship_id_ships IS NULL);";
    private static final String GET_CLIENT_BY_EMAIL_AND_PASS =
            "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String ADD_USER =
            "INSERT INTO users VALUES (default, ?, ?, ?, ?, default)";
    private static final String GET_USER_SHIPID =
            "SELECT ship_id_ships FROM users WHERE (user_id = ?)";

    /**
     * Get ship ID from user ID.
     *
     * @param userId User ID
     * @return Ship ID
     * @throws SQLException DB access errors
     * @throws DaoException No such user exists
     */
    public int getUserShipId(final int userId) throws SQLException, DaoException {
        int shipId = 0;

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
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

    /**
     * Get user information by email and password.
     *  (for login page)
     *
     * @param email E-mail
     * @param password Password
     * @return User data
     * @throws SQLException DB access errors
     * @throws DaoException No such client exists
     */
    public Client getClientByEmailAndPassword(final String email, final String password)
            throws SQLException, DaoException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
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

    /**
     * Get user information by user ID.
     *
     * @param userId User ID
     * @return User data
     * @throws SQLException DB access errors
     * @throws DaoException No such user exists
     */
    public Client getUserById(final int userId) throws SQLException, DaoException {
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        Client client;

        try {
            PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            client = getUser(resultSet);

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            throw new DaoException("There is no client with the specified user ID");
        }
        connection.close();
        return client;
    }

    private Client getUser(final ResultSet resultSet) throws SQLException {
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

    /**
     * Register new user.
     *
     * @param firstName First name
     * @param lastName Last name
     * @param email E-mail
     * @param password Password
     * @return 1 if user was added
     * @throws SQLException DB access errors
     */
    public int addNewUser(final String firstName, final String lastName, final String email, final String password)
            throws SQLException {
        int result = 0;

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
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
