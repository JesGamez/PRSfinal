package com.example.resourceadministrationservice.controller;

import com.example.resourceadministrationservice.model.Resource;
import com.example.resourceadministrationservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    // DTO para solicitudes de creación/actualización
    public static class ResourceRequest {
        public String title;
        public String description;
        public String status; // Opcional para actualizar
    }

    // POST /resources/rentals – Registrar un alquiler
    @PostMapping("/rentals")
    public ResponseEntity<Resource> createRental(@RequestBody ResourceRequest request) {
        Resource resource = new Resource();
        resource.setTitle(request.title);
        resource.setDescription(request.description);
        if (request.status != null) {
            resource.setStatus(request.status);
        }
        Resource created = resourceService.createRental(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // POST /resources/events – Crear un evento
    @PostMapping("/events")
    public ResponseEntity<Resource> createEvent(@RequestBody ResourceRequest request) {
        Resource resource = new Resource();
        resource.setTitle(request.title);
        resource.setDescription(request.description);
        if (request.status != null) {
            resource.setStatus(request.status);
        }
        Resource created = resourceService.createEvent(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /resources – Listar recursos y solicitudes
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    // PUT /resources/{id} – Actualizar estado o detalles de una reserva/evento
    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody ResourceRequest request) {
        Resource updatedData = new Resource();
        updatedData.setTitle(request.title);
        updatedData.setDescription(request.description);
        updatedData.setStatus(request.status);
        Resource updated = resourceService.updateResource(id, updatedData);
        return ResponseEntity.ok(updated);
    }
}
