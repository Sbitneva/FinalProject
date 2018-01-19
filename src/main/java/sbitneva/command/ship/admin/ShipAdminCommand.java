package sbitneva.command.ship.admin;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShipAdminCommand implements Command {

    static Logger log = Logger.getLogger(ShipAdminCommand.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("execution " + request.getQueryString());

    }
}
