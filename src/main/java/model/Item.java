package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Item {
    private String code;
    private String description;
    private String packageSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}
