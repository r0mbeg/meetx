package com.proffen.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "User registration request payload")
public record MemberRegisterRequest(

        @Schema(
                description = "Unique username for the new account",
                example = "test_username",
                minLength = 4,
                maxLength = 32,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Username must not be blank")
        @Size(min = 4, max = 32, message = "Username must be 4-32 characters")
        String username,

        @Schema(
                description = "Password for new account",
                example = "test_password",
                minLength = 5,
                maxLength = 64,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password must not be blank")
        @Size(min = 5, max = 64, message = "Password must be 8-64 characters")
        String password,

        @Schema(
                description = "Full name of the member",
                example = "Ivan Smirnov",
                minLength = 2,
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Name must not be blank")
        @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
        String name,


        @Schema(
                description = "Email of the member",
                example = "smirnov@mail.ru",
                minLength = 2,
                maxLength = 100,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email should be valid")
        String email
) {

}
