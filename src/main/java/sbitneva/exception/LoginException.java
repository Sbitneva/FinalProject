package sbitneva.exception;

/**
 * Login exception.
 */
public class LoginException extends RuntimeException {

    /**
     * Exception with message constructor.
     *
     * @param message Message to pass to the handler
     */
    public LoginException(final String message) {
        super(message);
    }
}
