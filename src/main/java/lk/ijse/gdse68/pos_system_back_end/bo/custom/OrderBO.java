package lk.ijse.gdse68.pos_system_back_end.bo.custom;

import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO {
    boolean placeOrder(Connection connection, OrderDTO orderDTO) throws SQLException;
}
