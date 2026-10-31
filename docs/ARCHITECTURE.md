# System Architecture — C.K.Traders (LabDistrib)

**Project Name:** C.K.Traders — Laboratory & Pathology Equipment Distribution Management System  
**System Code:** `labdistrib`  
**Document Status:** Stage 1 — Architectural Planning (Documentation Only — No Implementation Yet)  
**Target Audience:** Developer / Student learning Spring Boot & Hibernate

---

## 1. Overview

**C.K.Traders (`labdistrib`)** is a web-based distribution and enquiry management system designed for a family business distributing laboratory equipment, pathology machinery, diagnostic kits, and medical consumables.

The application follows a classic **Layered Architecture** (Multi-Tier Architecture). In this architecture, software components are organized into horizontal layers where each layer has a single, well-defined responsibility and interacts only with adjacent layers.

```
+--------------------------------------------------------------------+
|  Frontend Client Layer (HTML5 / CSS3 / Vanilla JavaScript)         |
+--------------------------------------------------------------------+
                                 |
                                 | REST API calls (HTTP / JSON)
                                 v
+--------------------------------------------------------------------+
|  Backend Server Layer (Java 17 / Spring Boot Framework)            |
|                                                                    |
|  [Controller Layer]  -->  [Service Layer]  -->  [Repository Layer] |
|                                                       |            |
|                                                       v            |
|                                              [Hibernate / JPA]     |
+--------------------------------------------------------------------+
                                 |
                                 | SQL Queries / JDBC
                                 v
+--------------------------------------------------------------------+
|  Database Persistence Layer (MySQL Relational Database)            |
+--------------------------------------------------------------------+
```

### Core Tenets of the LabDistrib Architecture:
1. **Separation of Presentation & Business Logic:** The user interface (HTML/CSS/JS) lives entirely separately from backend business rules and database code.
2. **RESTful HTTP Communication:** The frontend communicates with the backend via stateless REST APIs exchanging JSON payloads.
3. **Controller → Service → Repository Pattern:** Within the backend, requests enter via Controllers, execute business rules in Services, and fetch or persist data via Repositories.
4. **Relational Persistence via ORM:** MySQL stores all transactional and catalog data; Hibernate/JPA transparently maps relational database tables to Java objects.
5. **No Public Pricing:** Because medical and laboratory equipment involves fluctuating manufacturer costs and bulk order terms, products display specifications without fixed retail prices. Customers submit quotation enquiries.
6. **Role-Based Access Control:** Three user roles exist:
   - **`SUPER_ADMIN`**: Developer account with full system configuration and diagnostic access.
   - **`BUSINESS_ADMIN`**: Business owner (father) managing catalog, updating stock availability, reviewing customer enquiries, and sending quotes.
   - **`CUSTOMER`**: Pathology labs, diagnostic centers, and clinics browsing the catalog and submitting equipment enquiries.

> [!NOTE]
> **Stage 1 Status:** Backend components described in this document are **NOT YET IMPLEMENTED**. Stage 1 focuses strictly on architectural blueprints, requirements analysis, database schema modeling, and documentation.

---

## 2. Architecture Diagram

Below is the standard end-to-end architectural flow diagram for LabDistrib:

```
Frontend (HTML / CSS / JavaScript)
         │
         ▼
    REST API (HTTP / JSON)
         │
         ▼
    Controller Layer (Spring @RestController)
         │
         ▼
    Service Layer (Spring @Service - Business Logic)
         │
         ▼
    Repository Layer (Spring Data JPA @Repository)
         │
         ▼
    Hibernate / JPA (Object-Relational Mapping Engine)
         │
         ▼
    MySQL Database (Relational Tables & Constraints)
```

---

## 3. Layer Explanations (Beginner-Friendly Java Terminology)

If you already know core Java (classes, methods, variables, `ArrayList`, and `Scanner`), the Spring Boot layered architecture is just a clean way of organizing those exact same concepts for the web.

Here is a breakdown of every component in the system:

---

### 3.1 Frontend (HTML / CSS / JavaScript)

