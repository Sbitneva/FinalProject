package sbitneva.transactions;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionPoolWrapper {
    private static Logger log = Logger.getLogger(ConnectionPoolWrapper.class.getName());
    private final Connection connection;
    private final boolean isTransaction;


    public ConnectionPoolWrapper(Connection connection, boolean isTransaction) throws SQLException {
        this.connection = connection;
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
