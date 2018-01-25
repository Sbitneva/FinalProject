package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Excursion;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExcursionDao {
    public final static String GET_EXCURSION_NAME_BY_ID = "select excursion_name from excursions where excursion_id=?";
    private final static String GET_EXCURSIONS_BY_USER_ID = "SELECT * FROM many_tickets_has_many_excursions " +
            "INNER JOIN tickets " +
            "ON (tickets.user_id_users=? AND tickets.ticket_id= many_tickets_has_many_excursions.ticket_id_tickets)";
    private final static String GET_ALL_EXCURSIONS_BY_PORT_ID =
            "SELECT * FROM  excursions INNER JOIN ports ON " +
                    "(excursions.port_id_ports = ? AND excursions.port_id_ports = ports.port_id)";
    private static Logger log = Logger.getLogger(ExcursionDao.class.getName());

    public ArrayList<Excursion> getExcursionsByUser(int userId) throws SQLException, DaoException {
        ArrayList<Excursion> excursions = null;
        Connection con = ConnectionPool.getConnection();
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

    public ArrayList<Excursion> getAllExcursionsForPort(int portId) throws SQLException {
        ArrayList<Excursion> excursions = new ArrayList<>();

        Connection connection = ConnectionPool.getConnection();
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

    public String getExcursionNameById(int id) throws SQLException {
        return BasicDao.getNameById(GET_EXCURSION_NAME_BY_ID, id);
    }
}
