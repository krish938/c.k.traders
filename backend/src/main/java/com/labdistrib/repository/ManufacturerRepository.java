package com.labdistrib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labdistrib.entity.Manufacturer;

/**
 * ManufacturerRepository — Database Access for Manufacturers
 * 
 * Same pattern as CategoryRepository.
 * Extends JpaRepository to get all CRUD methods for free.
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    /** Find all active manufacturers */
    List<Manufacturer> findByActiveTrue();

    /** Find a manufacturer by exact name (case-insensitive) */
    Optional<Manufacturer> findByNameIgnoreCase(String name);
}
