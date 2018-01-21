package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Excursion;
import sbitneva.entity.Port;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowExcutsionsService {
    private static Logger log = Logger.getLogger(ShowCruisesService.class.getName());
    private static ShowExcutsionsService showExcutsionsService = new ShowExcutsionsService();

    private ShowExcutsionsService() {

    }

    public static ShowExcutsionsService getShowExcutsionsService() {
        return showExcutsionsService;
    }

    public ArrayList<Port> getExcursions(int ticketId) {
        ArrayList<Port> ports = null;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int shipId = ticketDao.getShipByTicketId(ticketId);
            if(shipId > 0) {
                PortDao portDao = DaoFactory.getPortDao();
                ports = portDao.getPortsByShipId(shipId);
                if(ports != null) {
                    ExcursionDao excursionDao = DaoFactory.getExcursionDao();
                    for(Port port : ports) {
                        port.setExcursions(excursionDao.getAllExcursionsForPort(port.getPortId()));
                        port.setPortName(portDao.getPortNameById(port.getPortId()));
                    }
                }
            }
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }

        return ports;
    }
}
