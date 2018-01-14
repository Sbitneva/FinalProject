package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Port;

import java.sql.SQLException;
import java.util.ArrayList;

public class BuyExcursionService {
    private static Logger log = Logger.getLogger(BuyExcursionService.class.getName());
    private static BuyExcursionService buyExcursionService = new BuyExcursionService();

    private BuyExcursionService() {

    }

    public static BuyExcursionService getBuyTicketService() {
        return buyExcursionService;
    }

    public ArrayList<Port> getExcursionsForPurchase(int ticketId) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        PortDao portDao = DaoFactory.getPortDao();
        ExcursionDao excursionDao = DaoFactory.getExcursionDao();
        ArrayList<Port> ports = new ArrayList<>();

        try {
            int userIdFromDao = ticketDao.getUserIdByTicketId(ticketId);
            if (userIdFromDao > 0) {
                ports = (portDao.getPortsByShipId(ticketDao.getShipByTicketId(ticketId)));

                for (int i = 0; i < ports.size(); i++) {
                    ports.get(i).setExcursions(
                            excursionDao.getAllExcursionsForPort(ports.get(i).getPortId()));
                }
                return ports;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return ports;
    }

    public void buyExcursionForTicket(int ticketId, int excursionId) {
        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            ticketsExcursionsDao.getAllFreeTickets(ticketId, excursionId);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
