package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.ItemDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDetailsDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.Item;
import lk.ijse.gdse68.pos_system_back_end.entity.Orders;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) new OrderDetailDAOImpl();
    ItemDAO itemDAO = (ItemDAO) new ItemDAOImpl();

    public String getLastId(Connection connection) throws SQLException {
        return orderDAO.getLastId(connection);
    }
//    @Override
//    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
//        try {
//            // Disable auto-commit to manage transactions manually
//            connection.setAutoCommit(false);
//
//            // Save the order
//            boolean isOrderSaved = orderDAO.save(connection, new Orders(dto.getOrder_id(), dto.getCust_id(), dto.getDate(), dto.getDiscount(), dto.getTotal()));
//            if (isOrderSaved) {
//                // Save the order details
//                boolean isOrderDetailsSaved = saveOrderDetails(connection, dto.getOrder_list());
//                if (isOrderDetailsSaved) {
//                    // Update item quantities
//                    boolean isItemQtyUpdated = updateItemQty(connection, dto.getOrder_list());
//                    if (isItemQtyUpdated) {
//                        // Commit the transaction if all operations succeeded
//                        connection.commit();
//                        return true;
//                    }
//                }
//            }
//
//            // If any operation failed, roll back the transaction
//            connection.rollback();
//            return false;
//        } catch (SQLException throwables) {
//            // Print the stack trace for debugging
//            throwables.printStackTrace();
//
//            // Roll back the transaction in case of an error
//            try {
//                connection.rollback();
//            } catch (SQLException rollbackException) {
//                rollbackException.printStackTrace();
//            }
//            return false;
//        } finally {
//            // Re-enable auto-commit before returning
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException autoCommitException) {
//                autoCommitException.printStackTrace();
//            }
//        }
//    }

    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // Verify if the customer exists
            if (!isCustomerExist(connection, dto.getCust_id())) {
                throw new SQLException("Customer ID does not exist: " + dto.getCust_id());
            }

            // Save the order
            boolean isOrderSaved = orderDAO.save(connection, new Orders(dto.getOrder_id(), dto.getCust_id(), dto.getDate(), dto.getDiscount(), dto.getTotal()));
            if (isOrderSaved) {
                // Save the order details
                boolean isOrderDetailsSaved = saveOrderDetails(connection, dto.getOrder_list());
                if (isOrderDetailsSaved) {
                    // Update item quantities
                    boolean isItemQtyUpdated = updateItemQty(connection, dto.getOrder_list());
                    if (isItemQtyUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }

    private boolean isCustomerExist(Connection connection, String cust_id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cust_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean updateItemQty(Connection connection, List<OrderDetailsDTO> order_list) throws SQLException {
        for (OrderDetailsDTO dto : order_list) {
            Item item = new Item(dto.getItem_code(), dto.getQty());
            if (!itemDAO.reduceQty(connection, item)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(Connection connection, List<OrderDetailsDTO> order_list) throws SQLException {
        for (OrderDetailsDTO dto : order_list) {
            OrderDetail orderDetail = new OrderDetail(dto.getOrder_id(), dto.getItem_code(), dto.getUnit_price(), dto.getQty());
            if (!orderDetailsDAO.save(connection, orderDetail)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public OrderDTO getOrderById(Connection connection, String id) throws SQLException {
        Orders orders = orderDAO.findBy(connection, id);

        return new OrderDTO(
                orders.getOrder_id(),
                orders.getDate(),
                orders.getCust_id(),
                orders.getDiscount(),
                orders.getTotal()
        );
    }
}
