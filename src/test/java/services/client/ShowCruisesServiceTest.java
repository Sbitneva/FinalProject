package services.client;

import org.apache.log4j.Logger;
import org.junit.Test;
import sbitneva.entity.Ship;
import sbitneva.services.client.ShowCruisesService;
import sbitneva.transactions.ConnectionPool;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ShowCruisesServiceTest {


    ShowCruisesService showCruisesService = ShowCruisesService.getShowCruisesService();

    @Test
    public void getCruiseShipsTest(){
        ArrayList<Ship> ships = showCruisesService.getCruiseShips();
        assertEquals(2, ships.size());
    }
}
