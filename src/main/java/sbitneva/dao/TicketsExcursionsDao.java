package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Excursion;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketsExcursionsDao {

    private final static String ADD_EXCURSION_AND_TICKET = "INSERT INTO many_tickets_has_many_excursions VALUES (?, ?)";
    private final static String GET_ALL_TICKET_EXCURSIONS =
            "select * from many_tickets_has_many_excursions where ticket_id_tickets = ?";
    private static Logger log = Logger.getLogger(TicketsExcursionsDao.class.getName());

    public int addExcursionToTicket(int ticketId, int excursionId) throws SQLException, DaoException {

        return BasicDao.updateCell(ADD_EXCURSION_AND_TICKET, ticketId, excursionId);
    }

    public ArrayList<Excursion> getAllExcursionsByTicketId(int ticketId) throws SQLException {

        Connection connection = ConnectionPool.getConnection();
        ArrayList<Excursion> excursions = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_ALL_TICKET_EXCURSIONS);
            statement.setInt(1, ticketId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                excursions.add(new Excursion(resultSet.getInt(2)));
            }

        } catch(SQLException e){

        }
        connection.close();
        return excursions;

    }
}
