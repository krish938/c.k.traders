package com.labdistrib.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.labdistrib.dto.CreateManufacturerRequest;
import com.labdistrib.dto.ManufacturerDTO;
import com.labdistrib.entity.Manufacturer;
import com.labdistrib.exception.ResourceNotFoundException;
import com.labdistrib.repository.ManufacturerRepository;

/**
 * ManufacturerService — Business Logic for Manufacturers
 * Same pattern as CategoryService.
 */
@Service
public class ManufacturerService {

    private static final Logger log = LoggerFactory.getLogger(ManufacturerService.class);
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<ManufacturerDTO> getAllManufacturers() {
        log.debug("Fetching all active manufacturers");
        return manufacturerRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ManufacturerDTO getManufacturerById(Long id) {
        log.debug("Fetching manufacturer with id: {}", id);
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));
        return toDTO(manufacturer);
    }

    public ManufacturerDTO createManufacturer(CreateManufacturerRequest request) {
        log.info("Creating manufacturer: {}", request.getName());

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer name is required");
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(request.getName().trim());
        manufacturer.setDescription(request.getDescription());
        manufacturer.setLogoUrl(request.getLogoUrl());
        manufacturer.setWebsite(request.getWebsite());

        Manufacturer saved = manufacturerRepository.save(manufacturer);
        log.info("Created manufacturer with id: {}", saved.getId());
        return toDTO(saved);
    }

    public ManufacturerDTO updateManufacturer(Long id, CreateManufacturerRequest request) {
        log.info("Updating manufacturer with id: {}", id);

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            manufacturer.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            manufacturer.setDescription(request.getDescription());
        }
        if (request.getLogoUrl() != null) {
            manufacturer.setLogoUrl(request.getLogoUrl());
        }
        if (request.getWebsite() != null) {
            manufacturer.setWebsite(request.getWebsite());
        }

        Manufacturer saved = manufacturerRepository.save(manufacturer);
        log.info("Updated manufacturer with id: {}", saved.getId());
        return toDTO(saved);
    }

    public void deleteManufacturer(Long id) {
        log.info("Soft-deleting manufacturer with id: {}", id);

        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));

        manufacturer.setActive(false);
        manufacturerRepository.save(manufacturer);
        log.info("Soft-deleted manufacturer with id: {}", id);
    }

    private ManufacturerDTO toDTO(Manufacturer manufacturer) {
        ManufacturerDTO dto = new ManufacturerDTO();
        dto.setId(manufacturer.getId());
        dto.setName(manufacturer.getName());
        dto.setDescription(manufacturer.getDescription());
        dto.setLogoUrl(manufacturer.getLogoUrl());
        dto.setWebsite(manufacturer.getWebsite());
        dto.setActive(manufacturer.isActive());
        dto.setProductCount(manufacturer.getProducts() != null ? manufacturer.getProducts().size() : 0);
        return dto;
    }
}
