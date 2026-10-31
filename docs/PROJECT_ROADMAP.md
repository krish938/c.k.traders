# Project Roadmap

LabDistrib will be developed in 12 progressive stages. Each stage builds on the previous one.

---

## Stage 1 — Project Architecture & Documentation ✅ (Current)

**Goal**: Plan the entire project before writing any application code.

**What I will learn**:
- How to structure a production project
- What documentation a real project needs
- The planned architecture and technology stack

**What will be created**:
- Project directory structure
- All documentation files (README, ARCHITECTURE, DATABASE, API, etc.)
- .gitignore
- Development roadmap

**Expected result**: A fully documented project skeleton with no application code. The entire plan is visible before implementation begins.

---

## Stage 2 — Frontend Foundation

**Goal**: Build the static HTML/CSS pages for the public-facing website.

**What I will learn**:
- HTML5 semantic structure
- CSS3 styling and responsive design
- Page layout and navigation
- How to structure frontend files

**What will be created**:
- index.html (home page)
- products.html (product listing)
- product-details.html (single product view with "Send Enquiry" button)
- categories.html (category listing)
- manufacturers.html (manufacturer listing)
- contact.html (contact/enquiry form)
- login.html (login form)
- CSS stylesheets
- Basic JavaScript structure

**Expected result**: A complete static website that looks like a real product catalogue. No data is loaded from a server yet — the pages use placeholder content.

---

## Stage 3 — Spring Boot Foundation

**Goal**: Set up the Spring Boot backend application.

**What I will learn**:
- What Spring Boot is and how it works
- Maven project setup and pom.xml
- Application entry point
- What Dependency Injection and Beans are
- How to create a basic REST controller
- How to run a Spring Boot application

**What will be created**:
- Maven project with Spring Boot dependencies
- LabDistribApplication.java (main class)
- A simple test controller ("Hello World" endpoint)
- application.properties configuration

**Expected result**: A running Spring Boot application that responds to HTTP requests. You can visit http://localhost:8080/api/test and see a response.

---

## Stage 4 — MySQL + JPA + Hibernate

**Goal**: Connect the application to a MySQL database using JPA and Hibernate.

**What I will learn**:
- How to install and configure MySQL
- What JPA annotations are (@Entity, @Id, @Column, etc.)
- What Hibernate does behind the scenes
- How to create Entity classes
- How to create Repository interfaces
- How entity relationships work (@OneToMany, @ManyToOne)

**What will be created**:
- MySQL database
- Entity classes (Product, Category, Manufacturer, etc.)
- Repository interfaces
- Database configuration in application.properties

**Expected result**: The application connects to MySQL and Hibernate automatically creates the database tables from your Entity classes.

---

## Stage 5 — Product CRUD API

**Goal**: Build the complete REST API for managing products, categories, and manufacturers.

**What I will learn**:
- How to build REST controllers with @RestController
- How to use @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
- How to create Service classes with business logic
- How to use DTOs for request/response data
- How to handle errors and exceptions
- How to validate input data

**What will be created**:
- ProductController, ProductService, ProductDTO
- CategoryController, CategoryService, CategoryDTO
- ManufacturerController, ManufacturerService, ManufacturerDTO
- Exception handling classes
- Input validation

**Expected result**: You can use Postman or a browser to create, read, update, and delete products, categories, and manufacturers through the API.

---

## Stage 6 — Frontend ↔ Backend Integration

**Goal**: Connect the static frontend to the live backend API.

**What I will learn**:
- How to make HTTP requests from JavaScript (fetch API)
- How to parse JSON responses
- How to dynamically render data in HTML
- How to handle loading states and errors
- What CORS is and how to configure it

**What will be created**:
- JavaScript files for each page (products.js, categories.js, etc.)
- Dynamic page rendering (replacing placeholder content with real data)
- Error handling in the frontend
- CORS configuration in Spring Boot

**Expected result**: The website displays real products, categories, and manufacturers loaded from the database through the API.

---

## Stage 7 — Authentication + Spring Security

