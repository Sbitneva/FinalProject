package services;

import org.junit.Test;
import sbitneva.entity.ComfortLevel;
import sbitneva.entity.Ship;
import sbitneva.entity.Staff;
import sbitneva.exception.RequestedDataException;
import sbitneva.services.ShipAdminService;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShipAdminServiceTest {
    ShipAdminService shipAdminService = ShipAdminService.getShipAdminService();

    @Test
    public void testGetShipBaseInfoAndTickets(){
        int realAdminUserId = 1;
        int realShipId = 1;

        int impossibleShipId = -1;

        Ship ship = null;
        try {
            ship = shipAdminService.getShipBaseInfoAndTickets(realAdminUserId, realShipId);
            assertEquals(ship.getShipId(), realAdminUserId);
        } catch (RequestedDataException e) {
            assert(true);
        }
        ship = null;
        try {
            ship = shipAdminService.getShipBaseInfoAndTickets(realAdminUserId, impossibleShipId);
        } catch (RequestedDataException e) {
            assert(true);
        }
        assertEquals(ship, null);
    }

    @Test
    public void setDiscountTest() {
        int discount = -45;
        int ticketId = 1;
        boolean res = shipAdminService.setDiscount(discount, ticketId);
        assertEquals(res, false);
        discount = 200;
        res = shipAdminService.setDiscount(discount, ticketId);
        assertEquals(res, false);
        ticketId = -20;
        res = shipAdminService.setDiscount(discount, ticketId);
        assertEquals(res, false);
    }

    @Test
    public void getComfortLevelServicesTest() {
        ComfortLevel comfortLevel = null;
        int comfortLevelId = -1;
        comfortLevel = shipAdminService.getComfortLevelServices(comfortLevelId);
        assertEquals(comfortLevel.getComfortLevelId(), 0);
    }

    @Test
    public void getStaffTest(){
        int shipId = 1;
        ArrayList<Staff> staff = new ArrayList<>();
        staff = shipAdminService.getStaff(shipId);
        assertEquals(staff.size(), 3);

        shipId = -1;
        staff = shipAdminService.getStaff(shipId);
        assertEquals(staff.size(), 0);
    }
}
