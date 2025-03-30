package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.exception.ResourceNotFoundException;
import com.example.usermanagementservice.model.Role;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // DTO para registro
    public static class RegisterRequest {
        public String username;
        public String password;
        public Role role;
        public String email;
    }

    // POST /users/register – Registro de un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.username, request.password, request.role, request.email);
        return ResponseEntity.ok(user);
    }

    // GET /users/{id} – Obtener detalles de un usuario
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        System.out.println("getUserById = " + id);
        try {
            System.out.println("getUserById = " + id);
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // PUT /users/{id} – Actualizar rol o datos personales
    public static class UpdateRequest {
        public Role role;
        public String email;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateRequest request) {
        User updatedUser = userService.updateUser(id, request.role, request.email);
        return ResponseEntity.ok(updatedUser);
    }
}
