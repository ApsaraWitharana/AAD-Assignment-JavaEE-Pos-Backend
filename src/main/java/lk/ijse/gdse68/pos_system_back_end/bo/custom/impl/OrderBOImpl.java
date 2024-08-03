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
import lk.ijse.gdse68.pos_system_back_end.entity.Order;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) new OrderDetailDAOImpl();
    ItemDAO itemDAO = (ItemDAO) new ItemDAOImpl();
    @Override
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
        try {
            connection.setAutoCommit(false);
            boolean isOrderSave = orderDAO.save(connection,new Order(dto.getOrder_id(),dto.getDate(),dto.getCust_id(),dto.getDiscount(),dto.getTotal()));
            if (isOrderSave){
                boolean isOrderDetailsSaved = saveOrderDetails(connection,dto.getOrder_list());
              if (isOrderDetailsSaved){
                  boolean isItemQtyUpdated = updateItemQty(connection,dto.getOrder_list());
                  if (isItemQtyUpdated){
                      connection.commit();
                      return true;
                  }
              }
            }
            return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
            return false;

        }
    }

    private boolean updateItemQty(Connection connection, List<OrderDetailsDTO> orderList) {
        for (OrderDetailsDTO dto:orderList){
            Item item = new Item(dto.getItem_code(),dto.getQty());
            if (!itemDAO.reduceQty(connection,item)){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(Connection connection, List<OrderDetailsDTO> orderList) throws SQLException {
        for (OrderDetailsDTO dto:orderList){
            OrderDetail orderDetail = new OrderDetail(dto.getOrder_id(),dto.getItem_code(),dto.getUnit_price(),dto.getQty());
            if (!orderDetailsDAO.save(connection,orderDetail)){
                return false;
            }
        }
        return true;
    }
}
