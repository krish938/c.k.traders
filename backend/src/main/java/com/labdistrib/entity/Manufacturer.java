package com.labdistrib.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Manufacturer Entity — Represents a product manufacturer/brand.
 * 
 * Maps to the "manufacturers" table in MySQL.
 * 
 * A manufacturer is a company that makes the products C.K.Traders distributes.
 * One manufacturer can have many products (e.g., "Olympus" makes microscopes,
 * cameras, and imaging equipment).
 * 
 * Like categories, manufacturers are NOT hardcoded — the Business Admin
 * creates and manages them through the admin dashboard.
 */
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(length = 255)
    private String website;

    @Column(nullable = false)
    private boolean active = true;

    /**
     * The list of products from this manufacturer.
     * Inverse side of the relationship — FK is in the products table.
     */
    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products = new ArrayList<>();

    // =========================================================================
    // Constructors
    // =========================================================================

    public Manufacturer() {
    }

    public Manufacturer(String name, String description) {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
        return "Manufacturer{id=" + getId() + ", name='" + name + "', active=" + active + "}";
    }
}
