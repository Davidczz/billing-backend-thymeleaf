package be.gold.agency.backendbill.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {
    private Long id;

    private String firstName;

    private String lastName;

    private String companyName;

    private String street;

    private String streetNumber;

    private String postalCode;

    private String city;

    private String country;

    private String vatNumber;

    private String email;

    private String phone;
}
