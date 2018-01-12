package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Ticket;
import sbitneva.transactions.ConnectionPool;
import sbitneva.transactions.ConnectionWrapper;
import sbitneva.transactions.TransactionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketDao {
    private static Logger log = Logger.getLogger(TicketDao.class.getName());

    private final String GET_ALL_FREE_TICKETS = "select * from tickets where (ship_id_ships = ? and ability = true)";
    private final String BUY_TICKET = "update tickets set user_id_users=?, ability=false where ticket_id = ?";
    private final String GET_USER_ID_BY_TICKET_ID = "select user_id_users from tickets where ticket_id = ?";
    private final String GET_SHIP_ID_BY_TICKET_ID = "select ship_id_ships from tickets where ticket_id = ?";


    public ArrayList<Ticket> getAllFreeTickets(int shipId) throws SQLException {
        ArrayList<Ticket> freeTickets = new ArrayList<>();
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(GET_ALL_FREE_TICKETS);
            statement.setInt(1, shipId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getInt(1));
                ticket.setDiscount(resultSet.getInt(2));
                ticket.setPrice(resultSet.getInt(3));
                ticket.setComfortLevel(resultSet.getInt(5));
                ticket.setShipId(resultSet.getInt(6));
                freeTickets.add(ticket);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
        return freeTickets;
    }

    public void buySelectedItem(int userId, int ticketId) throws SQLException {
        ConnectionWrapper con = TransactionManager.getConnection();
        try {
            PreparedStatement statement = con.preparedStatement(BUY_TICKET);
            statement.setInt(1, userId);
            statement.setInt(2, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        con.close();
    }

    public int getUserIdByTicketId(int ticketId) throws SQLException {
        int userId = 0;
        ConnectionWrapper con = TransactionManager.getConnection();
        try{
            PreparedStatement statement = con.preparedStatement(GET_USER_ID_BY_TICKET_ID);
            statement.setInt(1, ticketId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
               userId =  resultSet.getInt(1);
            }
        }catch (SQLException e){
            log.error(e.getMessage());
        }
        con.close();
        return userId;
    }

    public int getShipByTicketId(int ticketId) throws SQLException {
        int shipId = 0;
        ConnectionWrapper con = TransactionManager.getConnection();
        try{
            PreparedStatement statement = con.preparedStatement(GET_SHIP_ID_BY_TICKET_ID);
            statement.setInt(1, ticketId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                shipId =  resultSet.getInt(1);
            }
        }catch (SQLException e){
            log.error(e.getMessage());
        }
        con.close();
        return shipId;
    }
}
