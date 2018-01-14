package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShipDao {

    private static Logger log = Logger.getLogger(ShipDao.class.getName());

    private final String GET_SHIP_BY_ID = "select ship_name from ships where ship_id = ?";
    private final String GET_ALL_SHIPS = "select * from ships";



    public String getShipNameById(int shipId) throws SQLException {
        String name = "";
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_SHIP_BY_ID);
            statement.setInt(1, shipId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return name;
    }

    public ArrayList<Ship> getAllShips() throws SQLException {

        ArrayList<Ship> ships = new ArrayList<>();
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_ALL_SHIPS);
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
        con.close();
        return ships;
    }


}
