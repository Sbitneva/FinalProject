package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.ComfortLevel;
import sbitneva.entity.Excursion;
import sbitneva.entity.Service;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComfortLevelDao {
    private static Logger log = Logger.getLogger(ComfortLevelDao.class.getName());

    private static final String GET_SERVICES_BY_COMFORT_LEVEL_ID = "select * from services inner join " +
            "many_services_has_many_comfort_levels on" +
            " (services.service_id = many_services_has_many_comfort_levels.service_id_services " +
            "and many_services_has_many_comfort_levels.comfort_level_id_comfort_levels = ?)";
    private static final String GET_COMFORT_LEVEL_NAME_BY_ID = "select comfort_level_name from comfort_levels " +
            "where comfort_level_id = ?";

    public ArrayList<Service> getComfortLevelInfo(int comfortLevelId) throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        ConnectionWrapper con = TransactionManager.getConnection();
        try{
            PreparedStatement statement = con.preparedStatement(GET_SERVICES_BY_COMFORT_LEVEL_ID);
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

    public String getComfortLevelNameById(int comfortLevelId) throws SQLException {
        String name = "";
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_COMFORT_LEVEL_NAME_BY_ID);
            statement.setInt(1, comfortLevelId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                name = resultSet.getString(1);
            }
        } catch (SQLException e){
            log.error(e.getMessage());
        }
        con.close();
        return name;
    }
}
