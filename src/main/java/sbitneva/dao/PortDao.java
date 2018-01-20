package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Port;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortDao {
    private final static String GET_PORTNAME_BY_ID = "select port_name from ports where port_id = ?";
    private final static String GET_PORT_ID_BY_EXCURSION_ID = "select port_id_ports from excursions where excursion_id=?";
    private final static String GET_PORTS_BY_SHIP_ID =
            " select * from ports inner join many_ports_has_many_ships on" +
                    " (many_ports_has_many_ships.port_id_ports = ports.port_id" +
                    " and many_ports_has_many_ships.ship_id_ships = ?)";
    private final static String GET_ALL_PORTS = "select * from ports";
    private static Logger log = Logger.getLogger(PortDao.class.getName());

    public String getPortNameById(int id) throws SQLException, DaoException {
        String portName = new String();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PORTNAME_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                portName = resultSet.getString(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return portName;
    }

    public int getPortIdByExcursionId(int excursionId) throws SQLException {
        int portId = 0;
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PORT_ID_BY_EXCURSION_ID);
            statement.setInt(1, excursionId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                portId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
        return portId;
    }

    public ArrayList<Port> getPortsByShipId(int shipId) throws SQLException {
        ArrayList<Port> ports = new ArrayList<>();

        Connection connection = ConnectionPool.getConnection();
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
            log.error(e.getMessage());
        }
        connection.close();
        return ports;
    }

    public Map<Integer, String> getPortsMap() throws SQLException {
        Map<Integer, String> portsMap = new HashMap<>();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_PORTS);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                portsMap.put(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();

        return portsMap;
    }
}
