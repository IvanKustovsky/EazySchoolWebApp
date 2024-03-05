package com.example.model;

import com.example.annotation.FieldsValueMatch;
import com.example.annotation.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private int personId;
    @NotBlank(message = "Name must be not blank")
    @Size(min = 3, message = "Name must be at least 3 symbols long")
    private String name;

    @NotBlank(message = "Mobile number must be not blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email must be not blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Confirm Email must be not blank")
    @Email(message = "Please provide a confirm valid email address")
    @Transient
    @JsonIgnore
    private String confirmEmail;

    @NotBlank(message = "Password must be not blank")
    @Size(min = 5, message = "Password must be at least 5 symbols long")
    @PasswordValidator
    @Column(name = "password")
    @JsonIgnore
    private String pwd;

    @NotBlank(message = "Confirm Password must be not blank")
    @Size(min = 5, message = "Confirm Password must be at least 5 symbols long")
    @Transient
    @JsonIgnore
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "roleId", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "classId")
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {
            @JoinColumn(name = "person_id", referencedColumnName = "personId")},
            inverseJoinColumns = {
            @JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    private Set<Course> courses = new HashSet<>();
}
