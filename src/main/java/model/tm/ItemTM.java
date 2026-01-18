package model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ItemTM {
    private String code;
    private String description;
    private String packageSize;
    private Double unitPrice;
    private Integer qtyOnHand;

}
