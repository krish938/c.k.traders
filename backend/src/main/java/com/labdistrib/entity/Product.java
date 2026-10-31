package com.labdistrib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Product Entity — The core business object.
 * 
 * Maps to the "products" table in MySQL.
 * 
 * A Product represents something C.K.Traders distributes — a microscope,
 * centrifuge, reagent kit, etc. Each product belongs to ONE category and
 * ONE manufacturer.
 * 
 * IMPORTANT: There is NO price field.
 * Customers see "Send Enquiry" instead of a price.
 * Pricing is handled through the enquiry system (Stage 9).
 * 
 * RELATIONSHIPS:
 * 
 * @ManyToOne — "Many products belong to ONE category"
 *   This is the OWNING side of the relationship.
 *   "Owning side" means: this table has the foreign key column (category_id).
 * 
 *   In the database, products table will have:
 *   - category_id (FK → categories.id)
 *   - manufacturer_id (FK → manufacturers.id)
 * 
 * @JoinColumn(name = "category_id") — "Create a column called 'category_id' as the FK"
 * 
 * FetchType.LAZY — "Don't load the category data until I actually ask for it"
 *   This is a performance optimization. If you load 100 products, you don't want
 *   to also load 100 categories automatically. Load them only when needed.
 */
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Technical specifications for the product.
     * Stored as TEXT — could be plain text, or structured as key:value pairs.
     * Example: "Magnification: 40x-1000x\nDisplay: 7\" LCD\nWeight: 5.2kg"
     */
    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private boolean active = true;

    // NO PRICE FIELD — prices are handled through the enquiry system

    /**
     * The category this product belongs to.
     * 
     * @ManyToOne — "Many products → ONE category"
     * FetchType.LAZY — "Load category only when I call product.getCategory()"
     * @JoinColumn — "The FK column in this table is called 'category_id'"
     * 
     * In plain Java terms:
     *   Instead of storing categoryId (an integer), we store the entire Category object.
     *   Hibernate handles the conversion:
     *     Java:  product.getCategory().getName()  →  "Laboratory Equipment"
     *     SQL:   SELECT c.name FROM categories c WHERE c.id = products.category_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * The manufacturer of this product.
     * Same pattern as category — @ManyToOne with lazy loading.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    // =========================================================================
    // Constructors
    // =========================================================================

    public Product() {
    }

    public Product(String name, String description, Category category, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.manufacturer = manufacturer;
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

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Product{id=" + getId() + ", name='" + name + "', active=" + active + "}";
    }
}
