package com.example.model;


import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int contactId;

    @NotBlank(message = "Name must be not blank")
    @Size(min = 3, message = "Name must be at least 3 symbols long")
    private String name;

    @NotBlank(message = "Mobile number must be not blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Column(name = "mobile_num")
    private String mobileNum;

    @NotBlank(message = "Email must be not blank")
    @Email(message = "Provide a valid email address")
    private String email;

    @NotBlank(message = "Subject must be not blank")
    @Size(min = 4, message = "Subject must be at least 4 symbols long")
    private String subject;

    @NotBlank(message = "Message must be not blank")
    @Size(min = 10, message = "Subject must be at least 10 symbols long")
    private String message;

    private String status;


}
