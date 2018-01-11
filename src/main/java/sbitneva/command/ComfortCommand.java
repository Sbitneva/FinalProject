package sbitneva.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ComfortCommand implements Command {
    static Logger log = Logger.getLogger(ComfortCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int comfortLevel = Integer.parseInt(request.getParameter("comfortInfo"));
        log.debug("comfortLevel = " + comfortLevel);
    }
}
