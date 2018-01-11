package sbitneva.services;

import sbitneva.exception.DAOException;
import sbitneva.exception.RegistrationException;
import sbitneva.exception.TransactionException;

import java.sql.SQLException;

public class RegisterService {

    private static RegisterService loginService = new RegisterService();
    private static RegisterService registerService = new RegisterService();

    private RegisterService() {
    }

    public static RegisterService getLoginService() {
        return loginService;
    }

    public static RegisterService getRegisterService() {
        return registerService;
    }

    public void register(String firstName, String lastName, String email, String password)
            throws RegistrationException, SQLException, DAOException, TransactionException {
        /*
        UserDao clientDao = DaoFactory.getUserDao();
        TransactionManager.beginTransaction();
        if (Objects.isNull(clientDao.getByLogin(login))) {
            User client = new User();
            client.setName(name);
            client.setLogin(login);
            client.setPassword(password);
            try {
                clientDao.insert(client);
            } catch (DAOException e) {
                throw new RegistrationException(e);
            }
            TransactionManager.endTransaction();
        } else {
            TransactionManager.endTransaction();
            throw new RegistrationException("User already exist");
        }
        */
    }
}
