package sbitneva.transactions;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool.
 */
public final class ConnectionPool {
    private static Logger log = Logger.getLogger(ConnectionPool.class.getName());

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_PATH = "jdbc:postgresql://";
    private static final String DB_LOCAL_PATH = "localhost:54321/cruise_company";
    private static final String DB_LOGIN = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static BasicDataSource connectionPool = initDataSource();

    private ConnectionPool() {

    }

    /**
     * Get connection instance from pool.
     *
     * @return Connection
     * @throws SQLException DB access errors
     */
    public static Connection getConnection() throws SQLException {
        log.debug("Active connections :" + connectionPool.getNumActive());
        return connectionPool.getConnection();
    }

    private static BasicDataSource initDataSource() {

        try {
            String environmentVar = System.getenv("DATABASE_URL");
            String dbUrl;
            URI dbUri = null;
            if (environmentVar != null) {
                dbUri = new URI(environmentVar);
                dbUrl = DB_PATH + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
                log.debug("Using database uri from environment :" + dbUrl);
            } else {
                dbUri = new URI(DB_LOCAL_PATH);
                dbUrl = DB_PATH + DB_LOCAL_PATH;
                log.debug("Using database uri from localhost :" + dbUrl);
            }

            connectionPool = new BasicDataSource();

            if (dbUri.getUserInfo() == null) {
                connectionPool.setUsername(DB_LOGIN);
                connectionPool.setPassword(DB_PASSWORD);
            }
            connectionPool.setDriverClassName(DB_DRIVER);
            connectionPool.setUrl(dbUrl);
            connectionPool.setInitialSize(1);
            connectionPool.setDefaultAutoCommit(false);
        } catch (URISyntaxException e) {
            log.error(e.getStackTrace());
        }

        return connectionPool;
    }

    /**
     * Close connection pool.
     */
    public void close() {
        try {
            connectionPool.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}
