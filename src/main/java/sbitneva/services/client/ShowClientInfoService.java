package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Client;
import sbitneva.entity.Excursion;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Service: show client info.
 */
public final class ShowClientInfoService {
    private static Logger log = Logger.getLogger(ShowClientInfoService.class.getName());
    private static ShowClientInfoService showClientInfoService = new ShowClientInfoService();

    private ShowClientInfoService() {

    }

    /**
     * Get ShowClientInfoService instance.
     *
     * @return ShowClientInfoService instance
     */
    public static ShowClientInfoService getShowClientInfoService() {
        return showClientInfoService;
    }

    /**
     * Get client data by user ID.
     *
     * @param userId User ID
     * @return Client
     * @throws SQLException DB access errors
     * @throws DaoException DAO exceptions
     */
    public Client getClient(final int userId) throws SQLException, DaoException {
        Client client = null;
        boolean success = false;
        try {
            UserDao userDao = DaoFactory.getUserDao();
            client = userDao.getUserById(userId);
            if (client != null) {
                TransactionManager.beginTransaction();
                TicketDao ticketDao = DaoFactory.getTicketDao();
                client.setTickets(ticketDao.getUserTickets(userId));
                ArrayList<Ticket> tickets = client.getTickets();
                if (tickets.size() > 0) {
                    ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
                    Map<Integer, String> comfortLevels = comfortLevelDao.getComfortLevels();
                    for (Ticket ticket : tickets) {
                        ticket.setComfortLevelName(comfortLevels.get(ticket.getComfortLevel()));
                    }
                }
                fillClientFields(client);
                TransactionManager.endTransaction();
            } else {
                return null;
            }
        } catch (TransactionException e) {
        }

        return client;
    }

    private void fillClientFields(final Client client) {
        ShipDao shipDao = DaoFactory.getShipDao();
        PortDao portDao = DaoFactory.getPortDao();
        ArrayList<Excursion> userExcursions = new ArrayList<>();

        try {
            ExcursionDao excursionDao = DaoFactory.getExcursionDao();

            if (client.getTickets().size() > 0) {
                userExcursions = excursionDao.getExcursionsByUser(client.getClientId());
            }
            for (Excursion excursion : userExcursions) {
                excursion.setExcursionName(excursionDao.getExcursionNameById(excursion.getExcursionId()));
                excursion.setShipName(shipDao.getShipNameById(excursion.getShipId()));
                int portId = portDao.getPortIdByExcursionId(excursion.getExcursionId());
                excursion.setPortName(portDao.getPortNameById(portId));
            }
            client.setExcursions(userExcursions);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
    }

}
