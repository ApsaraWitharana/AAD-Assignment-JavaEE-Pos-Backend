package lk.ijse.gdse68.pos_system_back_end.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "customer",urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            var ctx = new InitialContext(); //get connection to connection pool
            DataSource pool = (DataSource) ctx.lookup("java/comp/env/jdbc/pos_system"); // cast and create datasource and get lookup set url path
            this.connection = pool.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
