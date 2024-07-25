package lk.ijse.gdse68.pos_system_back_end.dao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class CrudUtil {
    public static <T> T execute (Connection connection, String sql, Object... args) throws SQLException{
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i=0; i<args.length; i++){
            pstm.setObject((i+1),args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")){
            return (T) pstm.executeQuery(); // result set ek
        }

        return (T) (Boolean) (pstm.executeUpdate() > 0); //premitive =>>> boolean value
    }
}
