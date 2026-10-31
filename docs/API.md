# C.K.Traders — REST API Specification (Planned)

> ⚠️ **ALL ENDPOINTS IN THIS DOCUMENT ARE PLANNED — NOT YET IMPLEMENTED**  
> This document describes the API design. Implementation will happen in later stages.

---

## 1. What is a REST API?

### Core Concepts

- **API (Application Programming Interface):** A standardized set of rules and protocols that allows different software applications to communicate with each other.
- **REST (Representational State Transfer):** An architectural style for networked systems that relies on stateless communication using standard **HTTP** protocols and methods.
- **Frontend & Backend Communication:** In **LabDistrib**, the client-side user interface (built with standard **HTML, CSS, and Vanilla JavaScript**) runs in the browser and makes asynchronous HTTP requests (using JavaScript's `fetch()` API) to the backend server (built with **Java Spring Boot**). The server queries the **MySQL database** and sends structured responses back to the browser.
- **Endpoints & Data Exchange:** Each endpoint is identified by a specific URL path (e.g., `/api/products`), an HTTP request method, and exchanges data formatted in **JSON (JavaScript Object Notation)**.

### Standard HTTP Methods

RESTful web services use standard HTTP verbs to express actions performed on resources:

| HTTP Verb | Operation | Description | LabDistrib Example |
|-----------|-----------|-------------|-------------------|
| **GET** | Read | Retrieve data or collections of resources without altering server state. | Retrieve the product catalog or single product details. |
| **POST** | Create | Submit new data to create a new resource on the server. | Submit a new customer enquiry or register a new product. |
| **PUT** | Update | Replace or update an existing resource with fresh data. | Update product information or change enquiry status. |
| **DELETE** | Remove | Delete an existing resource from the server. | Remove a discontinued product or category. |

### Parameters: Path Variables vs Query Parameters

1. **Path Variables (`/api/products/{id}`):** Used to identify a specific single resource. For example, `/api/products/42` targets the product with ID `42`.
2. **Query Parameters (`/api/products/search?query=microscope`):** Used to filter, sort, paginate, or search resources. The `?query=microscope` string supplies filtering criteria to the endpoint.

---

## 2. Planned Endpoints

Endpoints are grouped by business domain. Access levels correspond to the three system roles:
- **Public:** Accessible by any visitor or customer without logging in.
- **Authenticated:** Accessible by any user with an active session / valid authentication token.
- **BUSINESS_ADMIN:** Restricted to the business owner (father) for daily operations (managing catalog, manufacturers, viewing/updating customer enquiries, updating business contact info).
- **SUPER_ADMIN:** Restricted to the system administrator/developer for system-level controls (user accounts, role assignments, audit logs).

---

### 2.1 Products

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/products` | Get all active products | Public |
| `GET` | `/api/products/{id}` | Get product details by ID | Public |
| `GET` | `/api/products/category/{categoryId}` | Get products filtered by category ID | Public |
| `GET` | `/api/products/manufacturer/{manufacturerId}` | Get products filtered by manufacturer ID | Public |
| `GET` | `/api/products/search?query=keyword` | Search products by name, description, or code | Public |
| `POST` | `/api/products` | Create a new product entry | BUSINESS_ADMIN |
| `PUT` | `/api/products/{id}` | Update an existing product | BUSINESS_ADMIN |
| `DELETE` | `/api/products/{id}` | Delete / deactivate a product | BUSINESS_ADMIN |

---

### 2.2 Categories

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/categories` | Get all product categories | Public |
| `GET` | `/api/categories/{id}` | Get category details by ID | Public |
| `POST` | `/api/categories` | Create a new category | BUSINESS_ADMIN |
| `PUT` | `/api/categories/{id}` | Update an existing category | BUSINESS_ADMIN |
| `DELETE` | `/api/categories/{id}` | Delete a category | BUSINESS_ADMIN |

---

### 2.3 Manufacturers

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/manufacturers` | Get all partner manufacturers / brands | Public |
| `GET` | `/api/manufacturers/{id}` | Get manufacturer details by ID | Public |
| `POST` | `/api/manufacturers` | Create a new manufacturer | BUSINESS_ADMIN |
| `PUT` | `/api/manufacturers/{id}` | Update an existing manufacturer | BUSINESS_ADMIN |
| `DELETE` | `/api/manufacturers/{id}` | Delete a manufacturer | BUSINESS_ADMIN |

---

### 2.4 Enquiries

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/enquiries` | Submit a new customer enquiry with requested items | Public |
| `GET` | `/api/enquiries` | Get all customer enquiries (filterable by status) | BUSINESS_ADMIN |
| `GET` | `/api/enquiries/{id}` | Get complete enquiry details and line items | BUSINESS_ADMIN |
| `PUT` | `/api/enquiries/{id}/status` | Update enquiry status (e.g., NEW, CONTACTED, QUOTED, CLOSED) | BUSINESS_ADMIN |

---

### 2.5 Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/auth/login` | Authenticate user credentials and return JWT token | Public |
| `POST` | `/api/auth/register` | Register an account (if customer registration is enabled) | Conditional |
| `POST` | `/api/auth/logout` | Invalidate session or instruct client to clear token | Authenticated |

---

### 2.6 Users (Admin)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/users` | Get all system user accounts | SUPER_ADMIN |
| `GET` | `/api/users/{id}` | Get specific user account by ID | SUPER_ADMIN |
| `PUT` | `/api/users/{id}` | Update user details, roles, or status | SUPER_ADMIN |
| `DELETE` | `/api/users/{id}` | Deactivate or remove a user account | SUPER_ADMIN |

---

### 2.7 Business Profile

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/business-profile` | Get public business info (address, phone, email, GSTIN, hours) | Public |
| `PUT` | `/api/business-profile` | Update company contact details and institutional info | BUSINESS_ADMIN |

---

### 2.8 Audit Logs

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/audit-logs` | Retrieve system audit trail logs (logins, updates, deletions) | SUPER_ADMIN |

---

## 3. Example Request/Response (Planned)

### Product Listing Request

**Request:**
```http
GET /api/products HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Planned Response:**
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "Example Product",
      "description": "A sample product description",
      "category": "Laboratory Equipment",
      "manufacturer": "Example Manufacturer",
      "imageUrl": "/images/products/example.jpg",
      "active": true
    }
  ]
}
```

> **Important Business Rule — No Public Prices:**  
> Notice that there is **no `price` field** included in the response.  
> C.K.Traders operates on a B2B distribution model for laboratories, hospitals, diagnostic centers, and clinics. Pricing varies depending on purchase volumes, institutional contracts, government tenders, manufacturer terms, and transport/installation logistics. Instead of fixed public prices, prospective buyers submit formal enquiries via `/api/enquiries`, allowing the business administrator to issue custom quotations.

---

### Error Response Format

All endpoints will return standard structured error payloads when requests fail:

```json
{
  "status": "error",
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Product with ID 42 was not found",
    "timestamp": "2026-09-22T17:05:00Z"
  }
}
```

---

## 4. API Design Principles

1. **RESTful Conventions:**  
   - Resource-oriented URLs use plural nouns (e.g., `/api/products`, `/api/categories`).
   - Standard HTTP verbs define the action (GET to fetch, POST to create, PUT to update, DELETE to remove).
   - HTTP status codes signal outcomes (`200 OK`, `201 Created`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`).

2. **Standardized JSON Format:**  
   - All request bodies and response payloads use JSON formatting with UTF-8 encoding.
   - Headers include `Content-Type: application/json` and `Accept: application/json`.

3. **Role-Based Access Control & Security:**  
   - **Public Endpoints:** Browsing products, manufacturers, categories, viewing business profile, and submitting enquiries do not require authentication.
   - **Admin Endpoints:** Administrative endpoints (`BUSINESS_ADMIN` and `SUPER_ADMIN`) require a valid **JSON Web Token (JWT)** sent in the HTTP header:
     ```http
     Authorization: Bearer <jwt_token_string>
     ```
   - Requests lacking valid authentication will receive `401 Unauthorized`.
   - Requests with insufficient role permissions will receive `403 Forbidden`.

4. **Consistent Error Handling:**  
   - Error responses contain structured, readable messages to help the frontend display clear feedback to the user.

5. **Implementation in Stage 5:**  
   - In Stage 5, these endpoints will be implemented using Spring Boot's `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping` annotations alongside Spring Security.
