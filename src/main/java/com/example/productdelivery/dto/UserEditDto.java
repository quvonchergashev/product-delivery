package com.example.productdelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;


}
