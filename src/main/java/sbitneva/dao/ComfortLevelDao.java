package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Service;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ComfortLevelDao {

    private static Logger log = Logger.getLogger(ComfortLevelDao.class.getName());

    public static final String GET_COMFORT_LEVELS = "select * from comfort_levels";
    public static final String GET_COMFORT_LEVEL_NAME =
            "select comfort_level_name from comfort_levels where (comfort_level_id = ?)";
    private static final String GET_SERVICES_BY_COMFORT_LEVEL_ID = "SELECT * FROM services INNER JOIN " +
            "many_services_has_many_comfort_levels ON" +
            " (services.service_id = many_services_has_many_comfort_levels.service_id_services " +
            "AND many_services_has_many_comfort_levels.comfort_level_id_comfort_levels = ?)";


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

    public Map<Integer, String> getComfortLevels() throws SQLException {
        return BasicDao.getIdNameDataFromTable(GET_COMFORT_LEVELS);
    }

}
