package commands.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.servlets.ServletDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sbitneva.command.BasicCommand.COMFORT_ID;
import static sbitneva.command.factory.FactoryCommand.GET_SERVICES;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class ShowServicesCommandTest {

    static Logger log = Logger.getLogger(ShowServicesCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(GET_SERVICES);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeWithNotExistedComfortLevelId() throws ServletException, IOException {
        when(request.getParameter(COMFORT_ID)).thenReturn(null);
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeWithCorrectParameters() throws ServletException, IOException {
        when(request.getParameter(COMFORT_ID)).thenReturn("16");
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

    @Test
    public void executeWithBadCorrectParameters() throws ServletException, IOException {
        when(request.getParameter(COMFORT_ID)).thenReturn("kuhijo454");
        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);
    }

}
