package lk.ijse.gdse68.pos_system_back_end.entity;

import jakarta.servlet.annotation.WebServlet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@WebServlet
@AllArgsConstructor
@Data
public class Custom {

    private String order_id;
    private LocalDate date;
    private String cust_id;
    private BigDecimal discount;
    private BigDecimal total;
    private String item_code;
    private BigDecimal unit_price;
    private int qty;
}
