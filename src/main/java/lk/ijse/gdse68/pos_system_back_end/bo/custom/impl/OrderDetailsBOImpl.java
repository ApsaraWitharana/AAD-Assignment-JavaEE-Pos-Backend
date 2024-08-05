package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderDetailsBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDetailsDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.Order;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) new OrderDetailDAOImpl();

//    public boolean placeOrder(Connection connection, OrderDTO orderDTO) {
//        try {
//            // Begin transaction
//            connection.setAutoCommit(false);
//
//            // Insert order into the orders table
//            String orderSQL = "INSERT INTO orders (order_id, date, cust_id, discount, total) VALUES (?, ?, ?, ?, ?)";
//            try (PreparedStatement orderStmt = connection.prepareStatement(orderSQL)) {
//                orderStmt.setString(1, orderDTO.getOrder_id());
//                orderStmt.setDate(2, Date.valueOf(orderDTO.getDate()));
//                orderStmt.setString(3, orderDTO.getCust_id());
//                orderStmt.setBigDecimal(4, orderDTO.getDiscount());
//                orderStmt.setBigDecimal(5, orderDTO.getTotal());
//                orderStmt.executeUpdate();
//            }
//
//            // Insert order details into the order_details table
//            String detailSQL = "INSERT INTO order_details (order_id, item_code, quantity, unit_price) VALUES (?, ?, ?, ?)";
//            try (PreparedStatement detailStmt = connection.prepareStatement(detailSQL)) {
//                for (OrderDetailsDTO order : orderDTO.getOrder_list()) {
//                    String itemCode = order.getItem_code();
//
//                    // Validate item_code
//                    if (!itemExists(connection, itemCode)) {
//                        throw new SQLException("Item code " + itemCode + " does not exist in the item table.");
//                    }
//
//                    detailStmt.setString(1, orderDTO.getOrder_id());
//                    detailStmt.setString(2, itemCode);
//                    detailStmt.setInt(3, order.getQty());
//                    detailStmt.setBigDecimal(4, order.getUnit_price());
//                    detailStmt.addBatch();
//                }
//                detailStmt.executeBatch();
//            }
//
//            // Commit transaction
//            connection.commit();
//            return true;
//
//        } catch (SQLException e) {
//            try {
//                // Rollback transaction if there is an error
//                connection.rollback();
//            } catch (SQLException rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                // Restore auto-commit mode
//                connection.setAutoCommit(true);
//            } catch (SQLException autoCommitEx) {
//                autoCommitEx.printStackTrace();
//            }
//        }
//    }
//
//    private boolean itemExists(Connection connection, String itemCode) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM item WHERE code = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, itemCode);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1) > 0;
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public OrderDTO getOrderDetailsById(Connection connection, String id) throws SQLException {

        OrderDTO orderDTO = new OrderDTO();
        Order order = orderDAO.findBy(connection,id);
        orderDTO.setDate(order.getDate());
        orderDTO.setCust_id(order.getCust_id());
        orderDTO.setDiscount(order.getDiscount());
        orderDTO.setTotal(order.getTotal());

        List<OrderDetail> orderDetailList = orderDetailsDAO.getAllById(connection,id);
        List<OrderDetailsDTO> orderDetailDTOList = new ArrayList<OrderDetailsDTO>();
        for (OrderDetail orderDetail:orderDetailList){
            orderDetailDTOList.add(new OrderDetailsDTO(
                    orderDetail.getItem_code(),
                    orderDetail.getUnit_price(),
                    orderDetail.getQty()
            ));
        }
        orderDTO.setOrder_list(orderDetailDTOList);
        return orderDTO;
    }
}
