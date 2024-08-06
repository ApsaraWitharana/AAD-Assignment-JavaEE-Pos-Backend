package lk.ijse.gdse68.pos_system_back_end.bo;

import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse68.pos_system_back_end.bo.custom.impl.OrderDetailsBOImpl;

public class BOFactory {

    private static  BOFactory boFactory;
    private BOFactory() {
    }

    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory():boFactory;
    }

    public enum BoTypes{
        CUSTOMER_BO,ITEM_BO,ORDER_BO,ORDER_DETAIL_BO
    }

    public <T extends  SuperBO> T getBO(BoTypes types){
        switch (types){
            case CUSTOMER_BO :
                return (T) new CustomerBOImpl();
            case ITEM_BO:
                return (T) new ItemBOImpl();
            case ORDER_BO:
                return (T) new OrderBOImpl();
            case ORDER_DETAIL_BO:
                return (T) new OrderDetailsBOImpl();

            default:
                return null;
        }
    }
}
