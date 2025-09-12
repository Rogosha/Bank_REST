package com.example.bankcards.service;

import com.example.bankcards.dto.AuthRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthService {
    @Autowired
    private final TransactionalService transactionalService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param request Запрос на регистрацию пользователя.
     * @return HTTP-ответ с сообщением об успешной регистрации или ошибке.
     */
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (!userRepository.findByUsername(request.getUsername()).isPresent()) {
            String hashCode = passwordEncoder.encode(request.getPassword());
            User user = new User(request.getUsername(), hashCode);
            transactionalService.save(user);
            return login(request);
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: user with this username already create");
    }
}
