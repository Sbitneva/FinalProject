package sbitneva.transactions;

import org.apache.log4j.Logger;
import sbitneva.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Transaction manager.
 */
public final class TransactionManager {

    private static Logger log = Logger.getLogger(TransactionManager.class.getName());

    private static ThreadLocal<ConnectionPoolWrapper> threadLocal = new ThreadLocal<>();

    private TransactionManager() {
    }

    /**
     * Start DB transaction.
     *
     * @throws SQLException DB access errors
     * @throws TransactionException ThreadLocal isn't empty
     */
    public static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get())) {
            throw new TransactionException("ThreadLocal for begin transaction is not empty");
        }
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        ConnectionPoolWrapper wrapper = new ConnectionPoolWrapper(connection, true);
        threadLocal.set(wrapper);
        log.debug(threadLocal.get().hashCode());
        log.debug("Transaction start");
    }

    /**
     * Stop DB transaction.
     *
     * @throws SQLException DB access errors
     * @throws TransactionException ThreadLocal isn't empty
     */
    public static void endTransaction() throws SQLException, TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            throw new TransactionException("ThreadLocal for end transaction is not empty");
        }
        ConnectionPoolWrapper wrapper = threadLocal.get();
        log.debug(threadLocal.get().hashCode());
        wrapper.getConnection().commit();
        wrapper.getConnection().close();
        threadLocal.set(null);
        log.debug("Transaction end");
    }

    /**
     * Transaction rollback.
     *
     * @throws TransactionException Rollback error
     */
    public static void rollbackTransaction() throws TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            return;
        }
        try {
            ConnectionPoolWrapper wrapper = threadLocal.get();
            Connection connection = wrapper.getConnection();
            log.debug(threadLocal.get().hashCode());
            connection.setAutoCommit(false);
            connection.rollback();
            connection.close();
            threadLocal.set(null);
            log.debug("Transaction rollback");
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
            throw new TransactionException("Rollback error");
        }
    }

    /**
     * Get connection.
     *
     * @return Connection pool wrapper
     * @throws SQLException DB access errors
     */
    public static ConnectionPoolWrapper getConnection() throws SQLException {
        if (Objects.isNull(threadLocal.get())) {
            Connection connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            return new ConnectionPoolWrapper(connection, false);
        } else {
            return threadLocal.get();
        }
    }
}
