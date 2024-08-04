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

    public OrderDetail(String string, BigDecimal bigDecimal, int anInt) {
    }
}
