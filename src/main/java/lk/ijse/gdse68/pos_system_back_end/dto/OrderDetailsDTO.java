package lk.ijse.gdse68.pos_system_back_end.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class OrderDetailsDTO {

    String order_id;
    String item_code;
    BigDecimal unit_price;
    int qty;

    public OrderDetailsDTO(String item_code, BigDecimal unit_price, int qty) {
        this.item_code = item_code;
        this.unit_price = unit_price;
        this.qty = qty;
    }
}
