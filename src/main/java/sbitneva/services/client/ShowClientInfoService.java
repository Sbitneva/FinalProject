package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Excursion;
import sbitneva.entity.Ticket;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static sbitneva.dao.ComfortLevelDao.*;

public class ShowClientInfoService {
    private static Logger log = Logger.getLogger(ShowClientInfoService.class.getName());
    private static ShowClientInfoService showClientInfoService = new ShowClientInfoService();

    private ShowClientInfoService() {

    }

    public static ShowClientInfoService getShowClientInfoService() {
        return showClientInfoService;
    }

    public Client getClient(int userId) throws SQLException, DaoException {
        Client client = null;
        if(isClient(userId)) {
            UserDao userDao = DaoFactory.getUserDao();
            TicketDao ticketDao = DaoFactory.getTicketDao();
            client = userDao.getUserById(userId);
            if(client != null) {
                client.setTickets(ticketDao.getUserTickets(userId));
                ArrayList<Ticket> tickets = client.getTickets();
                if (tickets.size() > 0) {
                    BasicDao basicDao = DaoFactory.getBasicDao();
                    Map<Integer, String> comfortLevels = basicDao.getIdNameDataFromTable(GET_COMFORT_LEVELS);
                    for (Ticket ticket : tickets) {
                        ticket.setComfortLevelName(comfortLevels.get(ticket.getComfortLevel()));
                    }
                }
                fillClientFields(client);
            }
        }
        return client;
    }

    private void fillClientFields(Client client) {
        ExcursionDao excursionDao = DaoFactory.getExcursionDao();
        ShipDao shipDao = DaoFactory.getShipDao();
        PortDao portDao = DaoFactory.getPortDao();
        ArrayList<Excursion> userExcursions = new ArrayList<>();

        try {
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
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + " : " +e.getMessage());
        }
    }

    private boolean isClient(int id) {
        boolean result = false;
        UserDao userDao = DaoFactory.getUserDao();
        try{
            if(userDao.getUserShipId(id) == 0){
                result = true;
            }
        } catch (DaoException | SQLException e){
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return result;
    }
}
