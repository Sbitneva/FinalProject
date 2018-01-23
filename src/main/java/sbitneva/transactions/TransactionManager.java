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
            throw new TransactionException("ThreadLocal for begin transaction is not empty");
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        ConnectionPoolWrapper wrapper = new ConnectionPoolWrapper(connection,true);
        threadLocal.set(wrapper);
    }

    public static void endTransaction() throws SQLException, TransactionException {
        if (Objects.isNull(threadLocal.get()))
            throw new TransactionException("ThreadLocal for end transaction is not empty");
        ConnectionPoolWrapper wrapper = threadLocal.get();
        wrapper.getConnection().setAutoCommit(false);
        wrapper.getConnection().commit();
        wrapper.getConnection().close();
        threadLocal.set(null);
    }

    public static void rollbackTransaction() throws TransactionException {
        if (Objects.isNull(threadLocal.get()))
            return;
        try {
            ConnectionPoolWrapper wrapper = threadLocal.get();
            Connection connection = wrapper.getConnection();
            connection.rollback();
            connection.close();
            threadLocal.set(null);
        } catch (SQLException e) {
            throw new TransactionException("Rollback error");
        }
    }

    public static ConnectionPoolWrapper getConnection() throws SQLException {
        if (Objects.isNull(threadLocal.get())){
            Connection connection = ConnectionPool.getConnection();
            return new ConnectionPoolWrapper(connection,false);
        } else {return threadLocal.get();}
    }
}
