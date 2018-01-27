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
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static sbitneva.command.factory.FactoryCommand.CLIENT;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;
import static sbitneva.command.CommandsHelper.PAGE;

public class ShowClientInfoCommandTest {
    static Logger log = Logger.getLogger(ShowClientInfoCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(CLIENT);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeTestWithCorrectParameters() throws IOException, ServletException {
        when(request.getSession().getAttribute(anyString())).thenReturn(5);
        when(request.getParameter(PAGE)).thenReturn("1");
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeTestWithWrongParameters() throws IOException, ServletException {
        when(request.getSession().getAttribute(anyString())).thenReturn(100);
        when(request.getParameter(PAGE)).thenReturn("hthth1");
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }


}
