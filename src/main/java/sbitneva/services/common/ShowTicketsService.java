package sbitneva.services.common;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Port;
import sbitneva.entity.Ship;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ShowTicketsService {
    private static Logger log = Logger.getLogger(LoginService.class.getName());
    private static final int ITEMS_PER_PAGE = 10;
    private static ShowTicketsService showTicketsService = new ShowTicketsService();
    private ShowTicketsService(){

    }

    public static ShowTicketsService getShowTicketsService() {
        return showTicketsService;
    }

    /*
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
    */

    public Ship getShip(int userId, int pageId) {
        Ship ship = null;
        UserDao userDao = DaoFactory.getUserDao();
        try{
            int shipId = userDao.getUserShipId(userId);
            if(shipId > 0){
                ShipDao shipDao = DaoFactory.getShipDao();
                ship = shipDao.getBasicShipData(shipId);
                if(ship != null) {
                    ArrayList<Ticket> tickets = getTickets(shipId, pageId);
                    ship.setTickets(tickets);
                    ship.setPorts(getShipPorts(shipId));
                }
            }
        } catch (SQLException | DaoException e){
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return ship;
    }

    public int getTicketsPages(int shipId) {
        int pagesNumber = 0;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int itemsNumber = ticketDao.getAvailableTicketsNumber(shipId);
            pagesNumber = itemsNumber / ITEMS_PER_PAGE;
            if (ITEMS_PER_PAGE * pagesNumber < itemsNumber) {
                pagesNumber = pagesNumber + 1;
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return pagesNumber;
    }

    public ArrayList<Ticket> getTickets(int shipId, int pageId) {
        ArrayList<Ticket> tickets = null;
        int offset = (pageId - 1) * ITEMS_PER_PAGE;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int itemsNumber = ticketDao.getAvailableTicketsNumber(shipId) - offset;
            if(itemsNumber > ITEMS_PER_PAGE){
                itemsNumber = ITEMS_PER_PAGE;
            }
            tickets = ticketDao.getTicketsForPage(shipId, offset, itemsNumber);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return tickets;
    }

    public Ship getShipForClient(int shipId, int currentPage){
        Ship ship = null;
        ShipDao shipDao = DaoFactory.getShipDao();
        try{
            ship = shipDao.getBasicShipData(shipId);
            if(ship != null) {
                ship.setTickets(getTickets(shipId, currentPage));
                ship.setPorts(getShipPorts(shipId));
            }
        } catch (SQLException e) {

        }
        return ship;
    }

    private ArrayList<Port> getShipPorts(int shipId){

        ArrayList<Port> ports = new ArrayList<>();
        PortDao portDao = DaoFactory.getPortDao();
        try {
            ports = portDao.getPortsByShipId(shipId);
        } catch (SQLException e){

        }
        return ports;

    }
}
