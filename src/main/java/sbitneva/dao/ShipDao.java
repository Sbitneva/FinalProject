package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipDao {

    private final static String GET_SHIP_BY_ID = "select ship_name from ships where ship_id = ?";
    private final static String GET_ALL_SHIPS = "select * from ships";
    private final static String GET_SHIP_INFO = "select * from ships where ship_id = ?";
    private static Logger log = Logger.getLogger(ShipDao.class.getName());

    public Ship getBasicShipData(int shipId) throws SQLException {
        Ship ship = new Ship();
        ship.setShipId(shipId);
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SHIP_INFO);
            statement.setInt(1, shipId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ship.setShipName(resultSet.getString(2));
                ship.setCruiseDuration(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return ship;
    }

    public String getShipNameById(int shipId) throws SQLException {
        String name = "";
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SHIP_BY_ID);
            statement.setInt(1, shipId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return name;
    }

    public ArrayList<Ship> getAllShips() throws SQLException {

        ArrayList<Ship> ships = new ArrayList<>();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_SHIPS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ship ship = new Ship();
                ship.setShipId(resultSet.getInt(1));
                ship.setShipName(resultSet.getString(2));
                ship.setCruiseDuration(resultSet.getInt(3));
                ships.add(ship);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return ships;
    }


}
