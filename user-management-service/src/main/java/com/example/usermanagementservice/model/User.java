package com.example.usermanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    // Se pueden manejar múltiples roles, pero aquí simplificamos con un solo rol
    @Enumerated(EnumType.STRING)
    private Role role;

    // Datos personales, correo, etc.
    private String email;

    public User() {}

    public User(String username, String password, Role role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

}
