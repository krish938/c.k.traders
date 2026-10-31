package com.labdistrib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labdistrib.entity.Product;

/**
 * ProductRepository — Database Access for Products
 * 
 * This repository has more custom methods because products are the core
 * business object — they need to be searched, filtered by category, etc.
 * 
 * SPRING DATA JPA NAMING RULES:
 * 
 * findBy + FieldName + Condition = automatic SQL generation
 * 
 *   findByCategoryId(5L)
 *     → SELECT * FROM products WHERE category_id = 5
 * 
 *   findByNameContainingIgnoreCase("micro")
 *     → SELECT * FROM products WHERE LOWER(name) LIKE '%micro%'
 * 
 *   findByActiveTrueAndCategoryId(5L)
 *     → SELECT * FROM products WHERE active = true AND category_id = 5
 * 
 * You NEVER write SQL. Spring reads the method name and builds the query.
 * This is one of the most powerful features of Spring Data JPA.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /** Find all active (visible) products */
    List<Product> findByActiveTrue();

    /** Find all products in a specific category */
    List<Product> findByCategoryId(Long categoryId);

    /** Find all products from a specific manufacturer */
    List<Product> findByManufacturerId(Long manufacturerId);

    /** Search products by name (case-insensitive, partial match) */
    List<Product> findByNameContainingIgnoreCase(String keyword);

    /** Find active products in a specific category */
    List<Product> findByActiveTrueAndCategoryId(Long categoryId);

    /** Find active products from a specific manufacturer */
    List<Product> findByActiveTrueAndManufacturerId(Long manufacturerId);
}
