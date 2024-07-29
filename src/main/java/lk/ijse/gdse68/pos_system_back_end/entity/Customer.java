package lk.ijse.gdse68.pos_system_back_end.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString
public class Customer {
    private String id;
    private String name;
    private String address;
    private String salary;
}
