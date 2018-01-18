package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ShowTicketsService {
    private static Logger log = Logger.getLogger(LoginService.class.getName());
    private static ShowTicketsService showTicketsService = new ShowTicketsService();
    private ShowTicketsService(){

    }

    public static ShowTicketsService getShowTicketsService() {
        return showTicketsService;
    }

    public Ship getTickets(int shipId){
        Ship ship = null;
        ShipDao shipDao = DaoFactory.getShipDao();
        try {
            ship = shipDao.getBasicShipData(shipId);
            if(ship != null) {
                TicketDao ticketDao = DaoFactory.getTicketDao();
                PortDao portDao = DaoFactory.getPortDao();
                ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
                Map<Integer, String> portMap = portDao.getPortsMap();
                ship.setTickets(ticketDao.getAllAvailableTickets(shipId));
                ship.setPorts(portDao.getPortsByShipId(shipId));
                for (int i = 0; i < ship.getTickets().size(); i++) {
                    ship.getTickets().get(i).setComfortLevelName(
                            comfortLevelDao.getComfortLevelNameById(ship.getTickets().get(i).getComfortLevel()));
                }
                for (int i = 0; i < ship.getPorts().size(); i++) {
                    ship.getPorts().get(i).setPortName(portMap.get(ship.getPorts().get(i).getPortId()));
                }
            }
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        return ship;
    }
}
