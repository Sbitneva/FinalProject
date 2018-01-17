package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Client;
import sbitneva.entity.Ship;
import sbitneva.exception.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyTicketService {
    private static Logger log = Logger.getLogger(BuyTicketService.class.getName());
    private static BuyTicketService buyTicketService = new BuyTicketService();

    private BuyTicketService() {

    }

    public static BuyTicketService getBuyTicketService() {
        return buyTicketService;
    }

    public boolean verify(int userId) {

        UserDao userDao = DaoFactory.getUserDao();
        try {
            Client client = userDao.getUserById(userId);
            if (client == null) {
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

    public Ship getShipTicketsForPurchase(int shipId){
        Ship ship = new Ship();
        ShipDao shipDao = DaoFactory.getShipDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        PortDao portDao = DaoFactory.getPortDao();
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        try {
            Map<Integer, String> portMap = portDao.getPortsMap();
            ship = shipDao.getBasicShipData(shipId);
            ship.setTickets(ticketDao.getAllAvailableTickets(shipId));
            ship.setPorts(portDao.getPortsByShipId(shipId));
            for (int i = 0; i < ship.getTickets().size(); i++) {
                ship.getTickets().get(i).setComfortLevelName(
                        comfortLevelDao.getComfortLevelNameById(ship.getTickets().get(i).getComfortLevel()));
            }
            for(int i = 0; i < ship.getPorts().size(); i++) {
                ship.getPorts().get(i).setPortName(portMap.get(ship.getPorts().get(i).getPortId()));
            }
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return ship;

    }


}
