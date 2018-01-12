package sbitneva.transactions;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_PATH = "jdbc:postgresql://localhost:5432/cruise_company";
    private static final String DB_LOGIN = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static Logger log = Logger.getLogger(ConnectionPool.class.getName());
    private static BasicDataSource connectionPool = initDataSource();

    private ConnectionPool() {

    }

    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    private static BasicDataSource initDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        URI dbUri = null;
        try {
            dbUri = new URI("localhost:5432/cruise_company");
        } catch (URISyntaxException e) {

        } finally {
            String dbUrl = DB_PATH;
            connectionPool = new BasicDataSource();

            if (dbUri.getUserInfo() == null) {
                connectionPool.setUsername(DB_LOGIN);
                connectionPool.setPassword(DB_PASSWORD);
            }
            connectionPool.setDriverClassName(DB_DRIVER);
            connectionPool.setUrl(dbUrl);
            connectionPool.setInitialSize(1);
            connectionPool.setMaxOpenPreparedStatements(100);
            try {
                connectionPool.getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }

        return connectionPool;
    }

}
