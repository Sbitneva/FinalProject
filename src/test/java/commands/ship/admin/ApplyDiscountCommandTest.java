package commands.ship.admin;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.TicketDao;
import sbitneva.entity.Ticket;
import sbitneva.servlets.ServletDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sbitneva.command.BasicCommand.*;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;
import static sbitneva.command.factory.FactoryCommand.SET_DISCOUNT;

public class ApplyDiscountCommandTest {

    static Logger log = Logger.getLogger(ApplyDiscountCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    private String ticketId;
    private String shipId;
    private String discount;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(SET_DISCOUNT);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeTestWithEmptyDiscountAndEmptyTicketId() throws IOException, ServletException {
        when(request.getParameter(anyString())).thenReturn(null);

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);

    }

    @Test
    public void executeTestWithWrongParametersFormat() throws IOException, ServletException {
        when(request.getParameter(TICKET_ID)).thenReturn("1k5");
        when(request.getParameter(DISCOUNT)).thenReturn("fokof");
        when(session.getAttribute(SHIP_ID)).thenReturn("dkdkllkijor");

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeTestWithCorrectData() throws IOException, ServletException {
        ticketId = "16";
        discount = "55";
        shipId = "1";

        when(request.getParameter(TICKET_ID)).thenReturn(ticketId);
        when(request.getParameter(DISCOUNT)).thenReturn(discount);
        when(session.getAttribute(SHIP_ID)).thenReturn(shipId);

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);

        TicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket ticket = new Ticket(Integer.parseInt(ticketId));
        try {
            ticketDao.setTicketProperties(ticket);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        assertEquals(Integer.parseInt(discount), ticket.getDiscount());
    }

    @Test
    public void executeTestWithTicketFromAnotherShip() throws IOException, ServletException {
        ticketId = "9";
        discount = "55";
        shipId = "1";

        when(request.getParameter(TICKET_ID)).thenReturn(ticketId);
        when(request.getParameter(DISCOUNT)).thenReturn(discount);
        when(session.getAttribute(SHIP_ID)).thenReturn(shipId);

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);

        TicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket ticket = new Ticket(Integer.parseInt(ticketId));
        try {
            ticketDao.setTicketProperties(ticket);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        assertEquals(0, ticket.getDiscount());
    }
}
