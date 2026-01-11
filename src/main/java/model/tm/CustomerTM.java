package model.tm;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTM {
    private Integer id;
    private String title;
    private String name;
    private String address;
    private Double salary;
    private Date dob;
    private String city;
    private String province;
    private String postalCode;
}
