/**
 * LabDistrib — API Service
 * 
 * This file will handle all communication between the frontend and the 
 * Spring Boot backend. Currently, it contains only the planned structure.
 * 
 * STATUS: NOT YET IMPLEMENTED
 * This file will be implemented in Stage 6 when we connect the frontend 
 * to the backend REST API.
 * 
 * How it will work:
 * 1. The frontend JavaScript (e.g., catalog.js) will call functions in this file
 * 2. This file will use the Fetch API to send HTTP requests to the backend
 * 3. The backend (Spring Boot) will process the request and return JSON
 * 4. This file will return the JSON data to the calling function
 * 5. The calling function will update the HTML to display the data
 */

'use strict';

// =============================================================================
// API Configuration
// =============================================================================

/**
 * Base URL for the backend API.
 * In development, the Spring Boot server will run on localhost:8080.
 * In production, this would be your actual server domain.
 */
const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Default headers sent with every API request.
 * 'Content-Type: application/json' tells the server we're sending JSON data.
 * The Authorization header will be added after login (Stage 7).
 */
const DEFAULT_HEADERS = {
    'Content-Type': 'application/json'
};

// =============================================================================
// Core API Function (will be implemented in Stage 6)
// =============================================================================

/**
 * Makes an HTTP request to the backend API.
 * This is the core function that all other API functions will use.
 * 
 * @param {string} endpoint - The API endpoint (e.g., '/products')
 * @param {string} method - HTTP method ('GET', 'POST', 'PUT', 'DELETE')
 * @param {object} body - Request body for POST/PUT requests (optional)
 * @returns {Promise<object>} The JSON response from the server
 * 
 * Example usage (will work in Stage 6):
 *   const products = await apiRequest('/products', 'GET');
 *   const newProduct = await apiRequest('/products', 'POST', productData);
 */
async function apiRequest(endpoint, method, body) {
    // TODO: Implement in Stage 6
    console.warn('API not yet connected. Endpoint:', method, API_BASE_URL + endpoint);
    return null;
}

// =============================================================================
// Product API Functions (will be implemented in Stage 6)
// =============================================================================

// async function getAllProducts() { }
// async function getProductById(id) { }
// async function getProductsByCategory(categoryId) { }
// async function searchProducts(query) { }
// async function createProduct(productData) { }     // BUSINESS_ADMIN only
// async function updateProduct(id, productData) { }  // BUSINESS_ADMIN only
// async function deleteProduct(id) { }               // BUSINESS_ADMIN only

// =============================================================================
// Category API Functions (will be implemented in Stage 6)
// =============================================================================

// async function getAllCategories() { }
// async function getCategoryById(id) { }
// async function createCategory(categoryData) { }    // BUSINESS_ADMIN only
// async function updateCategory(id, categoryData) { } // BUSINESS_ADMIN only
// async function deleteCategory(id) { }              // BUSINESS_ADMIN only

// =============================================================================
// Manufacturer API Functions (will be implemented in Stage 6)
// =============================================================================

// async function getAllManufacturers() { }
// async function getManufacturerById(id) { }
// async function createManufacturer(data) { }    // BUSINESS_ADMIN only
// async function updateManufacturer(id, data) { } // BUSINESS_ADMIN only
// async function deleteManufacturer(id) { }      // BUSINESS_ADMIN only

// =============================================================================
// Enquiry API Functions (will be implemented in Stage 9)
// =============================================================================

// async function submitEnquiry(enquiryData) { }   // Public
// async function getAllEnquiries() { }            // BUSINESS_ADMIN only
// async function getEnquiryById(id) { }          // BUSINESS_ADMIN only
// async function updateEnquiryStatus(id, status) { } // BUSINESS_ADMIN only

// =============================================================================
// Auth API Functions (will be implemented in Stage 7)
// =============================================================================

// async function login(email, password) { }
// async function logout() { }
// function getAuthToken() { }  // Get stored JWT token
// function isLoggedIn() { }    // Check if user is authenticated
// function getUserRole() { }   // Get current user's role

console.log('LabDistrib api.js loaded. API base URL:', API_BASE_URL);
console.log('Note: API endpoints are not yet implemented. They will be connected in Stage 6.');
