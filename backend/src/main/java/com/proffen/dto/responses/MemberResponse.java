package com.proffen.dto.responses;

import com.proffen.models.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MemberResponse(

        @NotNull(message = "Member id must not be null")
        @Positive(message = "Member id must be positive")
        Long id,

        @NotBlank(message = "Username must not be empty")
        @Size(min = 4, max = 32, message = "Username must be 4-32 characters")
        String username,

        @NotBlank(message = "Name must not be empty")
        @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
        String name,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email should be valid")
        String email//,

        //@NotNull(message = "Role must not be null")
        //Role role
        ) {
        //test comment
    public static MemberResponse toResponse(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getName(),
                member.getEmail()//,
                //member.getRole()
        );
    }
}
