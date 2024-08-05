package lk.ijse.gdse68.pos_system_back_end.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class OrderDTO {

    String order_id;
    LocalDate date;
    String cust_id;
    BigDecimal discount;
    BigDecimal total;
    List<OrderDetailsDTO> order_list;

    public OrderDTO(String order_id, LocalDate date, String cust_id, BigDecimal discount, BigDecimal total) {
        this.order_id = order_id;
        this.date = date;
        this.cust_id = cust_id;
        this.discount = discount;
        this.total = total;
    }


    public OrderDTO(String orderId, LocalDate date, String custId) {
    }
}
