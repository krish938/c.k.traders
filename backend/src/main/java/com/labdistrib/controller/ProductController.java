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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.labdistrib.dto.CreateProductRequest;
import com.labdistrib.dto.ProductDTO;
import com.labdistrib.service.ProductService;

/**
 * ProductController — REST Endpoints for Products
 * 
 * This is the most feature-rich controller because products are the core
 * business object. It supports:
 * - Basic CRUD (create, read, update, delete)
 * - Filter by category
 * - Filter by manufacturer
 * - Search by keyword
 * 
 * NEW ANNOTATION:
 * 
 * @RequestParam — Reads query parameters from the URL
 *   URL: /api/products/search?keyword=micro → keyword = "micro"
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/products — List all active products
     * 
     * Try it: http://localhost:8080/api/products
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * GET /api/products/{id} — Get a single product
     * 
     * Try it: http://localhost:8080/api/products/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * GET /api/products/category/{categoryId} — Products by category
     * 
     * Try it: http://localhost:8080/api/products/category/1
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    /**
     * GET /api/products/manufacturer/{manufacturerId} — Products by manufacturer
     * 
     * Try it: http://localhost:8080/api/products/manufacturer/1
     */
    @GetMapping("/manufacturer/{manufacturerId}")
    public ResponseEntity<List<ProductDTO>> getProductsByManufacturer(@PathVariable Long manufacturerId) {
        return ResponseEntity.ok(productService.getProductsByManufacturer(manufacturerId));
    }

    /**
     * GET /api/products/search?keyword=micro — Search products by name
     * 
     * @RequestParam reads 'keyword' from the URL query string.
     * Try it: http://localhost:8080/api/products/search?keyword=micro
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    /**
     * POST /api/products — Create a new product
     * 
     * Request body:
     * {
     *   "name": "New Microscope",
     *   "description": "A great microscope",
     *   "categoryId": 1,
     *   "manufacturerId": 3
     * }
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest request) {
        ProductDTO created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/products/{id} — Update an existing product */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
                                                    @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    /** DELETE /api/products/{id} — Soft-delete a product */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
