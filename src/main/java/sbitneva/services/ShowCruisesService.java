package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ShipDao;
import sbitneva.entity.Ship;

import java.sql.SQLException;
import java.util.ArrayList;


public class ShowCruisesService {

    private static Logger log = Logger.getLogger(ShowCruisesService.class.getName());

    private ShowCruisesService(){

    }

    private static ShowCruisesService showCruisesService = new ShowCruisesService();

    public static ShowCruisesService getShowCruisesService() {
        return showCruisesService;
    }

    public ArrayList<Ship> getCruiseShips(){
        ArrayList<Ship> ships = new ArrayList<>();
        ShipDao shipDao = DaoFactory.getShipDao();
        try{
            ships = shipDao.getAllShips();
        } catch(SQLException e) {
            log.error(e.getMessage());
        }
        return ships;
    }
}
