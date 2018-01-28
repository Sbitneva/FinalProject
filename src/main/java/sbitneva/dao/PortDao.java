package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Port;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Port DAO.
 */
public class PortDao extends BasicDao {
    private static Logger log = Logger.getLogger(PortDao.class.getName());

    private static final String GET_PORTNAME_BY_ID =
            "SELECT port_name FROM ports WHERE port_id = ?";
    private static final String GET_PORT_ID_BY_EXCURSION_ID =
            "SELECT port_id_ports FROM excursions WHERE excursion_id=?";
    private static final String GET_PORTS_BY_SHIP_ID =
            " SELECT * FROM ports INNER JOIN many_ports_has_many_ships ON"
            + " (many_ports_has_many_ships.port_id_ports = ports.port_id"
            + " AND many_ports_has_many_ships.ship_id_ships = ?)";

    /**
     * Get port name from port ID.
     *
     * @param id Port ID
     * @return Port name
     * @throws SQLException DB access errors
     */
    public String getPortNameById(final int id) throws SQLException {
        return super.getNameById(GET_PORTNAME_BY_ID, id);
    }

    /**
     * Get port ID from excursion ID.
     *
     * @param excursionId Excursion ID
     * @return Port ID
     * @throws SQLException DB access errors
     */
    public int getPortIdByExcursionId(final int excursionId) throws SQLException {
        return super.getId(GET_PORT_ID_BY_EXCURSION_ID, excursionId);
    }

    /**
     * Get ship's ports.
     *
     * @param shipId Ship ID
     * @return List of ports
     * @throws SQLException DB access errors
     */
    public ArrayList<Port> getPortsByShipId(final int shipId) throws SQLException {
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

}
