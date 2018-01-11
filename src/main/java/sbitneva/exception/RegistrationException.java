package sbitneva.exception;

public class RegistrationException extends Exception {
    public RegistrationException(String mess) {
    }

    public RegistrationException(DAOException e) {
    }
}
