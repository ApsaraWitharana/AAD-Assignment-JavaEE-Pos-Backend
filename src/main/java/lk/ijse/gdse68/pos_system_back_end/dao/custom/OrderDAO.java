package lk.ijse.gdse68.pos_system_back_end.dao.custom;

import lk.ijse.gdse68.pos_system_back_end.dao.CrudDAO;
import lk.ijse.gdse68.pos_system_back_end.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
    String getLastId(Connection connection) throws SQLException;
}
