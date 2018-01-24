package services.client;

import org.junit.Test;
import sbitneva.entity.Port;
import sbitneva.services.client.ShowExcursionsService;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShowExcursionsServiceTest {
    private ShowExcursionsService showExcursionsService = ShowExcursionsService.getShowExcursionsService();

    private final int TICKET_ID = 3;
    @Test
    public void getExcursionsTest() {
        ArrayList<Port> ports = showExcursionsService.getExcursions(TICKET_ID);
        assertNotNull(ports);
        assertEquals(3, ports.size());
    }
}
