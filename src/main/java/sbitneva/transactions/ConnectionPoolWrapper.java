package sbitneva.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionPoolWrapper {
    private final Connection connection;
    private final boolean isTransaction;


    public ConnectionPoolWrapper(Connection connection, boolean isTransaction) throws SQLException {
        this.connection = ConnectionPool.getConnection();
        this.isTransaction = isTransaction;

    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (!isTransaction) {
            connection.close();
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}
