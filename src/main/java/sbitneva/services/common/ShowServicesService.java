package sbitneva.services.common;

import sbitneva.dao.ComfortLevelDao;
import sbitneva.dao.DaoFactory;
import sbitneva.entity.ComfortLevel;

import java.sql.SQLException;

/**
 * Service: show services.
 */
public final class ShowServicesService {

    private static ShowServicesService showTicketsService = new ShowServicesService();

    private ShowServicesService() {

    }

    /**
     * Get ShowServicesService instance.
     *
     * @return ShowServicesService instance
     */
    public static ShowServicesService getShowTicketsService() {
        return showTicketsService;
    }

    /**
     * Get services for comfort level.
     *
     * @param comfortLevelId Comfort level ID
     * @return ComfortLevel
     */
    public ComfortLevel getServices(final int comfortLevelId) {
        ComfortLevel comfortLevel = null;
        ComfortLevelDao comfortLevelDao = DaoFactory.getComfortLevelDao();
        try {
            String clName = comfortLevelDao.getNameById(comfortLevelId);
            if (clName != null) {
                comfortLevel = new ComfortLevel();
                comfortLevel.setComfortLevelId(comfortLevelId);
                comfortLevel.setComfortLevelName(clName);
                comfortLevel.setServices(comfortLevelDao.getComfortLevelInfo(comfortLevelId));
            }

        } catch (SQLException e) {

        }
        return comfortLevel;
    }
}
