package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.CustomerDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.ItemDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.CustomerDAOImpl;
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

    CustomerDAO customerDAO = (CustomerDAO) new CustomerDAOImpl();
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


//public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
//    try {
//        connection.setAutoCommit(false);
//
//        // Check if customer exists
//        boolean customerExists = customerDAO.exists(connection, dto.getCust_id());
//        if (!customerExists) {
//            throw new SQLException("Customer ID does not exist: " + dto.getCust_id());
//
//        }
//
//        // Save order
//        boolean isOrderSave = orderDAO.save(connection, new Orders(dto.getOrder_id(), dto.getCust_id(), dto.getDate(), dto.getDiscount(), dto.getTotal()));
//        if (isOrderSave) {
//            // Save order details
//            boolean isOrderDetailsSaved = saveOrderDetails(connection, dto.getOrder_list());
//            if (isOrderDetailsSaved) {
//                // Update item quantity
//                boolean isItemQtyUpdated = updateItemQty(connection, dto.getOrder_list());
//                if (isItemQtyUpdated) {
//                    connection.commit();
//                    return true;
//                }
//            }
//        }
//        return false;
//    } catch (SQLException throwables) {
//        throwables.printStackTrace();
//        connection.rollback();
//        return false;
//    }
//}
//

    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // Check if customer exists
            boolean customerExists = customerDAO.exists(connection, dto.getCust_id());
            if (!customerExists) {
                throw new SQLException("Customer ID does not exist: " + dto.getCust_id());
            }

            // Save order
            boolean isOrderSave = orderDAO.save(connection, new Orders(dto.getOrder_id(), dto.getCust_id(), dto.getDate(), dto.getDiscount(), dto.getTotal()));
            if (!isOrderSave) {
                throw new SQLException("Failed to save order");
            }

            // Save order details and update item quantities
            for (OrderDetailsDTO details : dto.getOrder_list()) {
                // Check if item exists and has sufficient quantity
                boolean itemExists = itemDAO.exists(connection, details.getItem_code());
                if (!itemExists) {
                    throw new SQLException("Item code does not exist: " + details.getItem_code());
                }

                Item item = itemDAO.findBy(connection, details.getItem_code());
                if (item.getQty() < details.getQty()) {
                    throw new SQLException("Insufficient quantity for item: " + details.getItem_code());
                }

                // Save order detail
                boolean isOrderDetailSaved = orderDetailsDAO.save(connection, new OrderDetail(details.getOrder_id(), details.getItem_code(), details.getUnit_price(), details.getQty()));
                if (!isOrderDetailSaved) {
                    throw new SQLException("Failed to save order detail for item: " + details.getItem_code());
                }

                // Reduce item quantity
                boolean isQtyReduced = itemDAO.reduceQty(connection, new Item(details.getItem_code(), details.getQty()));
                if (!isQtyReduced) {
                    throw new SQLException("Failed to reduce quantity for item: " + details.getItem_code());
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }

    private boolean updateItemQty(Connection connection, List<OrderDetailsDTO> order_list) throws SQLException {
        for (OrderDetailsDTO dto : order_list) {
            Item item = new Item(dto.getItem_code(), dto.getQty());
            System.out.println("updateItemQty::"+ item);
            if (!itemDAO.reduceQty(connection, item)) {
                return false;
            }
        }
        return true;
    }

//    private boolean saveOrderDetails(Connection connection, List<OrderDetailsDTO> order_list) throws SQLException {
//        for (OrderDetailsDTO dto : order_list) {
//            OrderDetail orderDetail = new OrderDetail(dto.getOrder_id(), dto.getItem_code(), dto.getUnit_price(), dto.getQty());
//            if (!orderDetailsDAO.save(connection, orderDetail)) {
//                return false;
//            }
//        }
//        return true;
//    }
public boolean saveOrderDetails(Connection connection, List<OrderDetailsDTO> orderList) throws SQLException {
    for (OrderDetailsDTO details : orderList) {
        System.out.println("saveOrderDetails orderList:: "+orderList);

        // Check if item exists
        boolean itemExists = itemDAO.exists(connection, details.getItem_code());
        if (!itemExists) {
            throw new SQLException("Item code does not exist: " + details.getItem_code());

        }
        // Save order detail
        boolean isSaved = orderDetailsDAO.save(connection, new OrderDetail(details.getOrder_id(), details.getItem_code(), details.getUnit_price(), details.getQty()));
        if (!isSaved) {
            return false;
        }
    }
    return true;
}

    @Override
    public OrderDTO getOrderById(Connection connection, String id) throws SQLException {
        Orders orders = orderDAO.findBy(connection, id);
        System.out.println("getOrderById ::"+orders);
        return new OrderDTO(
                orders.getOrder_id(),
                orders.getDate(),
                orders.getCust_id(),
                orders.getDiscount(),
                orders.getTotal()
        );
    }
}
