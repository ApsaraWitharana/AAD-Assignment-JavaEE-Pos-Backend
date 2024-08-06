package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;


import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderDetailsBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDetailsDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.Orders;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) new OrderDetailDAOImpl();

//    @Override
//    public OrderDTO getOrderDetailsById(Connection connection, String id) throws SQLException {
//        System.out.println("Fetching order details for ID: " + id);
//
//        OrderDTO orderDTO = new OrderDTO();
//        if (orderDTO == null) {
//            System.out.println("No order found for ID: " + id);
//            return null;
//        }
//        System.out.println(orderDTO);
//        Orders order = orderDAO.findBy(connection, id);
//        orderDTO.setDate(order.getDate());
//        orderDTO.setCust_id(order.getCust_id());
//        orderDTO.setDiscount(order.getDiscount());
//        orderDTO.setTotal(order.getTotal());
//
//        List<OrderDetail> orderDetailList = orderDetailsDAO.getAllById(connection, id);
//
//        List<OrderDetailsDTO> orderDetailDTOList = new ArrayList<OrderDetailsDTO>();
//        for(OrderDetail orderDetail : orderDetailList){
//            orderDetailDTOList.add(new OrderDetailsDTO(
//                    orderDetail.getItem_code(),
//                    orderDetail.getUnit_price(),
//                    orderDetail.getQty()
//            ));
//        }
//        orderDTO.setOrder_list(orderDetailDTOList);
//
//        return orderDTO;
//    }

    @Override
    public OrderDTO getOrderDetailsById(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate date = rs.getDate("date").toLocalDate();
                    BigDecimal discount = rs.getBigDecimal("discount");
                    BigDecimal total = rs.getBigDecimal("total");

                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setOrder_id(rs.getString("order_id"));
                    orderDTO.setCust_id(rs.getString("cust_id"));
                    orderDTO.setDate(date);
                    orderDTO.setDiscount(discount);
                    orderDTO.setTotal(total);

                    List<OrderDetail> orderDetailList = orderDetailsDAO.getAllById(connection, id);
                    List<OrderDetailsDTO> orderDetailDTOList = new ArrayList<>();
                    for (OrderDetail orderDetail : orderDetailList) {
                        orderDetailDTOList.add(new OrderDetailsDTO(
                                orderDetail.getItem_code(),
                                orderDetail.getUnit_price(),
                                orderDetail.getQty()
                        ));

                    }
                    orderDTO.setOrder_list(orderDetailDTOList);

                    return orderDTO;
                } else {
                    return null; // Return null if no order is found
                }
            }
        }
    }


//    @Override
//    public OrderDTO getOrderDetailsById(Connection connection, String id) throws SQLException {
//        System.out.println("Fetching order details for ID: " + id);
//
//        Orders order = orderDAO.findBy(connection, id);
//        if (order == null) {
//            System.out.println("No order found for ID: " + id);
//            return null;
//        }
//
//        OrderDTO orderDTO = new OrderDTO();
//        System.out.println("Order from DAO: " + order);
//
//        orderDTO.setDate(order.getDate());
//        orderDTO.setCust_id(order.getCust_id());
//        orderDTO.setDiscount(order.getDiscount());
//        orderDTO.setTotal(order.getTotal());
//
//        List<OrderDetail> orderDetailList = orderDetailsDAO.getAllById(connection, id);
//        System.out.println("Order details list: " + orderDetailList);
//
//        List<OrderDetailsDTO> orderDetailDTOList = new ArrayList<>();
//        for (OrderDetail orderDetail : orderDetailList) {
//            OrderDetailsDTO dto = new OrderDetailsDTO(
//                    orderDetail.getItem_code(),
//                    orderDetail.getUnit_price(),
//                    orderDetail.getQty()
//            );
//            orderDetailDTOList.add(dto);
//            System.out.println("Added detail to DTO list: " + dto);
//        }
//        orderDTO.setOrder_list(orderDetailDTOList);
//
//        System.out.println("Final OrderDTO: " + orderDTO);
//        return orderDTO;
//    }

}
