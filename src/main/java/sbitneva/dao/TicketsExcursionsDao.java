package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketsExcursionsDao {

    private final static String ADD_EXCURSION_AND_TICKET = "insert into many_tickets_has_many_excursions values (?, ?)";
    private static Logger log = Logger.getLogger(TicketsExcursionsDao.class.getName());

    public void getAllFreeTickets(int ticketId, int excursionId) throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_EXCURSION_AND_TICKET);
            statement.setInt(1, ticketId);
            statement.setInt(2, excursionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        connection.close();
    }
}
