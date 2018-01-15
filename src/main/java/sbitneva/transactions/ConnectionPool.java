package sbitneva.transactions;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_PATH = "jdbc:postgresql://";
    private static final String DB_LOCAL_PATH = "localhost:5432/cruise_company";
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
        try {
            String environmentVar = System.getenv("DATABASE_URL");
            String dbUrl;
            URI dbUri = null;
            if(environmentVar != null) {
                dbUri = new URI(environmentVar);
                dbUrl = DB_PATH + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
                log.debug("Using database uri from environment :" + dbUrl);
            } else{
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
            try {
                connectionPool.getConnection().setAutoCommit(false);
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        } catch (URISyntaxException e) {

        }
        return connectionPool;
    }

}
