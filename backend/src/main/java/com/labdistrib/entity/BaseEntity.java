package com.labdistrib.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * BaseEntity — Shared Parent for All Entities
 * 
 * WHY DOES THIS EXIST?
 * Every database table needs an ID, a creation timestamp, and an update timestamp.
 * Instead of writing these fields in EVERY entity class, we write them ONCE here
 * and let other entities inherit them.
 * 
 * In plain Java terms: This is like a parent class that provides common functionality.
 * Just like how every Animal has a name and age, every Entity has an id and timestamps.
 * 
 * KEY ANNOTATION:
 * 
 * @MappedSuperclass
 *   "Don't create a database table for this class.
 *    Just share its fields with any class that extends it."
 * 
 *   If we used @Entity instead, Hibernate would create a "base_entity" table,
 *   which we don't want — it's just a shared template.
 * 
 * @PrePersist — "Run this method BEFORE saving a NEW entity to the database"
 * @PreUpdate  — "Run this method BEFORE updating an EXISTING entity"
 * 
 * These are called "lifecycle callbacks" — they run automatically at the right time.
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primary Key — unique identifier for each row.
     * 
     * @Id — "This field is the primary key"
     * @GeneratedValue(strategy = GenerationType.IDENTITY) — "Let the database 
     *   auto-increment this value (1, 2, 3, ...)"
     * 
     * In MySQL, this creates: id BIGINT AUTO_INCREMENT PRIMARY KEY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * When this entity was first created.
     * Set automatically — you never need to set this manually.
     * 
     * @Column(name = "created_at") — "Map this field to a column called 'created_at'"
     *   Java uses camelCase (createdAt), but databases use snake_case (created_at).
     * 
     * updatable = false — "Don't change this value when updating the entity"
     *   The creation date should never change once set.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * When this entity was last updated.
     * Updated automatically every time the entity is saved.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Runs automatically BEFORE a new entity is saved to the database.
     * Sets both createdAt and updatedAt to the current time.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Runs automatically BEFORE an existing entity is updated.
     * Only updates the updatedAt timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    // In plain Java, these are standard getter/setter methods.
    // JPA needs them to read and write field values.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
