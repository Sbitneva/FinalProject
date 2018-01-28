package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Ship DAO.
 */
public class ShipDao extends BasicDao {

    private static Logger log = Logger.getLogger(ShipDao.class.getName());

    private static final String GET_SHIP_NAME_BY_ID =
            "SELECT ship_name FROM ships WHERE ship_id = ?";
    private static final String GET_ALL_SHIPS =
            "SELECT * FROM ships";
    private static final String GET_SHIP_INFO =
            "SELECT * FROM ships WHERE ship_id = ?";

    /**
     * Get ship information.
     *
     * @param shipId Ship ID
     * @return Ship data
     * @throws SQLException DB access errors
     */
    public Ship getBasicShipData(final int shipId) throws SQLException {
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

    /**
     * Get all the ships.
     *
     * @return Ships list
     * @throws SQLException DB access errors
     */
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

    /**
     * Get ship name by ship ID.
     *
     * @param id Ship ID
     * @return Ship name
     * @throws SQLException DB access errors
     */
    public String getShipNameById(final int id) throws SQLException {
        return super.getNameById(GET_SHIP_NAME_BY_ID, id);
    }

}
