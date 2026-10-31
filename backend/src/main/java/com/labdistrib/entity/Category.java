package com.labdistrib.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Category Entity — Represents a product category in the database.
 * 
 * WHAT IS THIS?
 * A Category groups products together (e.g., "Laboratory Equipment", "Consumables").
 * This class maps to the "categories" table in MySQL.
 * 
 * DATABASE TABLE (created automatically by Hibernate):
 * +----+----------------------+--------------------+-----------+--------+------------+------------+
 * | id | name                 | description        | image_url | active | created_at | updated_at |
 * +----+----------------------+--------------------+-----------+--------+------------+------------+
 * | 1  | Laboratory Equipment | Precision instr... | null      | true   | 2026-09-23 | 2026-09-23 |
 * +----+----------------------+--------------------+-----------+--------+------------+------------+
 * 
 * KEY ANNOTATIONS:
 * 
 * @Entity — "This class represents a database table"
 *   Without this, Hibernate ignores the class completely.
 * 
 * @Table(name = "categories") — "The table should be called 'categories'"
 *   If you don't specify this, Hibernate names the table after the class (Category → category).
 * 
 * @Column — "Configure how this field maps to a database column"
 *   nullable = false → NOT NULL constraint
 *   unique = true → UNIQUE constraint
 *   length = 100 → VARCHAR(100)
 *   columnDefinition = "TEXT" → use TEXT type instead of VARCHAR
 * 
 * @OneToMany — "One category has many products"
 *   mappedBy = "category" → "The Product class has a field called 'category' that owns this relationship"
 *   This does NOT create a column in the categories table.
 *   The foreign key (category_id) lives in the products table.
 * 
 * IMPORTANT: Categories are NOT hardcoded.
 * The Business Admin creates them dynamically through the admin dashboard.
 * The sample data in DataInitializer is just for testing.
 */
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Whether this category is visible to customers.
     * 'active = false' means soft-deleted (hidden but not removed from database).
     * 
     * Why soft-delete? Deleting a category that has products would cause errors.
     * Instead, we hide it. The data stays safe.
     */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * The list of products in this category.
     * 
     * @OneToMany — "One category has many products"
     * mappedBy = "category" — "Look at the 'category' field in Product.java to find the FK"
     * 
     * This is the INVERSE side of the relationship.
     * The OWNING side is Product.category (which has @ManyToOne).
     * 
     * Why? Because the foreign key (category_id) is in the products table, not here.
     */
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    // =========================================================================
    // Constructors
    // =========================================================================

    /** Default constructor — required by JPA/Hibernate */
    public Category() {
    }

    /** Convenience constructor for creating categories */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{id=" + getId() + ", name='" + name + "', active=" + active + "}";
    }
}
