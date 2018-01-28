package sbitneva.exception;

/**
 * Requested data exception.
 */
public class RequestedDataException extends Exception {
    /**
     * Exception with message constructor.
     *
     * @param message Message to pass to the handler.
     */
    public RequestedDataException(final String message) {
        super(message);
    }
}
