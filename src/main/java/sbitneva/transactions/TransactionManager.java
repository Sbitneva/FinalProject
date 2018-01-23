package sbitneva.transactions;

import sbitneva.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManager {
    private static ThreadLocal<ConnectionPoolWrapper> threadLocal = new ThreadLocal<>();
    private TransactionManager() {
    }

    public static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get()))
            throw new TransactionException("ThreadLocal for transaction is not empty");
        Connection connection = ConnectionPool.getConnection();
        ConnectionPoolWrapper wrapper = new ConnectionPoolWrapper(connection,true);
        threadLocal.set(wrapper);
    }

    public static void endTransaction() throws SQLException, TransactionException {
        if (Objects.isNull(threadLocal.get()))
            throw new TransactionException("ThreadLocal for transaction is not empty");
        ConnectionPoolWrapper wrapper = threadLocal.get();
        wrapper.getConnection().close();
        threadLocal.set(null);
    }

    public static ConnectionPoolWrapper getConnection() throws SQLException {
        if (Objects.isNull(threadLocal.get())){
            Connection connection = ConnectionPool.getConnection();
            ConnectionPoolWrapper wrapper = new ConnectionPoolWrapper(connection,false);
            return wrapper;
        } else {return threadLocal.get();}
    }
}
