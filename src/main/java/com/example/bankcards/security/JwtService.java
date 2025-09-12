package com.example.bankcards.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    /**
     * Секретный ключ для подписи JWT
     */
    @Value("${testing.app.secret}")
    private String secret;

    /**
     * Время жизни токена в миллисекундах
     */
    @Value("${testing.app.lifetime}")
    private Integer lifetime;

    /**
     * Генерирует JWT токен для аутентифицированного пользователя
     * @param authentication объект аутентификации Spring Security
     * @return строка c JWT-токеном
     */
    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date( (new Date()).getTime() + lifetime ))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Извлекает логин пользоваетеля с JWT-токеном
     * @param token строка с JWT токеном
     * @return Возвращает логин пользователя
     */
    public String getLoginFormJwt(String token){
        return Jwts.parser().setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
