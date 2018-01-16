package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Excursion;
import sbitneva.exception.DAOException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExcursionDao {
    private final static String GET_EXCURSIONS_BY_USER_ID = "select * from many_tickets_has_many_excursions " +
            "inner join tickets " +
            "on (tickets.user_id_users=? and tickets.ticket_id= many_tickets_has_many_excursions.ticket_id_tickets)";
    private final static String GET_EXCURSION_NAME_BY_ID = "select excursion_name from excursions where excursion_id=?";
    private final static String GET_ALL_EXCURSIONS_BY_PORT_ID =
            "select * from  excursions inner join ports on " +
                    "(excursions.port_id_ports = ? and excursions.port_id_ports = ports.port_id)";
    private static Logger log = Logger.getLogger(ExcursionDao.class.getName());

    public ArrayList<Excursion> getExcursionsByUser(int userId) throws SQLException, DAOException {
        ArrayList<Excursion> excursions = new ArrayList<>();
        Connection con = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(GET_EXCURSIONS_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Excursion excursion = new Excursion();
                excursion.setExcursionId(resultSet.getInt(2));
                excursion.setShipId(resultSet.getInt(8));
                excursions.add(excursion);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return excursions;
    }

    public String getExcursionNameById(int id) throws SQLException {
        String name = "";

        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_EXCURSION_NAME_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
                log.debug(name);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());

        }
        connection.close();
        return name;
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
            log.error(e.getMessage());

        }
        connection.close();

        return excursions;
    }
}
