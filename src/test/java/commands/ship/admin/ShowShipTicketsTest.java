package commands.ship.admin;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.configaration.SecurityConfiguration;
import sbitneva.servlets.ServletDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;
import static sbitneva.command.CommandsHelper.*;
import static sbitneva.command.factory.FactoryCommand.SHOW_SHIP;

public class ShowShipTicketsTest {

    static Logger log = Logger.getLogger(ApplyDiscountCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp(){
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(SHOW_SHIP);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeWithCorrectClientParameters() throws ServletException, IOException{
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(2);
        when(request.getParameter(SHIP_ID)).thenReturn("2");
        when(request.getSession().getAttribute(USER_TYPE_SESSION_ATTRIBUTE)).thenReturn(SecurityConfiguration.CLIENT_TYPE);
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeWithCorrectShipAdminParameters() throws ServletException, IOException{
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(1);
        when(request.getParameter(SHIP_ID)).thenReturn("1");
        when(request.getSession().getAttribute(USER_TYPE_SESSION_ATTRIBUTE)).thenReturn(SecurityConfiguration.SHIP_ADMIN_TYPE);
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeWithWrongClientParameters() throws ServletException, IOException{
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn("iejioejfio");
        when(request.getParameter(SHIP_ID)).thenReturn("1");
        when(request.getSession().getAttribute(USER_TYPE_SESSION_ATTRIBUTE)).thenReturn(null);
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeWithWrongShipAdminParameters() throws ServletException, IOException{
        when(request.getSession().getAttribute(USER_ID_SESSION_ATTRIBUTE)).thenReturn(1);
        /**
         * Wrong shipId
         */
        when(request.getParameter(SHIP_ID)).thenReturn("3");
        when(request.getSession().getAttribute(USER_TYPE_SESSION_ATTRIBUTE)).thenReturn(SecurityConfiguration.SHIP_ADMIN_TYPE);
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

}
