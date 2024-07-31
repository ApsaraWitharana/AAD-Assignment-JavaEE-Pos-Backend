package lk.ijse.gdse68.pos_system_back_end.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@ToString
public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private Double salary;



}
