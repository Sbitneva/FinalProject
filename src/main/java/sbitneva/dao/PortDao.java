package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.exception.DAOException;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PortDao {
    private static Logger log = Logger.getLogger(PortDao.class.getName());

    private final String GET_PORTNAME_BY_ID = "select port_name from ports where port_id = ?";
    private final String GET_PORT_ID_BY_EXCURSION_ID = "select port_id_ports from excursions where excursion_id=?";

    public String getPortNameById(int id) throws SQLException, DAOException{
        String portName = "";
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_PORTNAME_BY_ID);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                portName = resultSet.getString(1);
            }
        }catch (SQLException e){
            log.error(e.getMessage());
        }
        con.close();
        return portName;
    }

    public int getPortIdByExcursionId(int excursionId) throws SQLException, DAOException{

        int portId = 0;
        ConnectionWrapper con = TransactionManager.getConnection();
        try{
            PreparedStatement statement = con.preparedStatement(GET_PORT_ID_BY_EXCURSION_ID);
            statement.setInt(1, excursionId);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                portId = resultSet.getInt(1);
            }
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return portId;
    }
}
