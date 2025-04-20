package com.proffen.security;

import com.proffen.configs.JwtProperties;
import com.proffen.models.Member;
import com.proffen.repo.MemberRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final MemberRepo memberRepo;  // Для получения данных о пользователе
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }


    // Метод для создания access токена
    public String createAccessToken(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .subject(member.getUsername())
                .issuedAt(now)
                .expiration(validity)
                .claim("id", member.getId())
                .claim("roles", member.getRole().name())
                .signWith(key)  // key — это SecretKey, созданный заранее в init()
                .compact();
    }

    // Метод для создания refresh токена
    public String createRefreshToken(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());

        return Jwts.builder()
                .subject(member.getUsername())
                .issuedAt(now)
                .expiration(validity)
                .claim("id", member.getId())
                .signWith(key)
                .compact();
    }

    // Проверка токена
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    /*
    // Извлечение ID из токена
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.get("id", String.class));
    }*/

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("id", Long.class); // Получаем id как Long
    }
}

