package commands.client;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.ExcursionDao;
import sbitneva.dao.TicketsExcursionsDao;
import sbitneva.exception.TransactionException;
import sbitneva.servlets.ServletDispatcher;
import sbitneva.transactions.TransactionManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static sbitneva.command.CommandsHelper.*;
import static sbitneva.command.factory.FactoryCommand.BUY_EXCURSION;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class BuyExcursionCommandTest {
    static Logger log = Logger.getLogger(BuyExcursionCommandTest.class.getName());
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(BUY_EXCURSION);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void executionTestWithCorrectParameters() throws ServletException, IOException {
        String ticketId = "39";
        when(request.getParameter(TICKET_ID)).thenReturn(ticketId);
        when(request.getParameter(EXCURSION_ID)).thenReturn("2");
        ServletDispatcher dispatcher = new ServletDispatcher();

        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            int excursionsBefore = ticketsExcursionsDao.getAllExcursionsByTicketId(Integer.parseInt(ticketId)).size();
            dispatcher.init();
            dispatcher.processRequest(request, response);
            int excursionsAfter = ticketsExcursionsDao.getAllExcursionsByTicketId(Integer.parseInt(ticketId)).size();
            assertEquals(excursionsBefore + 1, excursionsAfter);
        } catch (SQLException  e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }

    @Test
    public void executionTestWithWrongParameters() throws ServletException, IOException {
        when(request.getParameter(TICKET_ID)).thenReturn("90");
        when(request.getParameter(EXCURSION_ID)).thenReturn("2");
        ServletDispatcher dispatcher = new ServletDispatcher();

        TicketsExcursionsDao ticketsExcursionsDao = DaoFactory.getTicketsExcursionsDao();
        try {
            int excursionsBefore = ticketsExcursionsDao.getAllExcursionsByTicketId(90).size();
            dispatcher.init();
            dispatcher.processRequest(request, response);
            int excursionsAfter = ticketsExcursionsDao.getAllExcursionsByTicketId(90).size();
            assertEquals(excursionsBefore, excursionsAfter);
        } catch (SQLException  e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }
}
