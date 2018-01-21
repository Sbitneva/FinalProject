package sbitneva.services.client;

import org.apache.log4j.Logger;
import sbitneva.dao.BasicDao;
import sbitneva.dao.DaoFactory;
import sbitneva.entity.Excursion;

import java.util.ArrayList;

public class ShowExcutsionsService {
    private static Logger log = Logger.getLogger(ShowCruisesService.class.getName());
    private static ShowExcutsionsService showExcutsionsService = new ShowExcutsionsService();

    private ShowExcutsionsService() {

    }

    public static ShowExcutsionsService getShowExcutsionsService() {
        return showExcutsionsService;
    }

    public ArrayList<Excursion> getExcursions(int ticketId) {
        ArrayList<Excursion> excursions = null;
        BasicDao basicDao = DaoFactory.getBasicDao();

        return excursions;
    }
}
