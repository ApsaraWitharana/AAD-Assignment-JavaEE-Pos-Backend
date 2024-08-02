package lk.ijse.gdse68.pos_system_back_end.bo.custom.impl;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.ItemBO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.ItemDAO;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dto.ItemDTO;
import lk.ijse.gdse68.pos_system_back_end.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) new ItemDAOImpl();
    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException {
        return itemDAO.save(connection,new Item(dto.getCode(),dto.getName(),dto.getPrice(),dto.getQty()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO itemDTO) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public ItemDTO getItemByCode(Connection connection, String id) throws SQLException {
        return null;
    }

    @Override
    public boolean removeItem(Connection connection, String id) throws SQLException {
        return false;
    }
}
