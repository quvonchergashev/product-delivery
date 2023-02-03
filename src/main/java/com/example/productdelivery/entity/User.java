package com.example.productdelivery.entity;

import com.example.productdelivery.consts.Status;
import com.example.productdelivery.entity.template.AbsLongEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User extends AbsLongEntity {

    @NotNull(message = "first name is required")
    @Pattern(message = "just enter a letter", regexp = "^[a-zA-Z']+$")
    @Size(message = "First name must be at least 3 characters long", min = 3)
    private String firstname;

    @NotNull(message = "last name is required")
    @Pattern(message = "just enter a letter", regexp = "^[a-zA-Z']+$")
    @Size(message = "Last name must be at least 3 characters long", min = 3)
    private String lastname;

    @Column(nullable = false, unique = true)
    @Size(message = "User name must be at least 3 characters long", min = 3)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 8)
//    @Pattern(message = "The password must contain at least 8 characters, at least one lowercase letter, one uppercase letter, one number and one special character (!#@$*)",
//            regexp ="^[a-zA-Z]+[0-9]+$")
    private String password;

    @Column(nullable = false)
    private String image;

    private String status = String.valueOf(Status.ENABLED);

    private String phoneNumber;

    private String encodingName;

    @OneToOne
    @JsonIgnore
    private Roles role;


}
