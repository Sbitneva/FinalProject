package sbitneva.transactions;

import sbitneva.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManager {
    private static ThreadLocal<ConnectionWrapper> threadLocal = new ThreadLocal<>();

    private TransactionManager() {
    }

    public static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get()))
            throw new TransactionException();
        Connection connection = ConnectionPool.getConnection();
        ConnectionWrapper wrapper = new ConnectionWrapper(connection, true);
        threadLocal.set(wrapper);
    }

    public static void endTransaction() throws SQLException, TransactionException {
        if (Objects.isNull(threadLocal.get()))
            throw new TransactionException();
        ConnectionWrapper wrapper = threadLocal.get();
        wrapper.getConnection().close();
        threadLocal.set(null);
    }

    public static ConnectionWrapper getConnection() throws SQLException {
        if (Objects.isNull(threadLocal.get())) {
            Connection connection = ConnectionPool.getConnection();
            ConnectionWrapper wrapper = new ConnectionWrapper(connection, false);
            return wrapper;
        } else {
            return threadLocal.get();
        }
    }
}
