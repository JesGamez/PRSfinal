package com.example.assetmanagement.controller;

import com.example.assetmanagement.model.Asset;
import com.example.assetmanagement.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // POST /assets – Registrar un nuevo activo.
    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        Asset newAsset = assetService.createAsset(asset);
        return new ResponseEntity<>(newAsset, HttpStatus.CREATED);
    }

    // GET /assets – Listar todos los activos.
    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    // GET /assets/{id} – Detalle de un activo.
    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long id) {
        Asset asset = assetService.getAssetById(id);
        return ResponseEntity.ok(asset);
    }

    // PUT /assets/{id} – Actualización de estado o información.
    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset assetDetails) {
        Asset updatedAsset = assetService.updateAsset(id, assetDetails);
        return ResponseEntity.ok(updatedAsset);
    }
}
