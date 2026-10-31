# C.K.Traders — Laboratory & Pathology Equipment Distribution Management System

> **Code Name:** `labdistrib`  
> **Type:** Academic Final-Year Project & Real-World Business Solution  
> **Status:** Stage 1 — Project Architecture & Documentation (Planning Only)

---

## 🔬 What is LabDistrib?

**LabDistrib** is a full-stack web application designed and built for **C.K.Traders**, a family-owned distribution enterprise specializing in medical laboratory equipment, pathology devices, diagnostic instruments, and healthcare consumables.

In the medical distribution industry, equipment prices fluctuate based on order volume, technical specifications, custom installation requirements, and vendor agreements. For this reason, **LabDistrib does not display public prices**. Instead, it operates on a structured **customer enquiry workflow**:
1. **Customers** explore the digital product catalogue, view technical specifications, and submit targeted enquiries regarding pricing, quantity, and availability.
2. **Business Administrators** review incoming enquiries from an administrative dashboard, prepare custom price quotes, and manage inventory and product details.

---

## 🎯 Why Does This Project Exist?

This project fulfills two key purposes:

1. **Academic & Learning Objective:**  
   Built as a **final-year computer science project** to master production-grade full-stack software development. It bridges the gap between Core Java fundamentals and enterprise web frameworks, specifically **Spring Boot**, **Spring Data JPA**, **Hibernate ORM**, **Spring Security**, and **MySQL**.
2. **Real-World Business Impact:**  
   Replaces manual, spreadsheet-based, and fragmented communication processes for a real family business with an organized, searchable, web-based digital catalogue and enquiry tracking system.

---

## 👥 Who Uses It? (User Roles)

The application enforces **Role-Based Access Control (RBAC)** across three distinct user roles:

| Role | Target User | Responsibilities & Access Permissions |
|:-----|:------------|:---------------------------------------|
| `SUPER_ADMIN` | Developer (Student) | Complete system administration, database maintenance, user role configuration, technical monitoring, and audit log inspection. |
| `BUSINESS_ADMIN` | Business Owner (Father) | Daily business operations: adding/updating products, categorizing equipment, managing manufacturer brands, reviewing customer enquiries, and updating enquiry statuses. |
| `CUSTOMER` | Labs, Clinics, Hospitals & Public Visitors | Browsing the dynamic product catalogue, filtering by category and manufacturer, viewing product details and datasheets, and submitting enquiries for price quotes. |

---

## 🚀 Main Planned Features

- **Dynamic Product Catalogue:** Fast, responsive product listings with filtering by category, manufacturer, and keyword search.
- **Category Management:** Hierarchical organization of products into laboratory instruments, pathology analyzers, reagents, and consumables.
- **Manufacturer Management:** Dedicated brand profiles and manufacturer tracking for certified medical equipment vendors.
- **Customer Enquiry System:** Direct quotation request pipeline without public price tags, capturing contact information, item interest, requested quantity, and notes.
- **Role-Based Access Control (RBAC):** Secure authentication and endpoint protection ensuring customers only access public pages while admin panels remain strictly protected.
- **Admin Dashboard:** Central management console for business analytics, pending enquiry alerts, and inventory overview.
- **Audit Logging:** Traceability of administrative actions (e.g., product updates, category changes, and enquiry review status).

---

## 🛠️ Technology Stack

| Layer | Technologies | Purpose |
|:------|:-------------|:--------|
| **Frontend** | **HTML5, CSS3, Vanilla JavaScript (ES6+)** | Semantic markup, responsive mobile-friendly styling, and clean asynchronous `fetch()` API calls without heavy framework overhead. |
| **Backend** | **Java (17+), Spring Boot 3.x** | High-performance enterprise REST API runtime. |
| **Web Framework** | **Spring Web (Spring MVC)** | RESTful controller endpoints and request/response serialization. |
| **Security** | **Spring Security** | Authentication, authorization filters, BCrypt password hashing, and role checks. |
| **ORM / Data Access** | **Spring Data JPA, Hibernate** | Object-Relational Mapping (ORM) bridging Java entity classes with relational database tables. |
| **Database** | **MySQL 8.x** | Relational data persistence with strict foreign key constraints. |
| **Build & Tooling** | **Apache Maven** | Dependency management, compilation, and automated packaging. |
| **Version Control** | **Git & GitHub** | Source code version control and milestone tracking. |

