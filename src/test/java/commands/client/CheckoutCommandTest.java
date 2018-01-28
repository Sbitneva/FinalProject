package commands.client;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Ticket;
import sbitneva.exception.DaoException;
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
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sbitneva.command.BasicCommand.USER_ID_SESSION_ATTRIBUTE;
import static sbitneva.command.factory.FactoryCommand.CHECKOUT;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class CheckoutCommandTest {

    static Logger log = Logger.getLogger(CheckoutCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(CHECKOUT);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void executionTestWithCorrectParameters() throws ServletException, IOException {
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(9);

        ServletDispatcher dispatcher = new ServletDispatcher();

        CartDao cartDao = DaoFactory.getCartDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            TransactionManager.beginTransaction();
            cartDao.addTicketToCart(9, 34);
            cartDao.addTicketToCart(9, 11);
            TransactionManager.endTransaction();
            int ticketsNumBefore = ticketDao.getUserTickets(9).size();
            dispatcher.init();
            dispatcher.processRequest(request, response);
            int ticketsNumAfter = ticketDao.getUserTickets(9).size();
            assertEquals(ticketsNumBefore + 2, ticketsNumAfter);

        } catch (SQLException | TransactionException | DaoException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }

    @Test
    public void executionTestWithWrongParameters() throws ServletException, IOException {
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(9);

        ServletDispatcher dispatcher = new ServletDispatcher();
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(41));
        tickets.add(new Ticket(1));

        CartDao cartDao = DaoFactory.getCartDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        try {
            TransactionManager.beginTransaction();
            for (Ticket ticket : tickets) {
                cartDao.addTicketToCart(9, ticket.getTicketId());
            }
            TransactionManager.endTransaction();

            int ticketsNumBefore = ticketDao.getUserTickets(9).size();
            dispatcher.init();
            dispatcher.processRequest(request, response);
            int ticketsNumAfter = ticketDao.getUserTickets(9).size();
            assertEquals(ticketsNumBefore, ticketsNumAfter);

            TransactionManager.beginTransaction();
            cartDao.cleanCart(tickets, 9);
            TransactionManager.endTransaction();

        } catch (SQLException | TransactionException | DaoException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }
}
