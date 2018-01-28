package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ShipDao;
import sbitneva.entity.Ship;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Service: show cruises.
 */
public final class ShowCruisesService {

    private static Logger log = Logger.getLogger(ShowCruisesService.class.getName());
    private static ShowCruisesService showCruisesService = new ShowCruisesService();

    private ShowCruisesService() {

    }

    /**
     * Get ShowCruisesService instance.
     *
     * @return ShowCruisesService instance
     */
    public static ShowCruisesService getShowCruisesService() {
        return showCruisesService;
    }

    /**
     * Get cruise ships list.
     *
     * @return Ships list
     */
    public ArrayList<Ship> getCruiseShips() {
        ArrayList<Ship> ships = new ArrayList<>();
        ShipDao shipDao = DaoFactory.getShipDao();
        try {
            ships = shipDao.getAllShips();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return ships;
    }
}
