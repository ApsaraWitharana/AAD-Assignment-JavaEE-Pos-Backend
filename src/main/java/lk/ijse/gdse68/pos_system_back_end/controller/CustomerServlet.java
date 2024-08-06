package lk.ijse.gdse68.pos_system_back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.CustomerBO;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.CustomerDTO;
import lk.ijse.gdse68.pos_system_back_end.dto.ItemDTO;
import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

@WebServlet(name = "customer",urlPatterns = "/customer",loadOnStartup = 3)
public class CustomerServlet extends HttpServlet {

    CustomerBO customerBO = (CustomerBO) new CustomerBOImpl();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            System.out.println(customerDTO);

            if (customerDTO.getId() == null || !customerDTO.getId().matches("^(C00-)[0-9]{3}$")) {
                resp.getWriter().write("Id is empty or invalid!!");
                return;
            } else if (customerDTO.getName() == null || !customerDTO.getName().matches("^[A-Za-z ]{4,}$")) {
                resp.getWriter().write("Name is empty or invalid!!");
                return;
            } else if (customerDTO.getAddress() == null || !customerDTO.getAddress().matches("^[A-Za-z0-9., -]{5,}$")) {
                resp.getWriter().write("Address is empty or invalid!!");
                return;
            } else if (customerDTO.getSalary() <= 0) {
                resp.getWriter().write("Salary is empty or invalid!!");
                return;
            }

            boolean isSaved = customerBO.saveCustomer(connection, customerDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Customer Save Successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to save customer");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

//    @SneakyThrows
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String function = req.getParameter("function");
//
//        if (function != null) {
//            if (function.equals("someValue")) {
//                try (Connection connection = connectionPool.getConnection()) {
//                    ArrayList<CustomerDTO> customerDTOList = customerBO.getAllCustomers(connection);
//                    System.out.println(customerDTOList);
//
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String json = jsonb.toJson(customerDTOList);
//                    resp.getWriter().write(json);
//
//                } catch (JsonbException | IOException | SQLException e) {
//                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//                }
//            } else if (function.equals("getById")) {
//                String id = req.getParameter("id");
//
//                try (Connection connection = connectionPool.getConnection()) {
//                    CustomerDTO customerDTO = customerBO.getCustomerById(connection, id);
//                    System.out.println(customerDTO);
//
//                    Jsonb jsonb = JsonbBuilder.create();
//                    String json = jsonb.toJson(customerDTO);
//                    resp.getWriter().write(json);
//                } catch (JsonbException | IOException | SQLException e) {
//                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
//                }
//            } else {
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid function parameter");
//            }
//        } else {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Function parameter is missing");
//        }
//    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Log all request parameters
        req.getParameterMap().forEach((key, value) -> {
            System.out.println("Parameter: " + key + " = " + String.join(", ", value));
        });

        String function = req.getParameter("function");
        System.out.println("Function parameter: " + function);

        if (function != null) {
            if (function.equals("someValue")) {
                handleGetAllCustomers(req, resp);
            } else if (function.equals("getById")) {
                handleGetCustomerById(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid function parameter");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Function parameter is missing");
        }
    }

    private void handleGetAllCustomers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = connectionPool.getConnection()) {
            ArrayList<CustomerDTO> customerDTOList = customerBO.getAllCustomers(connection);
            Jsonb jsonb = JsonbBuilder.create();
            String json = jsonb.toJson(customerDTOList);
            resp.getWriter().write(json);
        } catch (JsonbException | IOException | SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void handleGetCustomerById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is missing");
            return;
        }

        try (Connection connection = connectionPool.getConnection()) {
            CustomerDTO customerDTO = customerBO.getCustomerById(connection, id);
            Jsonb jsonb = JsonbBuilder.create();
            String json = jsonb.toJson(customerDTO);
            resp.getWriter().write(json);
        } catch (JsonbException | IOException | SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try (Connection connection = connectionPool.getConnection()){
            boolean isDeleted = customerBO.deleteCustomer(connection,id);
            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Customer Delete Successfully");
            }else{
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete customer!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            System.out.println(customerDTO);

            if (customerDTO.getId() == null || !customerDTO.getId().matches("^(C00-)[0-9]{3}$")) {
                resp.getWriter().write("id is empty or invalid!");
                return;
            } else if (customerDTO.getName() == null || !customerDTO.getName().matches("^[A-Za-z ]{4,}$")) {
                resp.getWriter().write("Name is empty or invalid! ");
                return;
            } else if (customerDTO.getAddress() == null || !customerDTO.getAddress().matches("^[A-Za-z0-9., -]{8,}$")) {
                resp.getWriter().write("Address is empty or invalid");
                return;
            } else if (customerDTO.getSalary() <= 0) {
                resp.getWriter().write("Salary is empty or invalid!!");
                return;

            }
            boolean isUpdated = customerBO.updateCustomer(connection, customerDTO);
            if (isUpdated) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Customer Update Successfully");

            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to update customer");
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values. Please check again");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }
}