---

## 📍 Current Development Stage

> ### 🟢 Current Status: **Stage 1 — Project Architecture & Documentation**
> **No application code has been implemented yet.**  
> Following software engineering best practices, the system design, relational database schemas, REST API endpoints, security rules, and troubleshooting maps are documented first before writing backend or frontend code.

---

## 🗺️ How the Project Will Be Developed

LabDistrib is developed through a **12-stage progressive roadmap**. Each stage builds incrementally upon the previous stage, ensuring a steady, well-tested learning journey:

1. **Stage 1 — Project Architecture & Documentation** 📍 *(Current)*: Complete project planning, directory blueprints, and architectural documentation.
2. **Stage 2 — Frontend Foundation**: Static HTML5/CSS3 pages and user interface layouts for catalogue and enquiry forms.
3. **Stage 3 — Spring Boot Foundation**: Maven build configuration, Spring Boot bootstrap, and baseline REST test endpoints.
4. **Stage 4 — Database Setup & Hibernate Configuration**: MySQL database schema, JDBC connectivity, and Hibernate JPA configuration.
5. **Stage 5 — Entity Modeling & Relationships**: JPA entities for Users, Roles, Categories, Manufacturers, Products, and Enquiries.
6. **Stage 6 — Repository Layer (Spring Data JPA)**: CRUD and custom query interfaces for database operations.
7. **Stage 7 — Service Layer & Business Logic**: Transactional business services, validation, and DTO transformations.
8. **Stage 8 — REST Controller Layer (API)**: Public and protected REST endpoints returning standard JSON responses.
9. **Stage 9 — Authentication & Security (Spring Security)**: User login, session/token authentication, and role guardrails.
10. **Stage 10 — Frontend-to-Backend Integration**: Connecting JavaScript `fetch()` calls to backend REST endpoints.
11. **Stage 11 — Testing & Validation**: Unit testing, integration testing, API validation, and edge-case verification.
12. **Stage 12 — Production Polish & Final Review**: Code cleanup, performance tuning, and project presentation preparation.

👉 **Read the full stage-by-stage guide:** [`docs/PROJECT_ROADMAP.md`](docs/PROJECT_ROADMAP.md)

---

## 📁 Project Structure

