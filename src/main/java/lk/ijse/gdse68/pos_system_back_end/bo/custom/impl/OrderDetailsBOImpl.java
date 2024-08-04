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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) new OrderDetailDAOImpl();

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
