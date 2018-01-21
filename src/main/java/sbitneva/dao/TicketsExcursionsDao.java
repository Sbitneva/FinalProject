package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketsExcursionsDao {

    private final static String ADD_EXCURSION_AND_TICKET = "INSERT INTO many_tickets_has_many_excursions VALUES (?, ?)";
    private static Logger log = Logger.getLogger(TicketsExcursionsDao.class.getName());

    public int getAllFreeTickets(int ticketId, int excursionId) throws SQLException {
        return BasicDao.updateCell(ADD_EXCURSION_AND_TICKET, ticketId, excursionId);
    }
}
