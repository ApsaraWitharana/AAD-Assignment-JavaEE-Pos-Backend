package lk.ijse.gdse68.pos_system_back_end.dao;

import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDAOImpl;
import lk.ijse.gdse68.pos_system_back_end.dao.custom.impl.OrderDetailDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null) ? daoFactory = new DAOFactory() : daoFactory;

    }

    public enum DAOTypes{
        CUSTOMER_DAO, ITEM_DAO, ORDER_DAO, ORDER_DETAILS_DAO
    }

    public <T extends SuperDAO> T getDAO (DAOTypes types){
        switch (types){
            case CUSTOMER_DAO:
                return (T) new CustomerDAOImpl();
            case ITEM_DAO:
                return (T) new ItemDAOImpl();
            case ORDER_DAO:
                return (T) new OrderDAOImpl();
            case ORDER_DETAILS_DAO:
                return (T) new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
