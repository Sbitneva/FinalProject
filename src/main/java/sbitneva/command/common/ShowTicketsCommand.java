package sbitneva.command.common;

import org.apache.log4j.Logger;
import sbitneva.command.factory.Command;
import sbitneva.command.CommandsHelper;
import sbitneva.dao.UserDao;
import sbitneva.entity.Ship;
import sbitneva.services.ShowTicketsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowTicketsCommand implements Command {

    private static Logger log = Logger.getLogger(UserDao.class.getName());
    private static final String SHIP_ID_PARAMETER = "shipId";
    private static final String SHIP_ATTR_NAME = "ship";
    private static final String TICKETS_PAGE = "jsp/client/tickets.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter(SHIP_ID_PARAMETER) != null) {
            if(CommandsHelper.isParameterAcceptableInteger(request.getParameter(SHIP_ID_PARAMETER))) {
                int shipId = Integer.parseInt(request.getParameter(SHIP_ID_PARAMETER));
                ShowTicketsService showTicketsService = ShowTicketsService.getShowTicketsService();
                Ship ship = showTicketsService.getTickets(shipId);
                if(ship == null){
                    log.debug("undefined ship requested");
                    //TODO:call showCruises command - ship undefined in db
                    request.getRequestDispatcher("/Cruise?command=getCruises").forward(request, response);
                }else{
                    request.setAttribute(SHIP_ATTR_NAME, ship);
                    request.getRequestDispatcher(TICKETS_PAGE).forward(request, response);
                }
            }

        } else {
            request.getRequestDispatcher("/Cruise?command=getCruises").forward(request, response);
        }

    }
}
