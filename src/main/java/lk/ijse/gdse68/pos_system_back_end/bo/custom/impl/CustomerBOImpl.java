package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.CustomerBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.CustomerDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.CustomerDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.CustomerDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) new CustomerDAOImpl();
    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return customerDAO.save(connection,new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getSalary()));
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<Customer> customersList = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();

        for (Customer customer : customersList){
            CustomerDTO dto = new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
            customerDTOList.add(dto);
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO getCustomerById(Connection connection, String id) {
        return null;
    }
}
