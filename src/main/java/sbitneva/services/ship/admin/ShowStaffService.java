package sbitneva.services.ship.admin;


import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.StaffDao;
import sbitneva.entity.Staff;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowStaffService {

    private static Logger log = Logger.getLogger(ShipAdminService.class.getName());

    private static ShowStaffService showStaffService = new ShowStaffService();

    private ShowStaffService(){

    }

    public static ShowStaffService getShowStaffService() {
        return showStaffService;
    }

    public ArrayList<Staff> getStaff(int shipId){
        ArrayList<Staff> staff = null;
        StaffDao staffDao = DaoFactory.getStaffDao();
        try{
            staff = staffDao.getStaff(shipId);

        } catch (SQLException e) {

        }
        return staff;
    }
}
