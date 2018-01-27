package commands.common;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
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
import static sbitneva.command.CommandsHelper.*;
import static sbitneva.command.factory.FactoryCommand.PARAM_NAME_COMMAND;
import static sbitneva.command.factory.FactoryCommand.REGISTRATION;


public class RegistrationCommandTest {

    static Logger log = Logger.getLogger(RegistrationCommandTest.class.getName());

    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class, CALLS_REAL_METHODS);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PARAM_NAME_COMMAND)).thenReturn(REGISTRATION);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void executeTestCorrectData() throws ServletException, IOException {
        String firstName = "Кирилл";
        String lastName = "Крылов";
        String email = "kirill.krilov@gmail.com";
        String password = "kirill";

        when(request.getParameter(FIRST_NAME)).thenReturn(firstName);
        when(request.getParameter(LAST_NAME)).thenReturn(lastName);
        when(request.getParameter(EMAIL)).thenReturn(email);
        when(request.getParameter(PASSWORD)).thenReturn(password);

        ServletDispatcher servletDispatcher = new ServletDispatcher();
        servletDispatcher.init();
        servletDispatcher.processRequest(request, response);


        UserDao userDao = DaoFactory.getUserDao();
        Client client = new Client();
        try {
            client = userDao.getClientByEmailAndPassword(email, password);
        } catch (SQLException | DaoException e) {
            log.error(e.getClass().getSimpleName() + e.getMessage());
        }
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(email, client.getEmail());
        assertEquals(password, client.getPassword());
    }
}
