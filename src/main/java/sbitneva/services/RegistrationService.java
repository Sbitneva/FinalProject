package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.dao.DaoFactory;
import sbitneva.dao.UserDao;
import sbitneva.entity.User;
import sbitneva.exception.DAOException;
import sbitneva.exception.RegistrationException;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

public class RegistrationService {
    private static Logger log = Logger.getLogger(RegistrationService.class.getName());
    private final static String CHECK_EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[" +
            "\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
            "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private final static String CHECK_USERNAME_REGEXP = "[\\p{Alpha}\\-]{3,}";
    private static RegistrationService loginService = new RegistrationService();
    private static RegistrationService registrationService = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getLoginService() {
        return loginService;
    }

    public static RegistrationService getRegistrationService() {
        return registrationService;
    }

    public int register(String firstName, String lastName, String email, String password)
            throws RegistrationException {
        log.debug("firstName = " + firstName + " lastName =" + lastName +
                " email = " + email + " password = " + password);
        boolean verified = false;
        User user = new User();
        try {
            verified = verifyUserData(firstName, lastName, email, password);
        } catch (RegistrationException e) {
            log.error(e.getMessage());
        }

        if (verified) {
            UserDao userDao = DaoFactory.getUserDao();
            try {
                int i = userDao.addNewUser(firstName, lastName, email, password);
                if (i != 1) {
                    log.error("New user didn't created");
                    throw new RegistrationException("New user didn't created");
                } else {
                    user = userDao.getClientByEmailAndPassword(email, password);
                    log.debug("Registration is succeeded");
                }
            } catch (SQLException | DAOException e) {
                log.error(e.getMessage());
            }
        }

        return user.getUserId();
    }

    private boolean verifyEmail(String email) throws RegistrationException {
        boolean result = false;
        if (!email.isEmpty()) {
            Pattern pattern = Pattern.compile(CHECK_EMAIL_REGEXP, UNICODE_CHARACTER_CLASS);
            Matcher m = pattern.matcher(email);
            String s;
            if (m.find()) {
                s = m.group();
                if (!s.equals(email)) {
                    log.error("Wrong email");
                    throw new RegistrationException("Wrong email");
                }
                log.debug("Email is valid");
                result = true;
            }
        } else {
            log.error("Email field is empty");
            throw new RegistrationException("Email field is empty");
        }
        return result;
    }

    private boolean verifyUserData(String firstName, String lastName, String email, String password) throws RegistrationException {
        boolean result = false;
        try {
            if (verifyName(firstName) && verifyName(lastName) && !password.isEmpty() && verifyEmail(email)) {
                result = true;
            }

        } catch (RegistrationException e) {
            throw e;
        }
        return result;
    }

    private boolean verifyName(String name) throws RegistrationException {

        boolean result = false;
        if (!name.isEmpty()) {
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
        } else {
            log.error("Name field is empty");
            throw new RegistrationException("Name field is empty");
        }
        return result;
    }


}
