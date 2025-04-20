package com.proffen.controllers;

import com.proffen.dto.requests.MemberLogInRequest;
import com.proffen.dto.requests.MemberRegisterRequest;
import com.proffen.dto.responses.JwtResponse;
import com.proffen.dto.responses.MemberResponse;
import com.proffen.models.Member;
import com.proffen.security.JwtTokenProvider;
import com.proffen.services.MemberService;
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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberService.register(request, passwordEncoder);
        log.info("Register attempt for username: {}", request.username());
        return ResponseEntity.ok(MemberResponse.toResponse(member));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody MemberLogInRequest request) {
        Member member = memberService.loadByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member);

        log.info("Login attempt for username: {}", request.username());
        return ResponseEntity.ok(new JwtResponse(member.getId(), member.getUsername(), accessToken, refreshToken));
    }
}