```text
labdistrib/
├── .gitignore                   # Git exclusion rules (secrets, build outputs, IDE files)
├── README.md                    # Root project documentation (this file)
│
├── docs/                        # Comprehensive project documentation
│   ├── README.md                # Documentation index and overview
│   ├── ARCHITECTURE.md          # Multi-tier architectural design and data flow
│   ├── DATABASE.md              # Relational schema, tables, and ER relationships
│   ├── API.md                   # REST API contract and endpoint specifications
│   ├── AUTHENTICATION.md        # Authentication, authorization, and RBAC design
│   ├── SECURITY.md              # Security policies, protection mechanisms, and checklist
│   ├── RECOVERY.md              # Error recovery strategies and debugging maps
│   ├── TROUBLESHOOTING.md       # Common development issues and solutions
│   ├── LEARNING.md              # Student learning log, key concepts, and diary
│   └── PROJECT_ROADMAP.md       # Detailed 12-stage implementation roadmap
│
├── frontend/                    # User interface (HTML, CSS, JavaScript)
│   ├── README.md                # Frontend architecture and planned layout
│   ├── index.html               # Home / Landing page
│   ├── products.html            # Public product catalogue
│   ├── product-details.html     # Single product view & enquiry modal
│   ├── categories.html          # Category browsing page
│   ├── manufacturers.html       # Manufacturer listings
│   ├── contact.html             # General contact & enquiry form
│   ├── login.html               # Admin / user authentication page
│   ├── admin/                   # Administrative interface
│   │   ├── dashboard.html       # Metrics & enquiry summary
│   │   ├── products.html        # Product management (CRUD)
│   │   ├── categories.html      # Category management (CRUD)
│   │   ├── manufacturers.html   # Manufacturer management (CRUD)
│   │   └── enquiries.html       # Enquiry review & status updater
│   ├── css/                     # Stylesheets (modular CSS)
│   └── js/                      # Vanilla JS modules (API clients, DOM handlers)
│
├── backend/                     # Spring Boot Java application
│   ├── README.md                # Backend architecture and package guide
│   ├── pom.xml                  # Maven project dependencies and build configuration
│   └── src/
│       ├── main/
│       │   ├── java/com/labdistrib/
│       │   │   ├── config/      # Application configurations (Security, CORS, Web)
│       │   │   ├── controller/  # REST API Controllers (@RestController)
│       │   │   ├── dto/         # Request & Response Data Transfer Objects
│       │   │   ├── entity/      # JPA Hibernate Entities (@Entity, @Table)
│       │   │   ├── exception/   # Custom exceptions and @ControllerAdvice
│       │   │   ├── repository/  # Spring Data JPA interfaces (@Repository)
│       │   │   ├── security/    # Security filters, user details, password encoders
│       │   │   ├── service/     # Business logic interfaces & implementations (@Service)
│       │   │   └── LabDistribApplication.java # Main application runner
│       │   └── resources/
│       │       ├── application.properties      # Spring Boot configuration
│       │       └── application-dev.properties  # Development environment settings
│       └── test/                # Unit and integration test suites
│
└── database/                    # Database definitions and seed scripts
    ├── README.md                # Database documentation & setup instructions
    ├── schema.sql               # Reference SQL schema definitions
    └── seed.sql                 # Initial seed data (default admin, sample categories)
```

---

## 📚 Documentation Index

All architectural designs, implementation guides, and conceptual explanations are documented in the [`docs/`](docs/) directory:

| Document | Purpose |
|:---------|:--------|
| [`docs/README.md`](docs/README.md) | Central hub and overview of all documentation files. |
| [`docs/PROJECT_ROADMAP.md`](docs/PROJECT_ROADMAP.md) | Step-by-step 12-stage development journey from Stage 1 to Stage 12. |
| [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) | Multi-tier system architecture, component breakdown, and request-response flow. |
| [`docs/DATABASE.md`](docs/DATABASE.md) | Database entities, schema definitions, table relationships, and indexing. |
| [`docs/API.md`](docs/API.md) | Full REST API specification (endpoints, HTTP methods, payloads, status codes). |
| [`docs/AUTHENTICATION.md`](docs/AUTHENTICATION.md) | Spring Security workflow, RBAC permissions, and session/token management. |
| [`docs/SECURITY.md`](docs/SECURITY.md) | Core security principles (SQL injection, XSS, CSRF defense, password hashing). |
| [`docs/RECOVERY.md`](docs/RECOVERY.md) | Failure recovery procedures, rollback strategies, and error handling maps. |
| [`docs/TROUBLESHOOTING.md`](docs/TROUBLESHOOTING.md) | Quick fixes for common Java, Spring Boot, MySQL, and Maven errors. |
| [`docs/LEARNING.md`](docs/LEARNING.md) | Concepts learned throughout the project, glossary, and self-study notes. |

---

## 💡 Key Design Decision: No Public Prices

Unlike typical retail e-commerce stores, B2B medical and pathology distribution involves specialized business logic:
- Equipment pricing varies by hospital/lab requirements, volume tiering, warranty packages, and shipping distances.
- Prospective clients browse specifications and submit **Enquiries**.
- Administrators receive these enquiries and generate tailored formal quotations.

This architectural decision is woven into both the frontend forms and the backend `Enquiry` entity and API endpoints.

---

## 🎓 About the Developer

This project is developed as an academic final-year project by a student learning modern Java web development with Spring Boot and Hibernate. It demonstrates practical software engineering disciplines: deliberate design before coding, clean package modularity, separation of concerns, and comprehensive documentation.
