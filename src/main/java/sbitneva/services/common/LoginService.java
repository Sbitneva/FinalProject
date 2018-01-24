package sbitneva.services.common;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;

import java.sql.SQLException;

public class LoginService {
    private static Logger log = Logger.getLogger(LoginService.class.getName());

    private static LoginService loginService = new LoginService();

    private LoginService() {

    }

    public static LoginService getLoginService() {
        return loginService;
    }

    public Client getUser(String email, String password) {
        Client client = null;
        UserDao userDao = DaoFactory.getUserDao();
        try {
            client = userDao.getClientByEmailAndPassword(email, password);
        } catch (DaoException | SQLException e) {
            log.error(e.getMessage());
        }
        return client;
    }

}
