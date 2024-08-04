package lk.ijse.gdse68.pos_system_back_end.dao.custom;

import lk.ijse.gdse68.pos_system_back_end.dao.CrudDAO;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail> {
    List<OrderDetail> getAllById(Connection connection, String id) throws SQLException;
}
