package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Service;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComfortLevelDao {
    private static Logger log = Logger.getLogger(ComfortLevelDao.class.getName());

    private static final String GET_SERVICES_BY_COMFORT_LEVEL_ID = "select * from services inner join " +
            "many_services_has_many_comfort_levels on" +
            " (services.service_id = many_services_has_many_comfort_levels.service_id_services " +
            "and many_services_has_many_comfort_levels.comfort_level_id_comfort_levels = ?)";
    public static final String GET_COMFORT_LEVELS = "select * from comfort_levels";

    public ArrayList<Service> getComfortLevelInfo(int comfortLevelId) throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        Connection con = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(GET_SERVICES_BY_COMFORT_LEVEL_ID);
            statement.setInt(1, comfortLevelId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceName(resultSet.getString(2));
                services.add(service);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return services;
    }


}
