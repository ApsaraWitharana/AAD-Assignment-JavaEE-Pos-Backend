package lk.ijse.gdse68.pos_system_back_end.bo.custom;

import lk.ijse.gdse68.pos_system_back_end.bo.SuperBO;
import lk.ijse.gdse68.pos_system_back_end.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException;

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

    CustomerDTO getCustomerById(Connection connection, String id);
}
