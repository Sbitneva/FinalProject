package sbitneva.command.client;

import org.apache.log4j.Logger;
import sbitneva.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowClientInfoCommand implements Command {
    private static Logger log = Logger.getLogger(ShowClientInfoCommand.class.getName());

    private static final String CLIENT_PAGE_PATH = "jsp/client/client-page.jsp";
    private static final String USER_ATTRIBUTE = "client";
    private static final String CLIENT_ID_ATTRIBUTE = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
