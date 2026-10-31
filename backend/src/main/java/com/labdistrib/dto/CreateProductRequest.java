package com.labdistrib.dto;

/**
 * CreateProductRequest — Request DTO for creating/updating a product.
 * 
 * Notice: categoryId and manufacturerId are Long references, not full objects.
 * The service layer looks up the actual Category and Manufacturer from the database.
 */
public class CreateProductRequest {

    private String name;
    private String description;
    private String specifications;
    private String imageUrl;
    private Long categoryId;
    private Long manufacturerId;

    public CreateProductRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getManufacturerId() { return manufacturerId; }
    public void setManufacturerId(Long manufacturerId) { this.manufacturerId = manufacturerId; }
}
