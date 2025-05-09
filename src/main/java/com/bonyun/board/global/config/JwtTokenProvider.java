package com.bonyun.board.global.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private final String secret;// 32바이트 이상 필요
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // JWT의 유효 기간
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 32 bytes long");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    // SECRET_KEY를 이용해 HMAC-SHA 알고리즘으로 서명할 키 생성

    // JWT 생성 (역할 포함)
    public String generateToken(String username,Authentication authentication, Long userId) {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username) // 사용자 정보 (subject) 설정
                .claim("role", role) // 사용자 역할 (role) 추가
                .claim("userId", userId)
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, key) // HMAC-SHA256 알고리즘으로 서명
                .compact(); // JWT 문자열 반환
    }

    // 토큰 검증 후 사용자 이름 반환
    public String validateToken(String token) {
        try {
            // JWT 파싱 및 검증
            Claims claims = Jwts.parser()
                    .setSigningKey(key) // 서명 키 설정
                    .parseClaimsJws(token) // JWT를 파싱하여 Claims(페이로드) 추출
                    .getBody();
            return claims.getSubject(); // 사용자 이름 반환
        } catch (JwtException e) {
            return null; // 검증 실패 시 null 반환
        }
    }

    // 토큰에서 역할(Role) 추출
    public String extractRole(String token) {
        try {
            // JWT 파싱 및 역할 정보 추출
            Claims claims = Jwts.parser()
                    .setSigningKey(key) // 서명 키 설정
                    .parseClaimsJws(token) // JWT를 파싱하여 Claims(페이로드) 추출
                    .getBody();
            return claims.get("role", String.class); // 역할(Role) 값 반환
        } catch (JwtException e) {
            return null; // 검증 실패 시 null 반환
        }
    }

}

