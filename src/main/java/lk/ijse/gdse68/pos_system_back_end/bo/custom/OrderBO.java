package lk.ijse.gdse68.pos_system_back_end.bo.custom;

import lk.ijse.gdse68.pos_system_back_end.bo.SuperBO;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    boolean placeOrder(Connection connection, OrderDTO orderDTO) throws SQLException;

    String getLastId(Connection connection) throws SQLException;

    OrderDTO getOrderById(Connection connection, String id) throws SQLException;
}
