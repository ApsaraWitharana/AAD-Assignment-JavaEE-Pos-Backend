package lk.ijse.gdse68.pos_system_back_end.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{

    public boolean save(Connection connection, T entity) throws SQLException;

    public  boolean update (Connection connection, T entity) throws SQLException;

    public ArrayList<T> getAll (Connection connection) throws SQLException;

    public boolean delete (Connection connection, String id) throws SQLException;

    public T findBy (Connection connection,String id) throws SQLException;
}
