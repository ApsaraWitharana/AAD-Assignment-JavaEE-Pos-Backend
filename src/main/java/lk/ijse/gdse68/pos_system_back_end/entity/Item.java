package lk.ijse.gdse68.pos_system_back_end.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class Item {
    private String code;
    private String name;
    private BigDecimal price;
    private int qty;

}
