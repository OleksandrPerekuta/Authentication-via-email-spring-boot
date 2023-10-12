package com.authentication.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    @Email(message = "Incorrect email format")
    @NotEmpty(message = "Email can't be empty or null")
    private String email;
    @NotEmpty(message = "Password can't be empty or null")
    private String password;
}
