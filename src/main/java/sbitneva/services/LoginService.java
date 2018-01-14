package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;

import java.sql.SQLException;

public class LoginService {
    private static Logger log = Logger.getLogger(LoginService.class.getName());

    private static LoginService loginService = new LoginService();

    private LoginService() {

    }

    public static LoginService getLoginService() {
        return loginService;
    }

    public User verify(String email, String password) {
        User user = null;

        if ((email.isEmpty()) || password.isEmpty()) {
            return user;
        }

        UserDao userDao = DaoFactory.getUserDao();
        try {
            user = userDao.getClientByEmailAndPassword(email, password);
        } catch (DAOException | SQLException e) {
            log.error(e.getMessage());
        }
        return user;
    }

}
