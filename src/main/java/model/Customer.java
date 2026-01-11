package model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {
    private Integer id;
    private String title;
    private String name;
    private String address;
    private Double salary;
    private LocalDate dob;
    private String city;
    private String province;
    private String postalCode;
}
