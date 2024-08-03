package lk.ijse.gdse68.pos_system_back_end.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.ItemBO;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.ItemBOImpl;
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

@WebServlet(name = "item",urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    ItemBO itemBO = (ItemBO) new ItemBOImpl();

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


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getParameter("function");

        if (function != null) {
            if (function.equals("someValue")) {
                try (Connection connection = connectionPool.getConnection()) {
                    ArrayList<ItemDTO> itemDTOList = itemBO.getAllItems(connection);
                    System.out.println(itemDTOList);

                    Jsonb jsonb = JsonbBuilder.create();
                    String json = jsonb.toJson(itemDTOList);
                    resp.getWriter().write(json);

                } catch (JsonbException | IOException | SQLException e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
            } else if (function.equals("getById")) {
                String code = req.getParameter("code");

                try (Connection connection = connectionPool.getConnection()) {
                    ItemDTO itemDTO = itemBO.getItemByCode(connection, code);
                    System.out.println(itemDTO);

                    Jsonb jsonb = JsonbBuilder.create();
                    String json = jsonb.toJson(itemDTO);
                    resp.getWriter().write(json);
                } catch (JsonbException | IOException | SQLException e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid function parameter");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Function parameter is missing");
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()) {
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            System.out.println(itemDTO);

            if (itemDTO.getCode() == null || !itemDTO.getCode().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("Code is empty or invalid!");
                return;
            } else if (itemDTO.getName() == null || !itemDTO.getName().matches("^.{3,}$")) {
                resp.getWriter().write("Name is empty or invalid!");
                return;
            } else if (itemDTO.getPrice() == null || !itemDTO.getPrice().toString().matches("\\d+(\\.\\d{1,2})")) {
                resp.getWriter().write("Price is empty or invalid!");
                return;
            } else if (String.valueOf(itemDTO.getQty()) == null || !String.valueOf(itemDTO.getQty()).matches("^\\d+(\\.\\d{1,2})?$")) {
                resp.getWriter().write("Qty is empty or invalid");
                return;
            }

            boolean isSaved = itemBO.saveItem(connection, itemDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Save Item Successfully");

            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to add item");
            }
        }catch (SQLIntegrityConstraintViolationException e){
            resp.sendError(HttpServletResponse.SC_CONFLICT,"Duplicate values! Please check again");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = connectionPool.getConnection()){
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            System.out.println(itemDTO);

            if (itemDTO.getCode() == null || !itemDTO.getCode().matches("^(I00-)[0-9]{3}$")) {
                resp.getWriter().write("Code is empty or invalid!");
                return;
            } else if (itemDTO.getName() == null || !itemDTO.getName().matches("^.{3,}$")) {
                resp.getWriter().write("Name is empty or invalid!");
                return;
            } else if (itemDTO.getPrice() == null || !itemDTO.getPrice().toString().matches("\\d+(\\.\\d{1,2})")) {
                resp.getWriter().write("Price is empty or invalid!");
                return;
            } else if (String.valueOf(itemDTO.getQty()) == null || !String.valueOf(itemDTO.getQty()).matches("^\\d+(\\.\\d{1,2})?$")) {
                resp.getWriter().write("Qty is empty or invalid!");
                return;
            }

            boolean isUpdated = itemBO.updateItem(connection, itemDTO);
            if(isUpdated){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Update Item Successfully");
            }else{
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed to update item details!");
            }
        }catch (SQLIntegrityConstraintViolationException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate values!. Please check again");
        }catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request!!");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        try (Connection connection = connectionPool.getConnection()){
            boolean isDeleted = itemBO.deleteItem(connection,code);

            if (isDeleted){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("Item Delete Successfully");

            }else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to delete item!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
