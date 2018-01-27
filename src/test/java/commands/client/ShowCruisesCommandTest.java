package commands.client;

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
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static sbitneva.command.CommandsHelper.PAGE;
import static sbitneva.command.factory.FactoryCommand.CLIENT;
import static sbitneva.command.factory.FactoryCommand.GET_CRUISES;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class ShowCruisesCommandTest {

    static Logger log = Logger.getLogger(ShowCruisesCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(GET_CRUISES);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeTestWithWrongParameters() throws IOException, ServletException {

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);

    }
}
