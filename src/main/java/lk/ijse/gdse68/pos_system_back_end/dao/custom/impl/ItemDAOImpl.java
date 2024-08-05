package lk.ijse.gdse68.pos_system_back_end.dao.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.dao.custom.ItemDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.util.CrudUtil;
import lk.ijse.gdse68.pos_system_back_end.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean save(Connection connection, Item entity) throws SQLException {
        String sql = "INSERT INTO item (code,name,price,qty) VALUES (?,?,?,?)";
        return CrudUtil.execute(connection, sql, entity.getCode(), entity.getName(), entity.getPrice(), entity.getQty());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException {
        String sql = "UPDATE item SET name = ?, price = ?, qty = ? WHERE code = ?";
        return CrudUtil.execute(connection, sql, entity.getName(), entity.getPrice(), entity.getQty(), entity.getCode());
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM item";
        ArrayList<Item> itemList = new ArrayList<Item>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4)

            );

            itemList.add(item);
        }
        return itemList;
    }


    @Override
    public boolean delete(Connection connection, String code) throws SQLException {
        String sql = "DELETE FROM item WHERE code = ?";
        return CrudUtil.execute(connection, sql, code);
    }

    @Override
    public Item findBy(Connection connection, String code) throws SQLException {
        String sql = "SELECT * FROM item WHERE code = ?";
        Item item = new Item();
        ResultSet rst = CrudUtil.execute(connection, sql, code);

        if (rst.next()) {
            item.setCode(rst.getString(1));
            item.setName(rst.getString(2));
            item.setPrice(rst.getBigDecimal(3));
            item.setQty(rst.getInt(4));
        }
        return item;
    }

//    @Override
//    public boolean reduceQty(Connection connection, Item item) throws SQLException {
//        String sql = "UPDATE item SET qty = ( qty - ? ) WHERE code = ?";
//        return CrudUtil.execute(connection, sql, item.getQty(), item.getCode());
//    }
@Override
public boolean reduceQty(Connection connection, Item item) throws SQLException {
    String sql = "UPDATE item SET qty = (qty - ?) WHERE code = ?";
    try (PreparedStatement pst = connection.prepareStatement(sql)) {
        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: qty = " + item.getQty() + ", code = " + item.getCode());
        pst.setInt(1, item.getQty());
        pst.setString(2, item.getCode());
        int result = pst.executeUpdate();
        System.out.println("Update result: " + result);
        return result > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Failed to reduce quantity for item: " + item.getCode(), e);
    }
}

    public boolean exists(Connection connection, String itemCode) throws SQLException {
        String query = "SELECT 1 FROM item WHERE code = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, itemCode);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}