package com.labdistrib.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labdistrib.dto.CreateManufacturerRequest;
import com.labdistrib.dto.ManufacturerDTO;
import com.labdistrib.service.ManufacturerService;

/**
 * ManufacturerController — REST Endpoints for Manufacturers
 * Same pattern as CategoryController.
 */
@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    /** GET /api/manufacturers */
    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.getAllManufacturers());
    }

    /** GET /api/manufacturers/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturerById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }

    /** POST /api/manufacturers */
    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody CreateManufacturerRequest request) {
        ManufacturerDTO created = manufacturerService.createManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/manufacturers/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable Long id,
                                                              @RequestBody CreateManufacturerRequest request) {
        return ResponseEntity.ok(manufacturerService.updateManufacturer(id, request));
    }

    /** DELETE /api/manufacturers/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.noContent().build();
    }
}
