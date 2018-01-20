package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BasicDao {

    private static Logger log = Logger.getLogger(ComfortLevelDao.class.getName());

    public Map<Integer, String> getIdNameDataFromTable(String sql) throws SQLException {
        Map<Integer, String> comfortLevels = new HashMap<>();
        Connection con = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comfortLevels.put(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return comfortLevels;
    }

}
