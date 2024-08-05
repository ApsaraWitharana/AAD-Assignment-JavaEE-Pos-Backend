package lk.ijse.gdse68.pos_system_back_end.dao.custom;

import lk.ijse.gdse68.pos_system_back_end.dao.CrudDAO;
import lk.ijse.gdse68.pos_system_back_end.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    boolean exists(Connection connection, String custId) throws SQLException;
}
