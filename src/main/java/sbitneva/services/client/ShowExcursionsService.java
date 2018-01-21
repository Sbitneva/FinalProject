package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ExcursionDao;
import sbitneva.dao.PortDao;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Port;
import sbitneva.exception.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowExcursionsService {
    private static Logger log = Logger.getLogger(ShowCruisesService.class.getName());
    private static ShowExcursionsService showExcursionsService = new ShowExcursionsService();

    private ShowExcursionsService() {

    }

    public static ShowExcursionsService getShowExcursionsService() {
        return showExcursionsService;
    }

    public ArrayList<Port> getExcursions(int ticketId) {
        ArrayList<Port> ports = null;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            int shipId = ticketDao.getShipByTicketId(ticketId);
            if (shipId > 0) {
                PortDao portDao = DaoFactory.getPortDao();
                ports = portDao.getPortsByShipId(shipId);
                if (ports != null) {
                    ExcursionDao excursionDao = DaoFactory.getExcursionDao();
                    for (Port port : ports) {
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
