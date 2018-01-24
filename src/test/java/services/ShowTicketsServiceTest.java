package services;

import org.junit.Test;
import sbitneva.entity.Ship;
import sbitneva.services.common.ShowTicketsService;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;

public class ShowTicketsServiceTest {

    private ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();

    private int CLIENT_ID = 2;
    private int ADMIN_ID = 1;
    private int PAGE = 1;

    private int SHIP_ID = 1;

    @Test
    public void getShipTest(){
        Ship ship = showTicketsService.getShip(ADMIN_ID, PAGE);
        assertNotNull(ship);
        assertEquals(9, ship.getTickets().size());
    }

    @Test
    public void getShipForClientTest(){
        Ship ship = showTicketsService.getShipForClient(SHIP_ID, 1);
        assertNotNull(ship);
        assertEquals(9, ship.getTickets().size());
    }

    @Test
    public void getShipForClientWrongShipIdTest(){
        Ship ship = showTicketsService.getShipForClient(7, 1);
        assertNull(ship);
    }
}
