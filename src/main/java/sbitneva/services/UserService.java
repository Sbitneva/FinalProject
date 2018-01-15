package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.*;
import sbitneva.entity.Excursion;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private static Logger log = Logger.getLogger(UserService.class.getName());
    private static UserService userService = new UserService();
    private User user = new User();

    private UserService() {

    }

    public static UserService getUserService() {
        return userService;
    }

    public User verify(int userId) throws SQLException, DAOException {
        UserDao userDao = DaoFactory.getUserDao();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        User user = userDao.getUserById(userId);
        user.setTickets(ticketDao.getUserTickets(userId));
        if (user.getUserId() == 0) {
            throw new DAOException("there is no user with id = " + userId);
        } else {
            fillUserFields(user);
            return user;
        }
    }

    private void fillUserFields(User user) throws SQLException, DAOException {

        ExcursionDao excursionDao = DaoFactory.getExcursionDao();
        ShipDao shipDao = DaoFactory.getShipDao();
        PortDao portDao = DaoFactory.getPortDao();
        ArrayList<Excursion> userExcursions = new ArrayList<>();

        try {
            if (user.getTickets().size() > 0) {
                userExcursions = excursionDao.getExcursionsByUser(user.getUserId());
            }

            for (int i = 0; i < userExcursions.size(); i++) {
                userExcursions.get(i).setExcursionName(excursionDao.getExcursionNameById(userExcursions.get(i).getExcursionId()));
                userExcursions.get(i).setShipName(shipDao.getShipNameById(userExcursions.get(i).getShipId()));
                int portId = portDao.getPortIdByExcursionId(userExcursions.get(i).getExcursionId());
                userExcursions.get(i).setPortName(portDao.getPortNameById(portId));
            }

            user.setExcursions(userExcursions);

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
