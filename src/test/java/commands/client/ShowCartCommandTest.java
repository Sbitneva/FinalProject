package commands.client;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.CartDao;
import sbitneva.dao.DaoFactory;
import sbitneva.entity.Cart;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;
import static sbitneva.command.factory.FactoryCommand.SHOW_CART;

public class ShowCartCommandTest {

    static Logger log = Logger.getLogger(ShowCartCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(SHOW_CART);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeTestWithCorrectParameters() throws IOException, ServletException {
        int userId = 5;
        when(request.getSession().getAttribute(anyString())).thenReturn(userId);
        Cart cartBefore = new Cart();
        Cart cartAfter = new Cart();
        try {
            CartDao cartDao = DaoFactory.getCartDao();

            TransactionManager.beginTransaction();
            cartDao.addTicketToCart(userId, 27);
            cartDao.addTicketToCart(userId, 12);
            cartDao.addTicketToCart(userId, 13);
            cartDao.addTicketToCart(userId, 14);

            cartDao.addTicketToCart(userId, 1);
            cartDao.addTicketToCart(userId, 8);
            cartBefore = cartDao.getUserCart(userId);
            TransactionManager.endTransaction();

            assertEquals(6, cartBefore.getTickets().size());

            ServletDispatcher dispatcher = new ServletDispatcher();
            dispatcher.init();
            dispatcher.processRequest(request, response);

            cartAfter = cartDao.getUserCart(userId);
            assertEquals(4, cartAfter.getTickets().size());

        } catch (SQLException | TransactionException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
            if (e.getClass() == TransactionException.class) {
                try {
                    TransactionManager.endTransaction();
                } catch (TransactionException | SQLException e1) {
                    log.error(e1.getClass().getSimpleName() + ":" + e1.getMessage());
                }
            }
        }
    }

    @Test
    public void executeTestWithWrongParameters() throws IOException, ServletException {
        when(request.getSession().getAttribute(anyString())).thenReturn("ofkfk");

        ServletDispatcher dispatcher = new ServletDispatcher();
        dispatcher.init();
        dispatcher.processRequest(request, response);
    }
}
