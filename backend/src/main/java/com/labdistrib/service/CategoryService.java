package com.labdistrib.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.labdistrib.dto.CategoryDTO;
import com.labdistrib.dto.CreateCategoryRequest;
import com.labdistrib.entity.Category;
import com.labdistrib.exception.ResourceNotFoundException;
import com.labdistrib.repository.CategoryRepository;

/**
 * CategoryService — Business Logic for Categories
 * 
 * WHAT IS @Service?
 * It tells Spring: "This class contains business logic. Create one instance
 * and make it available for Dependency Injection."
 * 
 * WHY A SEPARATE SERVICE LAYER?
 * The Controller should only handle HTTP (receive request, send response).
 * The Service handles the "what should happen" logic:
 *   - Convert Entity to DTO
 *   - Validate business rules
 *   - Coordinate between repositories
 * 
 * CONSTRUCTOR INJECTION:
 * Notice the constructor takes CategoryRepository as a parameter.
 * Spring automatically provides it — this is Dependency Injection.
 * We use 'final' to ensure the repository can't be changed after creation.
 */
@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    /**
     * Constructor injection — Spring provides the CategoryRepository automatically.
     * 
     * In plain Java, you'd write: new CategoryService(new CategoryRepository(...))
     * With Spring, you just declare what you need and Spring provides it.
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get all active categories.
     */
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetching all active categories");
        return categoryRepository.findByActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a single category by ID.
     * Throws ResourceNotFoundException if not found → results in HTTP 404.
     */
    public CategoryDTO getCategoryById(Long id) {
        log.debug("Fetching category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        return toDTO(category);
    }

    /**
     * Create a new category.
     */
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        log.info("Creating category: {}", request.getName());

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }

        Category category = new Category();
        category.setName(request.getName().trim());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        Category saved = categoryRepository.save(category);
        log.info("Created category with id: {}", saved.getId());
        return toDTO(saved);
    }

    /**
     * Update an existing category.
     */
    public CategoryDTO updateCategory(Long id, CreateCategoryRequest request) {
        log.info("Updating category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            category.setName(request.getName().trim());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            category.setImageUrl(request.getImageUrl());
        }

        Category saved = categoryRepository.save(category);
        log.info("Updated category with id: {}", saved.getId());
        return toDTO(saved);
    }

    /**
     * Soft-delete a category (set active = false).
     * 
     * WHY SOFT DELETE?
     * If we actually DELETE the category from the database, any products
     * referencing it would have a broken foreign key. Instead, we just
     * hide it by setting active = false.
     */
    public void deleteCategory(Long id) {
        log.info("Soft-deleting category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));

        category.setActive(false);
        categoryRepository.save(category);
        log.info("Soft-deleted category with id: {}", id);
    }

    // =========================================================================
    // Helper: Convert Entity → DTO
    // =========================================================================

    /**
     * Converts a Category entity to a CategoryDTO.
     * This is where we control exactly what data gets sent to the client.
     */
    private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setActive(category.isActive());
        dto.setProductCount(category.getProducts() != null ? category.getProducts().size() : 0);
        return dto;
    }
}
