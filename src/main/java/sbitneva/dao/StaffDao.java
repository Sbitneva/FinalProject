package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Staff;
import sbitneva.transactions.ConnectionPoolWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Staff DAO.
 */
public class StaffDao extends BasicDao {

    private static Logger log = Logger.getLogger(StaffDao.class.getName());

    private static final String GET_ALL_STAFF_BY_SHIP_ID =
            "SELECT * FROM staff WHERE ship_id_ships = ?";

    /**
     * Get ship's staff.
     *
     * @param shipId Ship ID
     * @return Staff list
     * @throws SQLException DB access errors
     */
    public ArrayList<Staff> getStaff(final int shipId) throws SQLException {
        ArrayList<Staff> staff = new ArrayList<>();
        ConnectionPoolWrapper connection = TransactionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_STAFF_BY_SHIP_ID);
            statement.setInt(1, shipId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Staff staffItem = new Staff();
                staffItem.setShipId(resultSet.getInt(5));
                staffItem.setStaffId(resultSet.getInt(1));
                staffItem.setFirstName(resultSet.getString(2));
                staffItem.setLastName(resultSet.getString(3));
                staffItem.setPosition(resultSet.getString(4));
                staff.add(staffItem);
            }

        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + ":" + e.getMessage());
        }
        connection.close();
        return staff;
    }
}
