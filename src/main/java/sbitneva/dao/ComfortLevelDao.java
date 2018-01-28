package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Service;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Comfort level DAO.
 */
public class ComfortLevelDao extends BasicDao {

    private static final String GET_COMFORT_LEVEL_NAME =
            "SELECT comfort_level_name FROM comfort_levels WHERE (comfort_level_id = ?)";
    private static final String GET_COMFORT_LEVELS =
            "SELECT * FROM comfort_levels";
    private static final String GET_SERVICES_BY_COMFORT_LEVEL_ID =
            "SELECT * FROM services INNER JOIN "
            + "many_services_has_many_comfort_levels ON "
            + "(services.service_id = many_services_has_many_comfort_levels.service_id_services "
            + "AND many_services_has_many_comfort_levels.comfort_level_id_comfort_levels = ?)";
    private static Logger log = Logger.getLogger(ComfortLevelDao.class.getName());

    /**
     * Get comfort level information.
     *
     * @param comfortLevelId Comfort level ID
     * @return List of services
     * @throws SQLException DB access errors
     */
    public ArrayList<Service> getComfortLevelInfo(final int comfortLevelId) throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        ConnectionPoolWrapper con = TransactionManager.getConnection();
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
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        con.close();
        return services;
    }

    /**
     * Get comfort levels.
     *
     * @return Comfort levels list.
     * @throws SQLException DB access errors
     */
    public Map<Integer, String> getComfortLevels() throws SQLException {
        return super.getIdNameDataFromTable(GET_COMFORT_LEVELS);
    }

    /**
     * Get comfort level name by comfort level ID.
     *
     * @param comfortLevelId Comfort level ID
     * @return Comfort level name
     * @throws SQLException DB access errors
     */
    public String getNameById(final int comfortLevelId) throws SQLException {
        return super.getNameById(GET_COMFORT_LEVEL_NAME, comfortLevelId);
    }

}
