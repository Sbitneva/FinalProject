package sbitneva.exception;

/**
 * DAO exception.
 */
public class DaoException extends Throwable {

    /**
     * Default exception constructor.
     */
    public DaoException() {
    }

    /**
     * Exception with message constructor.
     *
     * @param message Message to pass to handler
     */
    public DaoException(final String message) {
        super(message);
    }
}
