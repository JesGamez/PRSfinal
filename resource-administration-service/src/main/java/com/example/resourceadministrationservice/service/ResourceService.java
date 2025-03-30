package com.example.resourceadministrationservice.service;

import com.example.resourceadministrationservice.exception.ResourceNotFoundException;
import com.example.resourceadministrationservice.model.Resource;
import com.example.resourceadministrationservice.model.ResourceType;
import com.example.resourceadministrationservice.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    // Registrar un alquiler
    public Resource createRental(Resource resource) {
        resource.setResourceType(ResourceType.RENTAL);
        resource.setCreatedAt(LocalDateTime.now());
        // Puedes definir un estado inicial, por ejemplo "pending"
        if (resource.getStatus() == null) {
            resource.setStatus("pending");
        }
        return resourceRepository.save(resource);
    }

    // Crear un evento
    public Resource createEvent(Resource resource) {
        resource.setResourceType(ResourceType.EVENT);
        resource.setCreatedAt(LocalDateTime.now());
        if (resource.getStatus() == null) {
            resource.setStatus("scheduled");
        }
        return resourceRepository.save(resource);
    }

    // Listar todos los recursos (alquileres, eventos, solicitudes)
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    // Actualizar estado o detalles de una reserva/evento
    public Resource updateResource(Long id, Resource updatedData) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        if (updatedData.getTitle() != null) {
            resource.setTitle(updatedData.getTitle());
        }
        if (updatedData.getDescription() != null) {
            resource.setDescription(updatedData.getDescription());
        }
        if (updatedData.getStatus() != null) {
            resource.setStatus(updatedData.getStatus());
        }
        resource.setUpdatedAt(LocalDateTime.now());
        return resourceRepository.save(resource);
    }
}
