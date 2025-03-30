package com.example.assetmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assets")
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del activo
    private String name;

    // Tipo: "inmueble" o "mueble"
    private String type;

    // Estado del activo (por ejemplo, "disponible", "en mantenimiento", etc.)
    private String status;

    // Historial de mantenimientos (se almacena como un texto, podría ser JSON en una versión avanzada)
    @Column(length = 2048)
    private String maintenanceHistory;

    // Constructores, getters y setters

    public Asset() {
    }

    public Asset(String name, String type, String status, String maintenanceHistory) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.maintenanceHistory = maintenanceHistory;
    }

}