package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.config.JwtService;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    // DTO para login
    public static class LoginRequest {
        public String username;
        public String password;
    }

    // DTO para respuesta con token
    public static class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Autenticar usuario
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username, request.password)
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Cargar el usuario
        User user = userService.findByUsername(request.username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Generar token
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
