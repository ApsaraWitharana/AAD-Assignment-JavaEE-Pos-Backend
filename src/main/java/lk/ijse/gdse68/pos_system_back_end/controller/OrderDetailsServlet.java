package lk.ijse.gdse68.pos_system_back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.pos_system_back_end.bo.BOFactory;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.OrderDetailsBO;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.OrderDetailsBOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDTO;
import lk.ijse.gdse68.pos_system_back_end.dto.OrderDetailsDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.OrderDetail;
import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orderDetails")
public class OrderDetailsServlet extends HttpServlet {

    OrderDetailsBO orderDetailsBO = BOFactory.getBoFactory().getBO(BOFactory.BoTypes.ORDER_DETAIL_BO);
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


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String function = req.getParameter("function");
//
//        if(function.equals("getById")){
//            String id = req.getParameter("id");
//            try (Connection connection = connectionPool.getConnection()){
//                OrderDTO orderDTO = orderDetailsBO.getOrderDetailsById(connection, id);
//                System.out.println(orderDTO);
//                Jsonb jsonb = JsonbBuilder.create();
//                String json = jsonb.toJson(orderDTO);
//                resp.getWriter().write(json);
//            } catch (JsonbException e) {
//                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//            } catch (IOException e) {
//                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//            }
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");
        String orderId = req.getParameter("order_id");

        if ("getById".equals(function) && orderId != null) {
            try (Connection connection = connectionPool.getConnection()) {
                OrderDTO orderDTO = orderDetailsBO.getOrderDetailsById(connection, orderId);
                System.out.println(orderDTO);
                if (orderDTO != null) {
                    Jsonb jsonb = JsonbBuilder.create();
                    String json = jsonb.toJson(orderDTO);
                    resp.setContentType("application/json");
                    resp.getWriter().write(json);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
        }
    }



}
