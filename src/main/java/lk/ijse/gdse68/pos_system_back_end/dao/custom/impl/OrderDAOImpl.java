package lk.ijse.gdse68.pos_system_back_end.dao.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.util.CrudUtil;
import lk.ijse.gdse68.pos_system_back_end.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean save(Connection connection, Order entity) throws SQLException {
        String sql = "INSERT INTO orders (order_id,date,cust_id,discount,total) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getOrder_id(),entity.getDate(),entity.getCust_id(),entity.getDiscount(),entity.getTotal());
    }

    @Override
    public boolean update(Connection connection, Order entity) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException {
        return false;
    }

    @Override
    public Order findBy(Connection connection, String id) throws SQLException {
        return null;
    }
}