* **What it is:** The visual interface running directly inside the client's web browser (Chrome, Firefox, Edge).
* **What it does:**
  - **HTML (HyperText Markup Language):** Creates the skeleton and structure of the page (buttons, search inputs, product tables, forms).
  - **CSS (Cascading Style Sheets):** Makes the page look professional with medical/corporate styling (colors, layout grids, cards, typography, responsiveness on mobile).
  - **JavaScript (Vanilla JS):** Gives the page dynamic behavior. It captures button clicks, sends background HTTP requests (using `fetch()`), parses responses, and dynamically updates the HTML page without requiring full browser reloads.
* **Java Analogy:**
  - In a desktop Java program, think of this like a **Swing or JavaFX GUI** with `JFrame`, `JButton`, and `JTextField`. It is the visual face that users interact with.

---

### 3.2 REST API (Representational State Transfer API)

* **What it is:** The standardized communication bridge between the frontend and the backend.
* **What it does:** Allows two separate programs (the browser running JavaScript and the server running Java) to talk over standard internet protocols (HTTP). Data is exchanged in **JSON** (JavaScript Object Notation), which looks like a lightweight text map of key-value pairs (e.g., `{"name": "Centrifuge Machine", "category": "Pathology"}`).
* **HTTP Verbs Used:**
  - `GET`: Retrieve data (e.g., fetch list of pathology microscopes).
  - `POST`: Submit new data (e.g., submit a new customer quotation enquiry).
  - `PUT`: Update existing data (e.g., update a product's description or specifications).
  - `DELETE`: Remove data (e.g., remove a discontinued test tube model).
* **Real-World / Java Analogy:**
  - **Restaurant Analogy:** The REST API is the **waiter**. The customer (frontend) looks at the menu and tells the waiter what they want. The waiter takes the request to the kitchen (backend) and brings back the cooked dish (JSON data).
  - **Java Analogy:** Think of a REST API like calling a `public` method over the network, where the URL is the method name and the HTTP body holds method parameters.

---

### 3.3 Controller Layer (`@RestController`)

* **What it is:** The entry point and traffic director on the backend server for all incoming web requests.
* **What it does:**
  - Listens to specific URL routes (e.g., `/api/products` or `/api/enquiries`).
  - Reads request parameters, headers, and incoming JSON bodies.
  - Performs basic validation (e.g., checking if the incoming request is properly formatted).
  - **Delegates** the real work to the appropriate Service class.
  - Packages the resulting Java objects into an HTTP Response with status codes (e.g., `200 OK`, `404 Not Found`, `400 Bad Request`).
  - **Important Rule:** Controllers should be "thin". They contain **no business calculations** or database queries.
* **Java Analogy:**
  - Think of a Controller like the **`main()` method** or an input-dispatcher menu loop in a console application:
    ```java
    // Console Analogy:
    int choice = scanner.nextInt();
    if (choice == 1) {
        productService.displayProducts(); // Controller just delegates!
    }
    ```
  - It acts like a **hotel receptionist**: they welcome you and direct you to the right department, but they don't clean your room or cook your meal themselves.

---

### 3.4 Service Layer (`@Service`)

* **What it is:** The core engine containing all business rules, decisions, and logic of the application.
* **What it does:**
  - Implements application business rules.
  - Examples in C.K.Traders:
    - *"A customer enquiry cannot be submitted without a valid contact number and at least one item requested."*
    - *"When a product is marked discontinued, archive its pending quotes or flag them for admin review."*
    - *"Only users with role `BUSINESS_ADMIN` or `SUPER_ADMIN` may create or edit product specifications."*
  - Manages database transactions (ensuring multi-step operations complete together or roll back safely if an error happens).
  - Coordinates between multiple repositories and utility services (such as sending notification emails or logging audits).
* **Java Analogy:**
  - This is where all your core **Java methods with `if/else`, loops, validation, and domain algorithms** live. Think of a class like `BankManager` or `OrderProcessor` containing `calculateTax()`, `verifyAccount()`, and `transferFunds()`.

---

### 3.5 Repository Layer (`@Repository` / Spring Data JPA)

* **What it is:** The data access component responsible for loading and saving information to and from the database.
* **What it does:**
  - Executes database operations: `save()`, `findById()`, `findAll()`, `deleteById()`, and custom finder methods like `findByCategory_Name(String categoryName)`.
  - Shields the rest of the application from raw SQL syntax and database dialect differences.
  - Uses Spring Data JPA interfaces where Spring automatically creates the working implementation at runtime based on method names.
* **Java Analogy:**
  - In traditional plain Java, you would write tedious JDBC code:
    ```java
    Connection conn = DriverManager.getConnection(...);
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE id = ?");
    ps.setLong(1, id);
    ResultSet rs = ps.executeQuery();
    // Then manually copy each column into object fields...
    ```
  - A Repository replaces 20 lines of repetitive boilerplate code with one line:
    ```java
    Product product = productRepository.findById(productId);
    ```

---

### 3.6 Entity (`@Entity`)

* **What it is:** A standard Java class that represents a specific database table.
* **What it does:**
  - Each **class** maps to a database **table** (e.g., `Product` class maps to `products` table).
  - Each **field** inside the class maps to a **column** (e.g., `private String modelNumber;` maps to `model_number` column).
  - Each **instantiated object** of this class represents **one row** in that database table.
  - Uses annotations like `@Id`, `@GeneratedValue`, `@Column`, `@ManyToOne` to describe relationships.
* **Java Analogy:**
  - A standard **POJO** (Plain Old Java Object) containing private fields, a default constructor, and getters/setters:
    ```java
    public class Product {
        private Long id;
        private String name;
        private String modelNumber;
        // getters and setters...
    }
    ```

---

### 3.7 DTO (Data Transfer Object)

* **What it is:** A lightweight Java object specifically tailored for sending data between the frontend and the backend over the network.
* **What it does:**
  - Shields sensitive internal database fields from being exposed to public users (for example, password hashes, internal timestamps, or private supplier purchase costs).
  - Combines or reshapes data from multiple database tables into a single convenient object for the frontend to render.
  - Prevents accidental recursive loops when serializing relationships to JSON.
* **Java Analogy:**
  - Think of an Entity as the **complete company employee file** (including medical records, salary, and home address), while a DTO is the **public business card** or badge (showing only name, title, and work email).

---

### 3.8 Hibernate / JPA (Java Persistence API)

* **What it is:** The Object-Relational Mapping (ORM) bridge connecting object-oriented Java to table-oriented SQL databases.
* **What it does:**
  - **JPA** is the official Java standard specification (set of interfaces).
  - **Hibernate** is the battle-tested library that actually implements JPA.
  - Automatically translates Java object operations (`repository.save(enquiry)`) into proper SQL statements (`INSERT INTO enquiries (...) VALUES (...)`).
  - Converts incoming SQL rows (`ResultSet`) back into hydrated Java objects automatically.
* **Java Analogy:**
  - An **automated bilingual translator**. You speak Java; MySQL speaks SQL. Hibernate listens to your Java commands and translates them into flawless SQL behind the scenes.

---

### 3.9 MySQL Database

* **What it is:** The relational database management system (RDBMS) running as a server daemon.
* **What it does:**
  - Permanently stores all critical business data on disk.
  - Ensures data integrity through primary keys, foreign key relationships, unique constraints, and ACID transactions.
  - Stores products, categories, manufacturers, customer records, quotation enquiries, and role permissions.
* **Java Analogy:**
  - In early beginner projects, you might save records into a text file (`products.txt`) or keep them in an in-memory `ArrayList<Product>`. But when the program stops, `ArrayList` data is lost! MySQL is the permanent, highly organized, indexed storage box that survives computer reboots and server restarts.

---

## 4. Why Don't We Put Everything in One Java Class?

When learning Java, students often start with a single class (like `Main.java`) that does everything: prompts the user, calculates results, prints to console, and writes to a text file. While fine for 50-line homework assignments, doing this in a real business application leads to catastrophic failure.

### The Restaurant Analogy

Imagine a restaurant operated entirely by a single person:
- They stand at the entrance to seat guests.
- They run to the table to write down food orders.
- They run into the kitchen to chop vegetables and cook dishes.
- They bring the food to the table.
- They run back to wash pots, pans, and plates.
- They handle the cash register and do the accounting books.

**What happens?**
1. **Bottleneck & Delay:** Guests wait hours just to place an order.
2. **Mistakes:** The cook burns the food because they were interrupted to seat a new guest.
3. **Cross-Contamination:** Dirty dishwater gets on the clean plates.
4. **Single Point of Failure:** If that one person gets sick, the entire restaurant shuts down completely.

Instead, a real restaurant separates roles:
- **Host / Waiter** (Controller): Interacts with customers, takes orders, delivers food.
- **Chef** (Service): Focuses exclusively on cooking food properly according to recipes.
- **Pantry Manager** (Repository): Fetches raw ingredients from storage shelves.
- **Walk-in Cooler / Shelves** (Database): Safely stores ingredients.

---

### The Monolithic Single-Class Trap

If all code for LabDistrib were placed in one giant class (e.g., `LabDistribGodClass.java`):

| Problem | Explanation |
| :--- | :--- |
| **Hard to Read** | A single file with 5,000+ lines of mixed HTML generators, SQL strings, and validation rules is intimidating and exhausting to navigate. |
| **Hard to Debug** | When an enquiry fails to save, is the bug in the HTTP header parsing, the email format check, or the database query? You have to search through one massive spaghetti file. |
| **Hard to Change** | Modifying a database column name might break your UI layout code because the variables are tangled together. |
| **Hard to Test** | You cannot test whether a discount calculation or contact validation works without spinning up a live database and sending real HTTP requests. |
| **Hard to Collaborate** | If two developers edit the same giant file at the same time, Git merge conflicts become a nightmare. |

### The Solution: Separation of Concerns (SoC)

By splitting the application into distinct layers:
- **Controller** only cares about HTTP requests and responses.
- **Service** only cares about business rules.
- **Repository** only cares about database persistence.
- **Entity** only cares about data structure.

Each class is small, focused, readable, testable, and maintainable. This fundamental software engineering principle is called **Separation of Concerns**.

---

## 5. How Data Flows: End-to-End Example

To understand how these layers cooperate in practice, let us trace a concrete scenario:  
**"A customer visits the catalog page to view all available laboratory centrifuges and microscopes."**

```
 [Browser: products.html]
         │
         │  1. User navigates to page
         ▼
 [Browser JS: products.js]
         │
         │  2. fetch("GET /api/products")
         ▼
 [Backend: ProductController]  <-- (NOT YET IMPLEMENTED)
         │
         │  3. calls productService.getAllProducts()
         ▼
 [Backend: ProductService]     <-- (NOT YET IMPLEMENTED)
         │
         │  4. calls productRepository.findAll()
         ▼
 [Backend: ProductRepository]  <-- (NOT YET IMPLEMENTED)
         │
         │  5. Delegates query to Hibernate
         ▼
 [ORM Engine: Hibernate / JPA]
         │
         │  6. Generates: SELECT * FROM products WHERE is_active = true;
         ▼
 [Database: MySQL]
         │
         │  7. Returns table rows
         ▼
 [ORM Engine: Hibernate / JPA]
         │
         │  8. Hydrates rows into List<Product> entity objects
         ▼
 [Backend: ProductService]
         │
         │  9. Maps List<Product> to List<ProductResponseDTO>
         ▼
 [Backend: ProductController]
         │
         │ 10. Wraps DTOs into ResponseEntity.ok(dtos)
         ▼
 [HTTP Network]
         │
         │ 11. Returns 200 OK with JSON payload
         ▼
 [Browser JS: products.js]
         │
         │ 12. Receives JSON array, creates HTML product cards
         ▼
 [Browser Screen]
           13. Customer sees equipment catalog rendered
```

### Detailed Step-by-Step Breakdown:

1. **User Action:** The customer clicks on the "Products" link, loading `products.html` in their web browser.
2. **Client Request:** `products.js` runs automatically on page load and fires an asynchronous HTTP request:
   `fetch('/api/products', { method: 'GET' })`.
3. **Controller Receipt:** Spring Boot routes the request to `ProductController.getAllProducts()`. The controller checks request headers and asks the service layer for data. *(Status: NOT YET IMPLEMENTED)*
4. **Business Logic Evaluation:** `ProductService.getAllProducts()` applies business filters (e.g., ensuring only active, published products are retrieved, not internal draft items). *(Status: NOT YET IMPLEMENTED)*
5. **Data Request:** `ProductService` calls `ProductRepository.findAllActive()`. *(Status: NOT YET IMPLEMENTED)*
6. **SQL Generation:** Hibernate detects the call and translates it into an optimized SQL statement:
   ```sql
   SELECT p.id, p.name, p.model_number, p.specifications, c.name AS category_name, m.name AS manufacturer_name
   FROM products p
   JOIN categories c ON p.category_id = c.id
   JOIN manufacturers m ON p.manufacturer_id = m.id
   WHERE p.is_active = 1;
   ```
7. **Database Execution:** MySQL executes the query on disk/cache and returns raw tabular data.
8. **Entity Hydration:** Hibernate converts the raw SQL result set rows into instances of the `Product` entity class (`List<Product>`).
9. **DTO Conversion:** `ProductService` converts the `Product` entities into clean `ProductResponseDTO` objects (stripping any internal administration notes or supplier details).
10. **Controller Response:** `ProductController` returns the list with HTTP status `200 OK`.
11. **Network Transfer:** Spring Boot's Jackson library converts the Java DTO list into a JSON string and sends it across HTTP to the client browser.
12. **DOM Update:** The JavaScript `fetch()` promise resolves. `products.js` loops through the JSON array and inserts HTML elements (product cards showing images, titles, specifications, and an *"Add to Enquiry"* button).
13. **Result:** The customer sees the updated, fully styled catalog instantly.

---

## 6. Planned Backend Package Structure

The backend will be structured using standard Maven conventions under root package `com.labdistrib`.

```
com.labdistrib/
├── controller/                 # Receives HTTP requests, exposes REST endpoints
│   ├── AuthController.java     # User login, session/token management
│   ├── ProductController.java  # Catalog browsing & management endpoints
│   ├── CategoryController.java # Product categories (Pathology, Lab, Consumables)
│   ├── ManufacturerController.java # Equipment manufacturers/brands
│   └── EnquiryController.java  # Customer quote requests & admin responses
│
├── service/                    # Business logic and transaction boundaries
│   ├── AuthService.java        # Authentication logic and role validation
│   ├── ProductService.java     # Product validation, search & filter algorithms
│   ├── CategoryService.java    # Category hierarchy and dependency checks
│   ├── ManufacturerService.java# Manufacturer catalog associations
│   └── EnquiryService.java     # Enquiry processing, reference numbering, status workflow
│
├── repository/                 # Database abstraction interfaces (Spring Data JPA)
│   ├── UserRepository.java     # DB queries for user accounts and roles
│   ├── ProductRepository.java  # DB queries for products, filtering by brand/category
│   ├── CategoryRepository.java # DB queries for categories
│   ├── ManufacturerRepository.java # DB queries for equipment manufacturers
│   └── EnquiryRepository.java  # DB queries for customer enquiries
│
├── entity/                     # JPA entity classes mapping directly to MySQL tables
│   ├── User.java               # users table (SUPER_ADMIN, BUSINESS_ADMIN, CUSTOMER)
│   ├── Role.java               # Enum or table defining system permissions
│   ├── Product.java            # products table (model, specifications, catalog status)
│   ├── Category.java           # categories table (Pathology Equipment, Consumables, etc.)
│   ├── Manufacturer.java       # manufacturers table (Roche, Sysmex, Abbott, etc.)
│   ├── Enquiry.java            # enquiries table (customer contact, status, timestamps)
│   └── EnquiryItem.java        # enquiry_items table (products requested in an enquiry)
│
├── dto/                        # Data Transfer Objects for API request and response payloads
│   ├── request/                # Inbound payloads from frontend
│   │   ├── LoginRequest.java
│   │   ├── ProductCreateRequest.java
│   │   └── EnquirySubmissionRequest.java
│   └── response/               # Outbound payloads sent to frontend
│       ├── AuthResponse.java
│       ├── ProductResponseDTO.java
│       ├── EnquirySummaryDTO.java
│       └── ApiResponse.java    # Standard envelope for status, message, and timestamp
│
├── security/                   # Spring Security & Authorization configuration
│   ├── SecurityConfig.java     # Route protection rules and CORS configuration
│   ├── CustomUserDetailsService.java # Bridges database user records to Spring Security
│   └── JwtAuthenticationFilter.java  # Validates request security tokens
│
├── exception/                  # Global error handling and custom domain exceptions
│   ├── GlobalExceptionHandler.java  # @ControllerAdvice converting errors to JSON
│   ├── ResourceNotFoundException.java # Thrown when an ID does not exist in DB
│   └── BadRequestException.java     # Thrown when business validation fails
│
├── config/                     # System configuration classes
│   ├── CorsConfig.java         # Allows frontend JS to call backend endpoints
│   └── SwaggerConfig.java      # Optional API documentation configuration
│
└── LabDistribApplication.java  # Main application entry point (@SpringBootApplication)
```

> [!IMPORTANT]
> **Implementation Note:** All classes listed in the package tree above are **planned specifications for future stages**. None of these Java classes are implemented in Stage 1.

---

## 7. Planned Frontend Structure

The frontend will be organized as static web assets served cleanly to browsers, separated into public-facing pages and protected administration pages.

```
frontend/
├── index.html                  # Landing homepage (Hero banner, featured equipment, about C.K.Traders)
├── products.html               # Public catalog page (Search, category filters, product cards)
├── product-details.html        # Individual product view (Detailed technical specs, enquiry drawer)
├── categories.html             # Directory of equipment categories & diagnostic domains
├── manufacturers.html          # Directory of brand partners (Sysmex, Roche, Olympus, etc.)
├── contact.html                # Business address, phone numbers, location map, quick contact
├── login.html                  # Authentication page for Admin and returning customers
│
├── admin/                      # Protected Management Portal (BUSINESS_ADMIN & SUPER_ADMIN)
│   ├── dashboard.html          # Overview: recent enquiries, product counts, system alerts
│   ├── products.html           # Admin CRUD table: Add new product, edit specs, toggle visibility
│   ├── categories.html         # Category manager: Add/edit equipment groups
│   ├── manufacturers.html      # Manufacturer manager: Add/edit brand profiles
│   └── enquiries.html          # Enquiry queue: View customer requests, mark responded/closed
│
├── css/                        # Stylesheets
│   ├── main.css                # Global typography, color palette, responsive navbar & footer
│   ├── catalog.css             # Product grid, filters, cards, and technical spec table styling
│   └── admin.css               # Admin dashboard layout, side navigation, data tables, modal popups
│
└── js/                         # Client-side JavaScript
    ├── api.js                  # Centralized fetch wrapper with base URL and error handling
    ├── auth.js                 # Session tracking, token handling, login/logout functions
    ├── catalog.js              # Fetching and rendering products, dynamic search and category filtering
    ├── enquiry.js              # Handling customer cart/enquiry submission form
    └── admin.js                # Admin portal table manipulation, form submissions, and status changes
```

### Purpose of Key Frontend Modules:

* **Public Catalog (`products.html`, `product-details.html`):**  
  Allows pathology labs, doctors, and hospitals to easily browse laboratory machinery (e.g., hematology analyzers, centrifuges, urine analyzers, biochemistry reagents) and view detailed technical specifications without being gated by pricing walls.
* **Enquiry Workflow (`enquiry.js`, `enquiries.html`):**  
  Because laboratory systems vary widely by installation requirement and volume, customers bundle desired machines into an "Enquiry List" and submit their institution details. The business owner reviews these enquiries in the admin portal and prepares formal quotations.
* **Admin Portal (`admin/*`):**  
  Provides a clean, non-technical dashboard for the business owner (father) to manage equipment records, upload specification sheets, and mark enquiries as answered without touching database code.
* **Developer Gateway (`SUPER_ADMIN`):**  
  Provides diagnostic visibility and system health monitoring for the developer student.

---

## 8. Summary of Architectural Principles

| Principle | How LabDistrib Applies It |
| :--- | :--- |
| **Loose Coupling** | The frontend does not care whether the backend is written in Java, Python, or Go; it only expects standardized JSON via REST. |
| **High Cohesion** | Each class has one job: Controllers handle HTTP, Services handle rules, Repositories handle database queries. |
| **Data Protection** | DTOs guarantee that internal database fields, security hashes, and private dealer costs are never sent to the browser. |
| **Maintainability** | Clear package separation allows a student developer to easily locate and enhance any feature without breaking unrelated modules. |
