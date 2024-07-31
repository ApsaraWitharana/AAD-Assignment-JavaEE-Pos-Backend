package lk.ijse.gdse68.pos_system_back_end.dao.custom;

import lk.ijse.gdse68.pos_system_back_end.dao.util.CrudUtil;
import lk.ijse.gdse68.pos_system_back_end.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO{
    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql = "INSERT INTO customer (id,name,address,salary) VALUES (?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        return false;
    }

    @Override
    public Customer findBy(Connection connection, String id) throws SQLException {
        return null;
    }
}
