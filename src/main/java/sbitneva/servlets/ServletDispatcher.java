package sbitneva.servlets;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.command.factory.FactoryCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Cruise")

public class ServletDispatcher extends HttpServlet {
    static Logger log = Logger.getLogger(ServletDispatcher.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
        log.debug("Get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Post");
        processRequest(req, resp);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        log.debug("Servlet initialization");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FactoryCommand factoryCommand = FactoryCommand.getInstance();
        Command command = factoryCommand.getCommand(request);
        if (command != null) {
            command.execute(request, response);
        }
    }
}
