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
    private static Logger log = Logger.getLogger(UserDao.class.getName());

    private static final String GET_CLIENT_BY_ID = "select * from users where (user_id = ? and ship_id_ships is null);";
    private static final String GET_CLIENT_BY_EMAIL_AND_PASS = "select * from users where email = ? and password = ?";
    private static final String ADD_USER = "insert into users values ( default, ?, ?, ?, ?, default )";
    private static final String GET_USER_SHIPID = "select ship_id_ships from users where (user_id = ?)";


    public int getUserShipId(int userId) throws SQLException, DaoException{
        int shipId = 0;
        Connection connection = ConnectionPool.getConnection();
        try{
                PreparedStatement statement = connection.prepareStatement(GET_USER_SHIPID);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    shipId = resultSet.getInt(1);
                } else {
                    throw new DaoException("User with user id = " + userId + "does not exist");
                }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
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
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            throw new DaoException("There is no client with the specified email and password");
        }
        connection.close();
        return client;
    }

    private Client getUser(ResultSet resultSet) throws SQLException {
        Client client = new Client();

        if (resultSet.next()) {
            client.setClientId(resultSet.getInt(1));
            client.setFirstName(resultSet.getString(2));
            client.setLastName(resultSet.getString(3));
            client.setEmail(resultSet.getString(4));
            client.setPassword(resultSet.getString(5));
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
            log.error(e.getStackTrace());
        }
        connection.close();
        return result;
    }
}
