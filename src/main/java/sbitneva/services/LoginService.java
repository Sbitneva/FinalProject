package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;

import java.sql.SQLException;
import java.util.Objects;

public class LoginService {
    static Logger log = Logger.getLogger(LoginService.class.getName());

    private static LoginService loginService = new LoginService();
    private int userId;

    private LoginService() {

    }

    public static LoginService getLoginService() {
        return loginService;
    }

    public boolean verify(String email, String password) throws SQLException, DAOException {

        if ((email == null) || password == null) {
            return false;
        }

        UserDao userDao = DaoFactory.getUserDao();
        User user = userDao.getClientByEmailAndPassword(email, password);

        log.debug(user.toString());

        if (Objects.nonNull(user)) {
            this.userId = user.getUserId();
            return true;
        }

        return false;
    }

    public int getUserId() {
        return this.userId;
    }
}
