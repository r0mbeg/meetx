package com.proffen.dto.responses;

public record JwtResponse(
        Long id,
        String username,
        String accessToken,
        String refreshToken
) {}