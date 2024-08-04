package lk.ijse.gdse68.pos_system_back_end.dao.custom;

import lk.ijse.gdse68.pos_system_back_end.dao.CrudDAO;
import lk.ijse.gdse68.pos_system_back_end.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {

    boolean reduceQty(Connection connection, Item item) throws SQLException;
}
