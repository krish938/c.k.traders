package com.labdistrib.dto;

/**
 * ProductDTO — Response DTO for product data.
 * 
 * Notice we include categoryName and manufacturerName as simple Strings,
 * NOT the full Category/Manufacturer objects. This avoids circular references
 * and keeps the JSON response clean and flat.
 */
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private String specifications;
    private String imageUrl;
    private boolean active;
    private Long categoryId;
    private String categoryName;
    private Long manufacturerId;
    private String manufacturerName;

    public ProductDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Long getManufacturerId() { return manufacturerId; }
    public void setManufacturerId(Long manufacturerId) { this.manufacturerId = manufacturerId; }

    public String getManufacturerName() { return manufacturerName; }
    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }
}
