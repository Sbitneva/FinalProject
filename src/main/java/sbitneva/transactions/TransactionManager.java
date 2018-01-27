package sbitneva.transactions;

import org.apache.log4j.Logger;
import sbitneva.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManager {
    private static Logger log = Logger.getLogger(TransactionManager.class.getName());
    private static ThreadLocal<ConnectionPoolWrapper> threadLocal = new ThreadLocal<>();

    private TransactionManager() {
    }

    public static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get()))
            throw new TransactionException("ThreadLocal for begin transaction is not empty");
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        ConnectionPoolWrapper wrapper = new ConnectionPoolWrapper(connection, true);
        threadLocal.set(wrapper);
        log.debug(threadLocal.get().hashCode());
        log.debug("Transaction start");
    }

    public static void endTransaction() throws SQLException, TransactionException {
        if (Objects.isNull(threadLocal.get()))
            throw new TransactionException("ThreadLocal for end transaction is not empty");
        ConnectionPoolWrapper wrapper = threadLocal.get();
        log.debug(threadLocal.get().hashCode());
        wrapper.getConnection().commit();
        wrapper.getConnection().close();
        threadLocal.set(null);
        log.debug("Transaction end");
    }

    public static void rollbackTransaction() throws TransactionException {
        if (Objects.isNull(threadLocal.get()))
            return;
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
