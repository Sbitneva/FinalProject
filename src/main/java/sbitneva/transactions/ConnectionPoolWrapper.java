package sbitneva.transactions;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Connection pool wrapper.
 */
public class ConnectionPoolWrapper {
    private static Logger log = Logger.getLogger(ConnectionPoolWrapper.class.getName());

    private final Connection connection;
    private final boolean isTransaction;

    /**
     * Connection pool wrapper constructor.
     *
     * @param connection Connection
     * @param isTransaction Transaction or not
     */
    public ConnectionPoolWrapper(final Connection connection, final boolean isTransaction) {
        this.connection = connection;
        this.isTransaction = isTransaction;
    }

    /**
     * Get connection.
     *
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Close pool wrapper.
     *
     * @throws SQLException DB access errors
     */
    public void close() throws SQLException {
        if (!isTransaction) {
            connection.close();
        }
    }

    /**
     * Prepare SQL statement.
     *
     * @param sql Sql statement
     * @return Prepared statement
     * @throws SQLException Statement error
     */
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

}
