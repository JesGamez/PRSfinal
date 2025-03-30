package com.example.resourceadministrationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "resources")
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de recurso: RENTAL, EVENT, o COMMON_USE
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    private String title;
    private String description;

    // Por ejemplo, "pending", "approved", "rejected", etc.
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores
    public Resource() {
    }
}
