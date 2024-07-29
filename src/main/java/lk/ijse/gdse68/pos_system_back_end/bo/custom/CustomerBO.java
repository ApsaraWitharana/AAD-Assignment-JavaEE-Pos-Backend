package lk.ijse.gdse68.pos_system_back_end.bo.custom;

import lk.ijse.gdse68.pos_system_back_end.dto.CustomerDTO;

import java.sql.Connection;
import java.util.ArrayList;

public interface CustomerBO {
    boolean saveCustomer(Connection connection, CustomerDTO customerDTO);

    ArrayList<CustomerDTO> getAllCustomers(Connection connection);

    CustomerDTO getCustomerById(Connection connection, String id);
}
