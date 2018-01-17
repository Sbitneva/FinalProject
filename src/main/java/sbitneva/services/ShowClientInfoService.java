package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Excursion;
import sbitneva.entity.Ticket;
import sbitneva.entity.Client;
import sbitneva.exception.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private static Logger log = Logger.getLogger(UserService.class.getName());
    private static UserService userService = new UserService();

    private UserService() {

    }

    public static UserService getUserService() {
        return userService;
    }

    public Client verify(int userId) throws SQLException, DAOException {
        UserDao userDao = DaoFactory.getUserDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        Client client = userDao.getUserById(userId);
        client.setTickets(ticketDao.getUserTickets(userId));
        ArrayList<Ticket> tickets = client.getTickets();
        for(Ticket ticket:tickets){
            ticket.setComfortLevelName(comfortLevelDao.getComfortLevelNameById(ticket.getComfortLevel()));
        }
        if (client.getClientId() == 0) {
            throw new DAOException("there is no client with id = " + userId);
        } else {
            fillUserFields(client);
            return client;
        }
    }

    private void fillUserFields(Client client) throws SQLException, DAOException {
        ExcursionDao excursionDao = DaoFactory.getExcursionDao();
        ShipDao shipDao = DaoFactory.getShipDao();
        PortDao portDao = DaoFactory.getPortDao();
        ArrayList<Excursion> userExcursions = new ArrayList<>();

        try {
            if (client.getTickets().size() > 0) {
                userExcursions = excursionDao.getExcursionsByUser(client.getClientId());
            }

            for (int i = 0; i < userExcursions.size(); i++) {
                userExcursions.get(i).setExcursionName(excursionDao.getExcursionNameById(userExcursions.get(i).getExcursionId()));
                userExcursions.get(i).setShipName(shipDao.getShipNameById(userExcursions.get(i).getShipId()));
                int portId = portDao.getPortIdByExcursionId(userExcursions.get(i).getExcursionId());
                userExcursions.get(i).setPortName(portDao.getPortNameById(portId));
            }

            client.setExcursions(userExcursions);

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
