package com.labdistrib.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.labdistrib.dto.CategoryDTO;
import com.labdistrib.dto.CreateCategoryRequest;
import com.labdistrib.service.CategoryService;

/**
 * CategoryController — REST Endpoints for Categories
 * 
 * This controller handles HTTP requests for the /api/categories URL.
 * It delegates ALL business logic to CategoryService — the controller's
 * only job is to receive the request and return the response.
 * 
 * NEW ANNOTATIONS:
 * 
 * @PostMapping — "When someone sends a POST request, run this method"
 *   POST = creating new data
 * 
 * @PutMapping("/{id}") — "When someone sends PUT to /api/categories/5, run this"
 *   PUT = updating existing data
 * 
 * @DeleteMapping("/{id}") — "When someone sends DELETE, run this"
 *   DELETE = removing data
 * 
 * @PathVariable — Extracts the {id} from the URL
 *   URL: /api/categories/5 → id = 5
 * 
 * @RequestBody — Reads the JSON from the request and converts it to a Java object
 *   JSON: {"name": "New Category"} → CreateCategoryRequest object
 * 
 * ResponseEntity<T> — Lets you set the HTTP status code:
 *   ResponseEntity.ok(data)                     → 200 OK
 *   ResponseEntity.status(201).body(data)       → 201 Created
 *   ResponseEntity.noContent().build()          → 204 No Content
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * GET /api/categories — List all active categories
     * 
     * Try it: http://localhost:8080/api/categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * GET /api/categories/{id} — Get a single category
     * 
     * Try it: http://localhost:8080/api/categories/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * POST /api/categories — Create a new category
     * 
     * Request body: {"name": "Safety Equipment", "description": "Lab safety gear"}
     * Response: 201 Created with the new category data
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryDTO created = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/categories/{id} — Update an existing category
     * 
     * Request body: {"name": "Updated Name", "description": "Updated description"}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    /**
     * DELETE /api/categories/{id} — Soft-delete a category
     * 
     * Returns 204 No Content (success, but nothing to return)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
