package services;

import org.junit.Test;
import sbitneva.entity.Staff;
import sbitneva.services.ship.admin.ShowStaffService;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ShowStaffServiceTest {

    private ShowStaffService showStaffService = ShowStaffService.getShowStaffService();

    @Test
    public void getStaffWithCorrectShipId() {
        int SHIP_ID = 1;
        ArrayList<Staff> staff = showStaffService.getStaff(SHIP_ID);
        assertNotNull(staff);
        assertEquals(3, staff.size());
    }

    @Test
    public void getStaffWithWrongShipId() {
        int SHIP_ID = 5;
        ArrayList<Staff> staff = showStaffService.getStaff(SHIP_ID);
        assertEquals(0, staff.size());
    }
}
