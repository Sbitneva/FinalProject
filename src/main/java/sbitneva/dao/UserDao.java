package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static final String GET_CLIENT_BY_ID = "select * from users where (user_id = ? and ship_id_ships is null);";
    private static final String GET_CLIENT_BY_EMAIL_AND_PASS = "select * from users where email = ? and password = ?";
    private static final String ADD_USER = "insert into users values ( default, ?, ?, ?, ?, default )";
    private static Logger log = Logger.getLogger(UserDao.class.getName());

    public User getClientByEmailAndPassword(String email, String password) throws SQLException, DAOException {

        Connection connection = ConnectionPool.getConnection();
        User user;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_EMAIL_AND_PASS);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            user = getUser(resultSet);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException("There is no user with the specified email and password");
        }
        connection.close();
        return user;
    }

    public User getUserById(int userId) throws SQLException, DAOException {

        Connection connection = ConnectionPool.getConnection();
        User user;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CLIENT_BY_ID);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            user = getUser(resultSet);

        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DAOException("There is no user with the specified email and password");
        }
        connection.close();
        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        if (resultSet.next()) {
            user.setUserId(resultSet.getInt(1));
            user.setFirstName(resultSet.getString(2));
            user.setLastName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setPassword(resultSet.getString(5));
            user.setShipId(resultSet.getInt(6));
        }
        return user;
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
