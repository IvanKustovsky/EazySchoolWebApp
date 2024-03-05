package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int addressId;
    @NotBlank(message = "Address1 must be not blank")
    @Size(min = 5, message = "Address1 must be at least 5 symbols long")
    private String address1;

    private String address2;

    @NotBlank(message = "City must be not blank")
    @Size(min = 3, message = "City must be at least 3 symbols long")
    private String city;

    @NotBlank(message = "State must be not blank")
    @Size(min = 5, message = "State must be at least 5 symbols long")
    private String state;

    @NotBlank(message = "Zip Code must be not blank")
    @Pattern(regexp = "(^$|[0-9]{5})", message = "Zip Code number must be 5 digits")
    private String zipCode;
}
