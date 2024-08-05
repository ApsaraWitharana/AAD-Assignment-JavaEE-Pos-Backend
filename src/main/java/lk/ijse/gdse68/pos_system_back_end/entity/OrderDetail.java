package lk.ijse.gdse68.pos_system_back_end.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetail {

    private String order_id;
    private String item_code;
    private BigDecimal unit_price;
    private int qty;

    public OrderDetail(String item_code, BigDecimal unit_price, int qty) {
        this.item_code = item_code;
        this.unit_price = unit_price;
        this.qty = qty;
    }
}
