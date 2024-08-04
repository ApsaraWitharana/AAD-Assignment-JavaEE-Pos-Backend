package lk.ijse.gdse68.pos_system_back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderBO;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "orders" , urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    OrderBO orderBO = (OrderBO) new OrderBOImpl();

    DataSource connectionPool;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext(); //get connection to connection pool
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/pos_system_new");
//            DataSource pool = (DataSource) ctx.lookup("java/comp/env/jdbc/pos_system"); // cast and create datasource and get lookup set url path
            this.connectionPool = dataSource;
        } catch (NamingException e) {
            throw new ServletException("Cannot find JNDI resource", e);
//            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");

        if (function.equals("getLastId")) {
            try (Connection connection = connectionPool.getConnection()) {
                String lastId = orderBO.getLastId(connection);
                resp.getWriter().write(lastId);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else if (function.equals("getById")) {
            String id = req.getParameter("id");
            try (Connection connection = connectionPool.getConnection()) {
                OrderDTO orderDTO = orderBO.getOrderById(connection, id);
                System.out.println(orderDTO);
                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(orderDTO);
                resp.getWriter().write(json);

            } catch (JsonbException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (SQLException e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }



//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try (Connection connection = connectionPool.getConnection()){
//
//            Jsonb jsonb = JsonbBuilder.create();
//            OrderDTO orderDTO = jsonb.fromJson(req.getReader(),OrderDTO.class);
//            System.out.println(orderDTO);
//
//            if(orderDTO.getOrder_id()==null || !orderDTO.getOrder_id().matches("^(ORD-)[0-9]{3}$")){
//                resp.getWriter().write("Order id is empty or invalid!");
//                return;
//            }else if(orderDTO.getDate()==null || !orderDTO.getDate().toString().matches("\\d{4}-\\d{2}-\\d{2}")){
//                resp.getWriter().write("Date is empty or invalid!");
//                return;
//            }else if(orderDTO.getCust_id()==null || !orderDTO.getCust_id().matches("^(C00-)[0-9]{3}$")){
//                resp.getWriter().write("Customer id is empty or invalid!");
//                return;
//            }else if(orderDTO.getDiscount()==null || !orderDTO.getDiscount().toString().matches("\\d+(\\.\\d+)?")){
//                resp.getWriter().write("Discount is empty or invalid!");
//                return;
//            }else if(orderDTO.getTotal()==null || !orderDTO.getTotal().toString().matches("\\d+(\\.\\d+)?")){
//                resp.getWriter().write("Total is empty or invalid!");
//                return;
//            }else if(orderDTO.getOrder_list().size()==0){
//                resp.getWriter().write("Order details list is empty!");
//                return;
//            }
//
//            boolean isOrder = orderBO.placeOrder(connection,orderDTO);
//            if (isOrder){
//                resp.setStatus(HttpServletResponse.SC_CREATED);
//                resp.getWriter().write("Save Order Successfully");
//
//            }else {
//                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to add Order");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {

            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            System.out.println(orderDTO);

            if (orderDTO.getOrder_id() == null || !orderDTO.getOrder_id().matches("^(ORD-)[0-9]{3}$")) {
                resp.getWriter().write("Order id is empty or invalid!");
                return;
            } else if (orderDTO.getDate() == null || !orderDTO.getDate().toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
                resp.getWriter().write("Date is empty or invalid!");
                return;
            } else if (orderDTO.getCust_id() == null || !orderDTO.getCust_id().matches("^(C00-)[0-9]{3}$")) {
                resp.getWriter().write("Customer id is empty or invalid!");
                return;
            } else if (orderDTO.getDiscount() == null || !orderDTO.getDiscount().toString().matches("\\d+(\\.\\d+)?")) {
                resp.getWriter().write("Discount is empty or invalid!");
                return;
            } else if (orderDTO.getTotal() == null || !orderDTO.getTotal().toString().matches("\\d+(\\.\\d+)?")) {
                resp.getWriter().write("Total is empty or invalid!");
                return;
            } else if (orderDTO.getOrder_list() == null || orderDTO.getOrder_list().isEmpty()) {
                resp.getWriter().write("Order details list is empty!");
                return;
            }

            boolean isOrder = orderBO.placeOrder(connection, orderDTO);
            if (isOrder) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Save Order Successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add Order");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
