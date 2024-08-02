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

        if (function.equals("getAll")){
            try (Connection connection = connectionPool.getConnection()){
                ArrayList<ItemDTO> customerDTOList = itemBO.getAllItems(connection);

                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(customerDTOList);
                resp.getWriter().write(json);

            }catch (JsonbException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }catch (IOException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }catch (SQLException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }

        }else if (function.equals("getById")){
            String id = req.getParameter("id");

            try (Connection connection = connectionPool.getConnection()){
                ItemDTO itemDTO = itemBO.getItemByCode(connection,id);

                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(itemDTO);
                resp.getWriter().write(json);
            }catch (JsonbException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }catch (IOException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }catch (SQLException e){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
            }
        }
    }

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
                resp.getWriter().write("price is empty or invalid!");
                return;
            } else if (String.valueOf(itemDTO.getQty()) == null || !String.valueOf(itemDTO.getQty()).matches("^\\d+(\\.\\d{1,2})?$")) {
                resp.getWriter().write("Qty is empty or invalid");
                return;
            }

            boolean isSaved = itemBO.saveItem(connection, itemDTO);
            if (isSaved) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
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
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
