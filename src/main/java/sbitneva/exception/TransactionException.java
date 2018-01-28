package sbitneva.exception;

/**
 * Transaction exception.
 */
public class TransactionException extends Throwable {
    /**
     * Inherited constructor.
     *
     * @param message Message to pass to the handler
     */
    public TransactionException(final String message) {
        super(message);
    }
}
