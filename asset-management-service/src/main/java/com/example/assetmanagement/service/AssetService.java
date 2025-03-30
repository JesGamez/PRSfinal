package com.example.assetmanagement.service;

import com.example.assetmanagement.exception.ResourceNotFoundException;
import com.example.assetmanagement.model.Asset;
import com.example.assetmanagement.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    // Registrar un nuevo activo
    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    // Listar todos los activos
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // Obtener el detalle de un activo por ID
    public Asset getAssetById(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with id: " + id));
    }

    // Actualizar un activo existente
    public Asset updateAsset(Long id, Asset assetDetails) {
        Asset asset = getAssetById(id);
        asset.setName(assetDetails.getName());
        asset.setType(assetDetails.getType());
        asset.setStatus(assetDetails.getStatus());
        asset.setMaintenanceHistory(assetDetails.getMaintenanceHistory());
        return assetRepository.save(asset);
    }
}
