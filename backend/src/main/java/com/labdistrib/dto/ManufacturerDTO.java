package com.labdistrib.dto;

/**
 * ManufacturerDTO — Response DTO for manufacturer data.
 */
public class ManufacturerDTO {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String website;
    private boolean active;
    private int productCount;

    public ManufacturerDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public int getProductCount() { return productCount; }
    public void setProductCount(int productCount) { this.productCount = productCount; }
}
