package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyTicketService {
    static Logger log = Logger.getLogger(BuyTicketService.class.getName());
    private static BuyTicketService buyTicketService = new BuyTicketService();

    private BuyTicketService() {

    }

    public static BuyTicketService getBuyTicketService() {
        return buyTicketService;
    }

    public boolean verify(int userId) {

        UserDao userDao = DaoFactory.getUserDao();
        try {
            User user = userDao.getUserById(userId);
            if (user == null) {
                return false;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        } catch (DAOException e1) {
            log.error(e1.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Ship> getTicketsForPurchase() {
        ArrayList<Ship> ships = new ArrayList<>();
        ShipDao shipDao = DaoFactory.getShipDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        PortDao portDao = DaoFactory.getPortDao();
        Map<Integer, String> portMap = new HashMap<>();
        try {
            ships = shipDao.getAllShips();
            portMap = portDao.getPortsMap();
            for (int i = 0; i < ships.size(); i++) {
                ships.get(i).setTickets(ticketDao.getAllFreeTickets(ships.get(i).getShipId()));
                ships.get(i).setPorts(portDao.getPortsByShipId(ships.get(i).getShipId()));
                for (int j = 0; j < ships.get(i).getPorts().size(); j++) {
                    ships.get(i).getPorts().get(j).setPortName(portMap.get(ships.get(i).getPorts().get(j).getPortId()));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return ships;
    }

    public void buySelectedTicket(int ticketId, int userId) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            ticketDao.buySelectedItem(ticketId, userId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


}
