package commands.client;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.servlets.ServletDispatcher;

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
import static sbitneva.command.factory.FactoryCommand.ADD_TO_CART;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class AddTicketToCartCommandTest {

    static Logger log = Logger.getLogger(AddTicketToCartCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(ADD_TO_CART);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void executionTestWithCorrectParameters() throws ServletException, IOException{
        when(request.getParameter(TICKET_ID)).thenReturn("29");
        when(request.getParameter(PAGE)).thenReturn("1");
        when(request.getParameter(SHIP_ID)).thenReturn("1");
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn("10");
        ServletDispatcher dispatcher = new ServletDispatcher();

        dispatcher.init();
        dispatcher.processRequest(request, response);

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            byte isInCart = cartDao.isTicketInCart(10, 29);
            assertEquals(1, isInCart);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }

    @Test
    public void executionTestWithWrongParameters() throws ServletException, IOException{
        String userId = "10";
        String ticketId = "100";
        when(request.getParameter(TICKET_ID)).thenReturn(ticketId);
        when(request.getParameter(PAGE)).thenReturn(null);
        when(request.getParameter(SHIP_ID)).thenReturn("1");
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(userId);
        ServletDispatcher dispatcher = new ServletDispatcher();

        dispatcher.init();
        dispatcher.processRequest(request, response);

        CartDao cartDao = DaoFactory.getCartDao();
        try {
            byte isInCart = cartDao.isTicketInCart(Integer.parseInt(userId), Integer.parseInt(ticketId));
            assertEquals(0, isInCart);
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }
}
