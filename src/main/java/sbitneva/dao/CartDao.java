package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
import sbitneva.exception.TransactionException;
import sbitneva.transactions.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDao {
    private static Logger log = Logger.getLogger(CartDao.class.getName());

    private static final String GET_TICKETS_IDs_FROM_CART =
            "select ticket_id from carts where (user_id_carts = ?)";

    private static final String DELETE_CART_ROW =
            "delete  from carts where(user_id_carts = ? and ticket_id = ?);";

    private static final String GET_TICKET_FROM_CART =
            "select user_id_carts from carts where (user_id_carts = ? and ticket_id = ?)";

    public Cart getUserCart(int userId) throws SQLException{
        Connection connection = ConnectionPool.getConnection();
        Cart cart = null;
        try{
            PreparedStatement statement = connection.prepareStatement(GET_TICKETS_IDs_FROM_CART);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            cart = new Cart();
            while(resultSet.next()) {
                cart.addTicketWithId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return cart;
    }

    public boolean cleanCart(ArrayList<Ticket> tickets, int userId) throws SQLException{
        boolean result = true;
        Connection connection = ConnectionPool.getConnection();
        connection.setAutoCommit(false);
        try{
            for(Ticket ticket : tickets) {
                PreparedStatement statement = connection.prepareStatement(DELETE_CART_ROW);
                statement.setInt(1, userId);
                statement.setInt(2, ticket.getTicketId());
                int row = statement.executeUpdate();
                if(row != 1) {
                    throw new TransactionException("Can't delete row from carts table");
                }
            }
            connection.commit();
        } catch (SQLException | TransactionException e){
            result = false;
            connection.rollback();
        }
        connection.close();
        return result;
    }

    public byte isTicketInCart(int userId, int ticketId) throws SQLException{
        byte isInCart = 0;
        Connection connection = ConnectionPool.getConnection();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_TICKET_FROM_CART);
            statement.setInt(1, userId);
            statement.setInt(2, ticketId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                isInCart = 1;
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        connection.close();
        return isInCart;
    }


}
