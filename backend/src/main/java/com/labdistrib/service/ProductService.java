package com.labdistrib.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.labdistrib.dto.CreateProductRequest;
import com.labdistrib.dto.ProductDTO;
import com.labdistrib.entity.Category;
import com.labdistrib.entity.Manufacturer;
import com.labdistrib.entity.Product;
import com.labdistrib.exception.ResourceNotFoundException;
import com.labdistrib.repository.CategoryRepository;
import com.labdistrib.repository.ManufacturerRepository;
import com.labdistrib.repository.ProductRepository;

/**
 * ProductService — Business Logic for Products
 * 
 * This service is more complex because Products reference both
 * Category and Manufacturer. When creating a product, we need to:
 * 1. Look up the Category by ID
 * 2. Look up the Manufacturer by ID
 * 3. Create the Product with those references
 * 
 * Notice this service depends on THREE repositories — Spring injects all of them.
 */
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * Get all active products.
     */
    public List<ProductDTO> getAllProducts() {
        log.debug("Fetching all active products");
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a single product by ID.
     */
    public ProductDTO getProductById(Long id) {
        log.debug("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return toDTO(product);
    }

    /**
     * Get all active products in a specific category.
     */
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        log.debug("Fetching products for category id: {}", categoryId);
        // Verify category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category", categoryId);
        }
        return productRepository.findByActiveTrueAndCategoryId(categoryId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all active products from a specific manufacturer.
     */
    public List<ProductDTO> getProductsByManufacturer(Long manufacturerId) {
        log.debug("Fetching products for manufacturer id: {}", manufacturerId);
        if (!manufacturerRepository.existsById(manufacturerId)) {
            throw new ResourceNotFoundException("Manufacturer", manufacturerId);
        }
        return productRepository.findByActiveTrueAndManufacturerId(manufacturerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search products by name keyword.
     */
    public List<ProductDTO> searchProducts(String keyword) {
        log.debug("Searching products with keyword: {}", keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new product.
     * 
     * The request contains categoryId and manufacturerId (numbers).
     * We look up the actual Category and Manufacturer objects from the database,
     * then set them on the Product. Hibernate handles the foreign keys.
     */
    public ProductDTO createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (request.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }
        if (request.getManufacturerId() == null) {
            throw new IllegalArgumentException("Manufacturer ID is required");
        }

        // Look up the referenced entities
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", request.getCategoryId()));
        Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", request.getManufacturerId()));

        Product product = new Product();
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setSpecifications(request.getSpecifications());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        product.setManufacturer(manufacturer);

        Product saved = productRepository.save(product);
        log.info("Created product with id: {}", saved.getId());
        return toDTO(saved);
    }

    /**
     * Update an existing product.
     */
    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        log.info("Updating product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            product.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getSpecifications() != null) {
            product.setSpecifications(request.getSpecifications());
        }
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", request.getCategoryId()));
            product.setCategory(category);
        }
        if (request.getManufacturerId() != null) {
            Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", request.getManufacturerId()));
            product.setManufacturer(manufacturer);
        }

        Product saved = productRepository.save(product);
        log.info("Updated product with id: {}", saved.getId());
        return toDTO(saved);
    }

    /**
     * Soft-delete a product.
     */
    public void deleteProduct(Long id) {
        log.info("Soft-deleting product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        product.setActive(false);
        productRepository.save(product);
        log.info("Soft-deleted product with id: {}", id);
    }

    // =========================================================================
    // Helper: Convert Entity → DTO
    // =========================================================================

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setSpecifications(product.getSpecifications());
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.isActive());

        // Extract just the name and ID from the related entities
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getManufacturer() != null) {
            dto.setManufacturerId(product.getManufacturer().getId());
            dto.setManufacturerName(product.getManufacturer().getName());
        }

        return dto;
    }
}
