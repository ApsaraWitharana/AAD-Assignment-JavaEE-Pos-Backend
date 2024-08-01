package lk.ijse.gdse68.pos_system_back_end.dao.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.dao.custom.CustomerDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.util.CrudUtil;
import lk.ijse.gdse68.pos_system_back_end.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException {
        String sql = "INSERT INTO customer (id,name,address,salary) VALUES (?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException {
        String sql = "UPDATE customer SET name = ?, address = ?,salary = ? WHERE id = ?";
        return CrudUtil.execute(connection,sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM customer";
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while(rst.next()){
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            );

            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(connection,sql,id);
    }

    @Override
    public Customer findBy(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE id=?";
        Customer customer =new Customer();
        ResultSet rst = CrudUtil.execute(connection,sql,id);

        if (rst.next()){
            customer.setId(rst.getString(1));
            customer.setName(rst.getString(2));
            customer.setAddress(rst.getString(3));
            customer.setSalary(Double.valueOf(rst.getString(4)));
        }
        return customer;
    }
}
