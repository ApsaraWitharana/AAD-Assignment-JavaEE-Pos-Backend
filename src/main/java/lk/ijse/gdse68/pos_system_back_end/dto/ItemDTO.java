package lk.ijse.gdse68.pos_system_back_end.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class ItemDTO {
    private String code;
    private String name;
    private BigDecimal price;
    private int qty;
}
