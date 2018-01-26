package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BasicDao {

    private static Logger log = Logger.getLogger(BasicDao.class.getName());

    public static Map<Integer, String> getIdNameDataFromTable(String sql) throws SQLException {
        Map<Integer, String> comfortLevels = new HashMap<>();
        ConnectionPoolWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comfortLevels.put(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        con.close();
        return comfortLevels;
    }

    public static String getNameById(String sql, int id) throws SQLException {
        String name = null;
        ConnectionPoolWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
            }

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        con.close();
        return name;
    }

    public static int getId(String sql, int parameter) throws SQLException {
        int id = 0;
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
        return id;
    }

    public static int updateCell(String sql, int parameter1, int parameter2) throws SQLException, DaoException {
        int result = 0;
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        //connection.getConnection().setAutoCommit(false);
        log.debug(connection.getConnection().toString());
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, parameter1);
            statement.setInt(2, parameter2);
            result = statement.executeUpdate();
            if (result != 1) {
                throw new DaoException("Query " + statement.toString() + " not executed");
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
        return result;
    }

}
