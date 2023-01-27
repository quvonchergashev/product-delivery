package com.example.productdelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminDto {
    @NotBlank(message = "full Name must not be null")
    private String firstname;

    @NotBlank(message = "full Name must not be null")
    private String lastname;

    @NotBlank(message = "PhoneNumber must not be null")
    private String phoneNumber;

    @NotBlank(message = "User name must not be null")
    private String username;

    @NotBlank(message = "Password must not be null")
    private String password;

    @NotBlank(message = "PrePassword must not be null")
    private String prePassword;

    @NotBlank(message = "Email must not be null")
    private String email;

    private String encodingName;
}
