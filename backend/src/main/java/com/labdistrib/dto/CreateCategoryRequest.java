package com.labdistrib.dto;

/**
 * CreateCategoryRequest — What the browser sends when creating/updating a category.
 * 
 * Notice: No 'id' field — the server assigns the ID automatically.
 * No 'createdAt' — the server sets timestamps via @PrePersist.
 * 
 * This is a REQUEST DTO — it defines what the client is ALLOWED to send.
 * Even if someone sends extra fields in the JSON, they'll be ignored.
 */
public class CreateCategoryRequest {

    private String name;
    private String description;
    private String imageUrl;

    public CreateCategoryRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
