package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Excursion;
import sbitneva.exception.DaoException;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Ticket's excursions DAO.
 */
public class TicketsExcursionsDao extends BasicDao {

    private static Logger log = Logger.getLogger(TicketsExcursionsDao.class.getName());

    private static final String ADD_EXCURSION_AND_TICKET =
            "INSERT INTO many_tickets_has_many_excursions VALUES (?, ?)";
    private static final String GET_ALL_TICKET_EXCURSIONS =
            "SELECT * FROM many_tickets_has_many_excursions WHERE ticket_id_tickets = ?";

    /**
     * Add excursion to ticket.
     *
     * @param ticketId Ticket ID
     * @param excursionId Excursion ID
     * @return 1 if excursion was added
     * @throws SQLException DB access errors
     * @throws DaoException Query error
     */
    public int updateCell(final int ticketId, final int excursionId)
            throws SQLException, DaoException {
        return super.updateCell(ADD_EXCURSION_AND_TICKET, ticketId, excursionId);
    }

    /**
     * Get excursions for ticket.
     *
     * @param ticketId Ticket ID
     * @return Excursions list
     * @throws SQLException DB access errors
     */
    public ArrayList<Excursion> getAllExcursionsByTicketId(final int ticketId) throws SQLException {

        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        ArrayList<Excursion> excursions = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_TICKET_EXCURSIONS);
            statement.setInt(1, ticketId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                excursions.add(new Excursion(resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return excursions;
    }
}
