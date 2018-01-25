package commands.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.command.common.LoginCommand;
import sbitneva.servlets.ServletDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sbitneva.command.CommandsHelper.EMAIL;
import static sbitneva.command.CommandsHelper.PASSWORD;
import static sbitneva.command.factory.FactoryCommand.LOGIN;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;

public class LoginCommandTest {

    static Logger log = Logger.getLogger(LoginCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(LOGIN);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeWithEmptyParameters() throws ServletException, IOException {
        when(request.getParameter(EMAIL)).thenReturn("");
        when(request.getParameter(PASSWORD)).thenReturn("");
        ServletDispatcher dispatcher = new ServletDispatcher();

        dispatcher.init();
        dispatcher.processRequest(request, response);
        assertEquals(false, LoginCommand.isFullRequest);
    }

    @Test
    public void executeWithNotExistedUser() throws ServletException, IOException {
        when(request.getParameter(EMAIL)).thenReturn("olololl@mail.ru");
        when(request.getParameter(PASSWORD)).thenReturn("1234");
        ServletDispatcher dispatcher = new ServletDispatcher();

        dispatcher.init();
        dispatcher.processRequest(request, response);
        assertEquals(false, LoginCommand.isFullRequest);
        dispatcher.destroy();
    }

    @Test
    public void executeWithExistedUser() throws ServletException, IOException {
        when(request.getParameter(EMAIL)).thenReturn("masha.sbitneva@gmail.com");
        when(request.getParameter(PASSWORD)).thenReturn("1234");
        ServletDispatcher dispatcher = new ServletDispatcher();

        dispatcher.init();
        dispatcher.processRequest(request, response);
        assertEquals(true, LoginCommand.isFullRequest);
        dispatcher.destroy();
    }
}
