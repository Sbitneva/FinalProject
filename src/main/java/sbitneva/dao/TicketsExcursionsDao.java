package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ticket;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketsExcursionsDao {
    private static Logger log = Logger.getLogger(TicketsExcursionsDao.class.getName());

    private final String ADD_EXCURSION_AND_TICKET = "insert into many_tickets_has_many_excursions values (?, ?)";

    public void getAllFreeTickets(int ticketId, int excursionId) throws SQLException {
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(ADD_EXCURSION_AND_TICKET);
            statement.setInt(1, ticketId);
            statement.setInt(2, excursionId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
    }
}
