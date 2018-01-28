package sbitneva.services.ship.admin;


import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.StaffDao;
import sbitneva.entity.Staff;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Service: show staff.
 */
public final class ShowStaffService {

    private static Logger log = Logger.getLogger(ShowStaffService.class.getName());

    private static ShowStaffService showStaffService = new ShowStaffService();

    private ShowStaffService() {
    }

    /**
     * Get ShowStaffService instance.
     *
     * @return ShowStaffService instance
     */
    public static ShowStaffService getShowStaffService() {
        return showStaffService;
    }

    /**
     * Get ship staff.
     *
     * @param shipId Ship ID
     * @return Staff list
     */
    public ArrayList<Staff> getStaff(final int shipId) {
        ArrayList<Staff> staff;
        StaffDao staffDao = DaoFactory.getStaffDao();
        try {
            staff = staffDao.getStaff(shipId);
        } catch (SQLException e) {
            staff = null;
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return staff;
    }
}
