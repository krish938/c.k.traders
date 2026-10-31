package com.labdistrib.dto;

/**
 * CategoryDTO — What the API sends back to the browser when you request a category.
 * 
 * WHY NOT RETURN THE ENTITY DIRECTLY?
 * The Category entity has a List<Product> field. If we return it:
 *   Category → products → each Product has a Category → infinite loop!
 * 
 * The DTO only includes what the frontend needs — no circular references,
 * no internal fields, no surprises.
 */
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean active;
    private int productCount;

    // Default constructor
    public CategoryDTO() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }
}
