package sbitneva.services.common;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.Client;
import sbitneva.exception.DaoException;
import sbitneva.exception.RegistrationException;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.TransactionManager;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

public class RegistrationService {

    private final static String CHECK_EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[" +
            "\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
            "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final static String CHECK_USERNAME_REGEXP = "[\\p{Alpha}\\-]{3,}";
    private static Logger log = Logger.getLogger(RegistrationService.class.getName());
    private static RegistrationService registrationService = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getRegistrationService() {
        return registrationService;
    }

    public int register(String firstName, String lastName, String email, String password)
            throws RegistrationException {
        log.debug("firstName = " + firstName + " lastName =" + lastName +
                " email = " + email + " password = " + password);
        boolean verified = false;
        Client client = new Client();

        verified = verifyUserData(firstName, lastName, email, password);

        if (verified) {
            UserDao userDao = DaoFactory.getUserDao();
            try {
                TransactionManager.beginTransaction();
                int i = userDao.addNewUser(firstName, lastName, email, password);
                TransactionManager.endTransaction();
                if (i != 1) {
                    log.error("New client didn't created");
                    throw new RegistrationException("New client didn't created");
                } else {
                    client = userDao.getClientByEmailAndPassword(email, password);
                    log.debug("Registration is succeeded");
                }

            } catch (SQLException | DaoException | TransactionException e) {
                log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
                try {
                    TransactionManager.endTransaction();
                } catch (SQLException | TransactionException e1) {
                    log.error(e1.getClass().getSimpleName() + " : " + e1.getMessage());
                }
            }
        }
        return client.getClientId();
    }

    private boolean verifyEmail(String email) throws RegistrationException {

        boolean result = false;

        Pattern pattern = Pattern.compile(CHECK_EMAIL_REGEXP, UNICODE_CHARACTER_CLASS);
        Matcher m = pattern.matcher(email);
        String s;
        if (m.find()) {
            s = m.group();
            if (s.equals(email)) {
                result = true;
                log.debug("Email is valid");
            }
        }
        if (!result) {
            log.error("Wrong email");
            throw new RegistrationException("Wrong email");
        }

        return result;
    }

    private boolean verifyUserData(String firstName, String lastName, String email, String password) throws RegistrationException {
        boolean result = false;

        if (verifyName(firstName) && verifyName(lastName) && verifyEmail(email)) {
            result = true;
        }
        return result;
    }

    private boolean verifyName(String name) throws RegistrationException {
        boolean result = false;
        Pattern pattern = Pattern.compile(CHECK_USERNAME_REGEXP, UNICODE_CHARACTER_CLASS);
        Matcher m = pattern.matcher(name);
        String s;
        if (m.find()) {
            s = m.group();
            if (!s.equals(name)) {
                log.error("Wrong name");
                throw new RegistrationException("Wrong name");
            }
            log.debug("Name is valid");
            result = true;
        }
        return result;
    }
}
