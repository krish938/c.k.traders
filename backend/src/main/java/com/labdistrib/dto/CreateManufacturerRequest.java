package com.labdistrib.dto;

/**
 * CreateManufacturerRequest — Request DTO for creating/updating a manufacturer.
 */
public class CreateManufacturerRequest {

    private String name;
    private String description;
    private String logoUrl;
    private String website;

    public CreateManufacturerRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
}
