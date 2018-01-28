package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Excursion;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Excursion DAO.
 */
public class ExcursionDao extends BasicDao {

    private static Logger log = Logger.getLogger(ExcursionDao.class.getName());

    private static final String GET_EXCURSION_NAME_BY_ID =
            "SELECT excursion_name FROM excursions WHERE excursion_id = ?";
    private static final String GET_EXCURSIONS_BY_USER_ID =
            "SELECT * FROM many_tickets_has_many_excursions "
            + "INNER JOIN tickets ON (tickets.user_id_users = ? "
            + "AND tickets.ticket_id = many_tickets_has_many_excursions.ticket_id_tickets)";
    private static final String GET_ALL_EXCURSIONS_BY_PORT_ID =
            "SELECT * FROM  excursions INNER JOIN ports ON "
            + "(excursions.port_id_ports = ? AND excursions.port_id_ports = ports.port_id)";

    /**
     * Get excursions for user.
     *
     * @param userId User ID
     * @return List of excursions
     * @throws SQLException DB access errors
     */
    public ArrayList<Excursion> getExcursionsByUser(final int userId) throws SQLException {
        ArrayList<Excursion> excursions = null;
        ConnectionPoolWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(GET_EXCURSIONS_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            excursions = new ArrayList<>();
            while (resultSet.next()) {
                Excursion excursion = new Excursion();
                excursion.setTicketId(resultSet.getInt(1));
                excursion.setExcursionId(resultSet.getInt(2));
                excursion.setShipId(resultSet.getInt(7));
                excursions.add(excursion);
            }

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        con.close();
        return excursions;
    }

    /**
     * Get excursions for port.
     *
     * @param portId Port ID
     * @return List of excursions
     * @throws SQLException DB access errors
     */
    public ArrayList<Excursion> getAllExcursionsForPort(final int portId) throws SQLException {
        ArrayList<Excursion> excursions = new ArrayList<>();

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_EXCURSIONS_BY_PORT_ID);
            statement.setInt(1, portId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Excursion excursion = new Excursion();
                excursion.setExcursionId(resultSet.getInt(1));
                excursion.setExcursionName(resultSet.getString(2));
                excursion.setPrice(resultSet.getInt(3));
                excursion.setPortName(resultSet.getString(6));
                excursions.add(excursion);
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());

        }
        connection.close();

        return excursions;
    }

    /**
     * Get excursion name by excursion ID.
     *
     * @param id Excursion ID
     * @return Excursion name
     * @throws SQLException DB access errors
     */
    public String getExcursionNameById(final int id) throws SQLException {
        return super.getNameById(GET_EXCURSION_NAME_BY_ID, id);
    }

}
