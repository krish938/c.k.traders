package com.labdistrib.exception;

/**
 * ResourceNotFoundException — Thrown when an entity is not found in the database.
 * 
 * For example, if someone requests GET /api/categories/99 and category with id=99
 * doesn't exist, we throw this exception. The GlobalExceptionHandler catches it
 * and returns a proper HTTP 404 response.
 * 
 * Extends RuntimeException so we don't need to declare it with 'throws'
 * in every method signature.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}
