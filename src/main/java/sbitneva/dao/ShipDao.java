package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipDao {

    public final static String GET_SHIP_NAME_BY_ID = "select ship_name from ships where ship_id = ?";
    private final static String GET_ALL_SHIPS = "SELECT * FROM ships";
    private final static String GET_SHIP_INFO = "SELECT * FROM ships WHERE ship_id = ?";
    private static Logger log = Logger.getLogger(ShipDao.class.getName());

    public Ship getBasicShipData(int shipId) throws SQLException {
        Ship ship = null;

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_SHIP_INFO);
            statement.setInt(1, shipId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ship = new Ship();
                ship.setShipId(resultSet.getInt(1));
                ship.setShipName(resultSet.getString(2));
                ship.setCruiseDuration(resultSet.getInt(3));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return ship;
    }

    public ArrayList<Ship> getAllShips() throws SQLException {

        ArrayList<Ship> ships = new ArrayList<>();
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
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
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return ships;
    }

    public String getShipNameById(int id) throws SQLException {
        return BasicDao.getNameById(GET_SHIP_NAME_BY_ID, id);
    }


}
