package com.proffen.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberLogInRequest(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 4, max = 32, message = "Username must be 4-32 characters")
        String username,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 5, max = 64, message = "Password must be 8-64 characters")
        String password
) {
}