**Goal**: Implement user login, JWT authentication, and role-based authorization.

**What I will learn**:
- What Spring Security is and how it works
- How to configure the Security Filter Chain
- How to generate and validate JWT tokens
- How BCrypt password hashing works
- How role-based access control works
- How to protect API endpoints

**What will be created**:
- SecurityConfig.java
- JwtService.java
- AuthController.java, AuthService.java
- User entity and repository
- Login functionality in the frontend
- Protected routes

**Expected result**: Users can log in, receive a JWT token, and access only the endpoints their role allows.

---

## Stage 8 — Business Admin Dashboard

**Goal**: Build the admin panel for the Business Admin.

**What I will learn**:
- How to build admin interfaces
- How to send authenticated requests from JavaScript
- CRUD operations from the admin frontend
- How to manage business profile information

**What will be created**:
- admin/dashboard.html
- admin/products.html (add/edit/delete products)
- admin/categories.html (add/edit/delete categories)
- admin/manufacturers.html (add/edit/delete manufacturers)
- admin/enquiries.html (view and manage enquiries)
- Admin JavaScript files
- BusinessProfile API and management

**Expected result**: The Business Admin can log in and manage the entire product catalogue, categories, manufacturers, and view customer enquiries.

---

## Stage 9 — Customer Enquiry System

**Goal**: Implement the full enquiry workflow.

**What I will learn**:
- How to build and validate forms
- How to handle form submissions
- How to create a multi-step workflow (submit → review → respond)
- How to send email notifications (optional)

**What will be created**:
- EnquiryController, EnquiryService, EnquiryDTO
- Enquiry form on product pages
- Enquiry management in admin dashboard
- Enquiry status workflow (NEW → IN_PROGRESS → RESOLVED → CLOSED)

**Expected result**: Customers can send enquiries about products. The Business Admin receives and manages these enquiries from the dashboard.

---

## Stage 10 — Super Admin

**Goal**: Build the Super Admin (developer) management capabilities.

**What I will learn**:
- How to create admin-level user management
- How audit logging works
- How to view and manage system-wide settings

**What will be created**:
- User management API and interface
- AuditLog entity, repository, and service
- Audit log viewer
- System settings management

**Expected result**: The Super Admin can manage all users, view audit logs, and configure system settings.

---

## Stage 11 — Security Hardening

**Goal**: Review and strengthen the application's security.

**What I will learn**:
- Common web security vulnerabilities (OWASP Top 10)
- Input sanitization and validation
- Rate limiting
- Secure headers
- Production security best practices

**What will be created**:
- Input validation on all endpoints
- Rate limiting on sensitive endpoints (login, enquiry)
- Security headers configuration
- Production-ready error handling (no stack traces)
- Security audit and fixes

**Expected result**: The application follows security best practices and is ready for production use.

---

## Stage 12 — Testing & Deployment

**Goal**: Test the application thoroughly and prepare for deployment.

**What I will learn**:
- Unit testing with JUnit
- Integration testing with Spring Boot Test
- How to package a Spring Boot application
- Deployment options (local server, cloud)
- Production configuration

**What will be created**:
- Unit tests for services
- Integration tests for controllers
- Production application.properties
- Deployment documentation
- Final project report

---

## Progress Tracker

| Stage | Name | Status |
|-------|------|--------|
| 1 | Project Architecture & Documentation | ✅ Complete |
| 2 | Frontend Foundation | ✅ Complete |
| 3 | Spring Boot Foundation | ✅ Complete |
| 4 | MySQL + JPA + Hibernate | ✅ Complete |
| 5 | Product CRUD API | ⬜ Not Started |
| 6 | Frontend ↔ Backend Integration | ⬜ Not Started |
| 7 | Authentication + Spring Security | ⬜ Not Started |
| 8 | Business Admin Dashboard | ⬜ Not Started |
| 9 | Customer Enquiry System | ⬜ Not Started |
| 10 | Super Admin | ⬜ Not Started |
| 11 | Security Hardening | ⬜ Not Started |
| 12 | Testing & Deployment | ⬜ Not Started |
