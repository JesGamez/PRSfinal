package com.example.usermanagementservice.service;

import com.example.usermanagementservice.exception.ResourceNotFoundException;
import com.example.usermanagementservice.model.Role;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Inyectado desde SecurityConfig
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword, Role role, String email) {
        // En producción, verifica que el username no exista
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User(username, encodedPassword, role, email);
        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        System.out.println("User retrieved: " + user);
        return user;
    }

    public User updateUser(Long id, Role role, String email) {
        User user = getUserById(id);
        user.setRole(role);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
