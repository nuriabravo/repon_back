package com.nuriabravo.repon.infrastructure.controller;

import com.nuriabravo.repon.application.dto.input.LoginRequest;
import com.nuriabravo.repon.application.dto.output.LoginResponse;
import com.nuriabravo.repon.application.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Permite la autorización y autenticación")
public class AuthController {

    private final UserAuthService userService;

    @PostMapping
    @Operation(summary = "Autenticar y obtener un token de acceso")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse result = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(result.getToken(), result.getExpiration()));
    }
}