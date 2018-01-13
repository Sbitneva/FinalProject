package sbitneva.services;

import org.apache.log4j.Logger;
import sbitneva.exception.DAOException;
import sbitneva.exception.RegistrationException;
import sbitneva.exception.TransactionException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;

public class RegistrationService {
    static Logger log = Logger.getLogger(RegistrationService.class.getName());

    private static RegistrationService loginService = new RegistrationService();
    private static RegistrationService registrationService = new RegistrationService();
    private final String CHECK_EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[" +
            "\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
            "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private RegistrationService() {
    }

    public static RegistrationService getLoginService() {
        return loginService;
    }

    public static RegistrationService getRegistrationService() {
        return registrationService;
    }

    public void register(String firstName, String lastName, String email, String password) {

        try {
            log.debug("firstName = " + URLDecoder.decode(firstName, "UTF-8") + " lastName =" + lastName +
                    " email = " + email + " password = " + password);
        } catch ( UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }

        if(!email.isEmpty()){
            Pattern pattern = Pattern.compile(CHECK_EMAIL_REGEXP, UNICODE_CHARACTER_CLASS);
            Matcher m = pattern.matcher(email);
            String s;
            if (m.find()){
                s = m.group();
                if(!s.equals(email)) {
                    //TODO: throw Registration exception
                    return;
                }
            }
        } else {
            return;
        }



    }
}
