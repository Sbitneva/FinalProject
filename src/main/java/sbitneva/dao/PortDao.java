package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Port;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class PortDao {
    private final static String GET_PORTNAME_BY_ID = "SELECT port_name FROM ports WHERE port_id = ?";
    private final static String GET_PORT_ID_BY_EXCURSION_ID = "SELECT port_id_ports FROM excursions WHERE excursion_id=?";
    private final static String GET_PORTS_BY_SHIP_ID =
            " SELECT * FROM ports INNER JOIN many_ports_has_many_ships ON" +
                    " (many_ports_has_many_ships.port_id_ports = ports.port_id" +
                    " AND many_ports_has_many_ships.ship_id_ships = ?)";
    private final static String GET_ALL_PORTS = "select * from ports";
    private static Logger log = Logger.getLogger(PortDao.class.getName());

    public String getPortNameById(int id) throws SQLException, DaoException {
        return BasicDao.getNameById(GET_PORTNAME_BY_ID, id);
    }

    public int getPortIdByExcursionId(int excursionId) throws SQLException {
        return BasicDao.getId(GET_PORT_ID_BY_EXCURSION_ID, excursionId);
    }

    public ArrayList<Port> getPortsByShipId(int shipId) throws SQLException {
        ArrayList<Port> ports = new ArrayList<>();

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PORTS_BY_SHIP_ID);
            statement.setInt(1, shipId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Port port = new Port();
                port.setPortId(resultSet.getInt(1));
                port.setPortName(resultSet.getString(2));
                ports.add(port);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return ports;
    }

    public Map<Integer, String> getPortsMap() throws SQLException {
        return BasicDao.getIdNameDataFromTable(GET_ALL_PORTS);
    }

}
