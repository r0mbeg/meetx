package com.proffen.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberRegisterRequest(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 4, max = 32, message = "Username must be 4-32 characters")
        String username,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 5, max = 64, message = "Password must be 8-64 characters")
        String password,

        @NotBlank(message = "Name must not be empty")
        @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
        String name,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email should be valid")
        String email
) {

}
