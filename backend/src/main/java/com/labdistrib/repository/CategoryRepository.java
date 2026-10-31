package com.labdistrib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labdistrib.entity.Category;

/**
 * CategoryRepository — Database Access for Categories
 * 
 * HOW DOES THIS WORK?
 * 
 * You write ZERO SQL. Spring Data JPA reads the method name and generates
 * the SQL query automatically.
 * 
 * JpaRepository<Category, Long> means:
 *   - Category = the entity type this repository manages
 *   - Long = the type of the primary key (id)
 * 
 * FREE methods you get from JpaRepository (no code needed):
 *   findAll()          → SELECT * FROM categories
 *   findById(1L)       → SELECT * FROM categories WHERE id = 1
 *   save(category)     → INSERT INTO categories ... (or UPDATE if it exists)
 *   deleteById(1L)     → DELETE FROM categories WHERE id = 1
 *   count()            → SELECT COUNT(*) FROM categories
 *   existsById(1L)     → SELECT COUNT(*) FROM categories WHERE id = 1
 * 
 * CUSTOM methods (you write the name, Spring writes the SQL):
 *   findByActiveTrue() → SELECT * FROM categories WHERE active = true
 *   findByNameIgnoreCase("microscopes") → SELECT * FROM categories WHERE LOWER(name) = LOWER('microscopes')
 * 
 * @Repository is optional here (Spring auto-detects JpaRepository interfaces)
 * but it's good practice to include it for clarity.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /** Find all active (non-deleted) categories */
    List<Category> findByActiveTrue();

    /** Find a category by its exact name (case-insensitive) */
    Optional<Category> findByNameIgnoreCase(String name);
}
