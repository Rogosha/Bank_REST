package com.example.bankcards.controller;

import com.example.bankcards.dto.AuthRequest;
import com.example.bankcards.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    /**
     * Регистрация нового пользователя
     * @param authRequest Тело запроса, содержащее email, password, role
     * @return HTTP код и текстовое сообщение
     */
    @Operation(summary = "Registration", description = "Allows to register by email, password and role")
    @PostMapping("/signup")
    ResponseEntity<?> signUp(@Parameter(description = "Sign up request with email, password and role parameters") @RequestBody AuthRequest authRequest){
        return authService.register(authRequest);
    }

    /**
     * Регистрация нового пользователя
     * @param authRequest Тело запроса, содержащее email, password
     * @return HTTP код и текстовое сообщение
     */
    @Operation(summary = "Authentication", description = "Allows to authenticate by email and password. Return JWT-token")
    @PostMapping("/signin")
    ResponseEntity<?> signIn(@Parameter(description = "Sign up request with email and password parameters") @RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }


}
