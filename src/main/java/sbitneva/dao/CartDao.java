package sbitneva.dao;

import org.apache.log4j.Logger;
import sbitneva.entity.Cart;
import sbitneva.entity.Ticket;
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

    public Cart getUserCart(int userId) throws SQLException{
        Connection connection = ConnectionPool.getConnection();
        Cart cart = new Cart();
        try{
            PreparedStatement statement = connection.prepareStatement(GET_TICKETS_IDs_FROM_CART);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                cart.addTicketWithId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            log.error(e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        return cart;
    }

}
