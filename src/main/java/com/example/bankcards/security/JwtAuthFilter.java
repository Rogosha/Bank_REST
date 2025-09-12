package com.example.bankcards.security;

import com.example.bankcards.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Фильтрация входящего HTTP-запроса для проверки и обработки JWT-токена.
     *
     * @param request HTTP-запрос
     * @param response HTTP-ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException если произошла ошибка сервлета
     * @throws IOException если произошла ошибка ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        // Пропускаем фильтрацию для эндпоинта аутентификации
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = null;
        User user = new User();
        UserDetails userDetails = null;
        UsernamePasswordAuthenticationToken auth = null;
        try {
            // Извлекаем заголовок авторизации
            String headerAuth = request.getHeader("Authorization");
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7);
            }
            // Проверяем и обрабатываем JWT-токен
            if (jwt != null) {
                try {
                    user.setUsername(jwtService.getLoginFormJwt(jwt));
                } catch (ExpiredJwtException e) {

                }
                if (user.getId() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("HEEELL YEEEEAR");
                    userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                    auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {

        }
        filterChain.doFilter(request, response);
    }
}
