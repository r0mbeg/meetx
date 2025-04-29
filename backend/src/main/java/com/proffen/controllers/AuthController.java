package com.proffen.controllers;

import com.proffen.dto.requests.MemberLogInRequest;
import com.proffen.dto.requests.MemberRegisterRequest;
import com.proffen.dto.responses.JwtResponse;
import com.proffen.dto.responses.MemberResponse;
import com.proffen.dto.responses.ErrorResponse;
import com.proffen.models.Member;
import com.proffen.security.JwtTokenProvider;
import com.proffen.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization and Registration",
        description = "Endpoints for member authentication, registration and getting token")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400",
                description = "Invalid input parameters or malformed request",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401",
                description = "Unauthorized - Invalid credentials or expired token",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403",
                description = "Forbidden - Insufficient permissions",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422",
                description = "Validation error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500",
                description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "Register new member",
            description = "Creates new member account and returns authentication tokens",
            operationId = "registerMember")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Registration successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "409",
                    description = "Member is already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MemberResponse> register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberService.register(request, passwordEncoder);
        log.info("Register attempt for username: {}", request.username());
        return ResponseEntity.ok(MemberResponse.toResponse(member));
    }


    @PostMapping("/login")
    @Operation(summary = "Authenticate member",
            description = "Validates member's credentials and returns JWT tokens for authorization",
            operationId = "loginMember")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Member is not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Validation error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<JwtResponse> login(@RequestBody MemberLogInRequest request) {
        Member member = memberService.loadByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            log.warn("Invalid login attempt for username: {}", request.username());
            throw new BadCredentialsException("Invalid credentials");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        log.info("Login attempt for username: {}", request.username());
        return ResponseEntity.ok(new JwtResponse(member.getId(), member.getUsername(), accessToken, refreshToken));
    }
}
