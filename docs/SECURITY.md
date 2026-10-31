# Security Principles & Design (Planned)

> ⚠️ **This document describes the security DESIGN. The system is NOT yet secure because none of these measures have been implemented.**  
> Everything detailed below represents the security standards and planned practices for the **C.K.Traders — Laboratory & Pathology Equipment Distribution Management System (labdistrib)**. These measures will be implemented across upcoming development stages.

---

## 1. Overview & Security Philosophy

For a laboratory and pathology equipment distribution business like C.K.Traders:
- Customer contact info, quotation inquiries, product catalogs, and distributor relationships are valuable business assets.
- System integrity ensures that prices (negotiated offline), customer inquiries, and administrator functions are protected against unauthorized modification or exposure.
- As a learning developer building with Java Spring Boot and MySQL, applying standard defensive programming practices from the ground up prevents major vulnerabilities (such as SQL injection, data leaks, and credential exposure).

---

## 2. Planned Security Principles

### Principle 1: Password Security
Passwords are the first line of defense for user accounts.

- **BCrypt Hashing**: All passwords will be securely hashed using BCrypt (adaptive one-way hashing with salt) prior to database persistence.
- **Never Plain Text**: Passwords will **NEVER** be stored in plain text anywhere in the database, cache, or backups.
- **Never Logged or Displayed**: Passwords will be excluded from backend logs (SLF4J/Logback), debug prints, and will never be returned in API response bodies.
- **Minimum Password Requirements**: Enforce strong password rules upon registration and password changes (e.g., minimum 8 characters, combination of uppercase, lowercase, numbers, and special symbols).

---

### Principle 2: Authentication Security
Authentication confirms the user's identity before granting access to the system.

- **Stateless Authentication via JWT**: The application will use JSON Web Tokens (JWT) rather than server-side HTTP sessions, eliminating session storage vulnerabilities and memory overhead.
- **Reasonable Expiration Windows**: JWT tokens will be issued with a finite lifetime (e.g., 2 hours for normal activity or 24 hours max). Expired tokens cannot be used and require the user to log in again.
- **Per-Request Token Validation**: Every request to a protected API endpoint must pass through a custom filter (`JwtAuthenticationFilter`) that checks the cryptographic signature and expiration timestamp.
- **Immediate Rejection of Invalid Tokens**: Any malformed, expired, or tampered token will be rejected immediately with HTTP `401 Unauthorized` without executing any business logic.

---

### Principle 3: Authorization Security (Access Control)
Authorization ensures that authenticated users can only perform actions appropriate for their role.

- **Role-Based Access Control (RBAC)**: All privileges will map to three clear roles: `CUSTOMER`, `BUSINESS_ADMIN`, and `SUPER_ADMIN`.
- **Endpoint-Level Restrictions**: Each REST controller route will explicitly specify required roles using annotations like `@PreAuthorize("hasRole('BUSINESS_ADMIN')")`.
- **Strict Role Boundaries**:
  - `CUSTOMER` users **MUST NOT** access any administrative endpoints (e.g., cannot add/edit products, cannot view other clients' enquiries).
  - `BUSINESS_ADMIN` users (Father/Manager) manage products, categories, manufacturers, and customer enquiries, but **MUST NOT** access Super Admin functions (user management, database administration, system logs).
  - `SUPER_ADMIN` users (Developer) retain system administration authority.
- **Default Deny Rule**: All endpoints will be closed by default unless explicitly marked as public (e.g., public catalog browsing and login).

---

### Principle 4: API Security
Protecting REST API endpoints from malicious input and unauthorized access.

- **Mandatory Authentication on Admin APIs**: All endpoints under `/api/admin/**` will strictly require valid authentication credentials.
- **Comprehensive Input Validation**:
  - All user inputs (registration, login, product forms, enquiry messages) will be validated using Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Size`, `@Email`, `@Pattern`).
  - Rejection of invalid inputs occurs at the controller boundary with HTTP `400 Bad Request`.
- **SQL Injection Prevention**:
  - Prevented by design using Spring Data JPA and Hibernate with parameterized queries and Object-Relational Mapping (ORM).
  - Raw string concatenation will **NEVER** be used to assemble SQL/JPQL queries.
- **Cross-Site Scripting (XSS) Mitigation**:
  - Proper output encoding on the frontend (using `textContent` instead of `innerHTML` when rendering user-supplied strings).
  - Sanitization of text areas (e.g., customer enquiry notes or product descriptions) before rendering in browsers.
- **CORS (Cross-Origin Resource Sharing) Configuration**:
  - Restrict allowed browser origins strictly to the trusted frontend domain (e.g., `http://localhost:3000` or production domain), blocking arbitrary third-party websites from making cross-origin requests with user credentials.

---

### Principle 5: Data Security
Ensuring sensitive enterprise and user data is shielded from exposure.

- **No Sensitive Data in Responses**: Password hashes, internal system IDs, and database trace information will never be returned in API responses.
- **Data Transfer Objects (DTOs)**:
  - Separate database entities (`User`, `Product`, `Enquiry`) from API payloads (`UserResponseDTO`, `ProductDTO`, `EnquiryRequestDTO`).
  - DTOs act as a security firewall, controlling exactly which fields enter or leave the backend.
- **Secure Configuration & Environment Variables**:
  - Database credentials (passwords, usernames) and JWT signing secrets will be loaded from environment variables or a non-committed `application-local.properties` file.
  - Secrets will **NEVER** be committed to version control (Git).
- **Enforced `.gitignore` Rules**: Sensitive files like `.env`, `application-local.properties`, build artifacts, and secret keys are permanently excluded from Git tracking.

---

### Principle 6: Development & Operational Security
Practices during coding, testing, and system operation.

- **No Hardcoded Credentials**: Source code will never contain hardcoded passwords, database URLs with embedded credentials, or JWT signing secrets.
- **Safe Error Handling**:
  - A centralized exception handler (`@ControllerAdvice` / `@ExceptionHandler`) will intercept errors and return clean, user-friendly JSON messages.
  - Stack traces, database schema details, and SQL errors will **NEVER** be sent to client responses, preventing attackers from fingerprinting system internals.
- **Audit Trails**:
  - Key business actions (admin logins, product price/spec updates, user role changes) will be logged to an audit table with timestamp and user ID.

---

## 3. Security Checklist (For Later Implementation)

Use this checklist during Stage 7 (Security & Integration) and subsequent milestones to verify that every planned security control has been implemented and tested:

- [ ] **BCrypt Password Hashing**: `BCryptPasswordEncoder` bean configured with a cost factor of at least 10–12.
- [ ] **JWT Token Generation**: Secure token generation with HMAC-SHA256 signature and sensible expiry time.
- [ ] **JWT Token Validation**: Custom `JwtAuthenticationFilter` verifying tokens on incoming requests.
- [ ] **Role-Based Access Control (RBAC)**: User roles correctly mapped (`ROLE_CUSTOMER`, `ROLE_BUSINESS_ADMIN`, `ROLE_SUPER_ADMIN`) and enforced via `@PreAuthorize`.
- [ ] **API Endpoint Security**: `SecurityFilterChain` configured to protect `/api/admin/**` while allowing public access to catalog and auth endpoints.
- [ ] **Input Validation on All Endpoints**: Jakarta Bean Validation applied to all incoming DTOs (`@NotNull`, `@Size`, `@Email`, etc.).
- [ ] **CORS Configuration**: Explicit `CorsConfigurationSource` configured to permit only authorized client origins.
- [ ] **Secrets Removed from Source Code**: JWT secret key and database credentials moved out of `application.properties` into environment variables.
- [ ] **`.gitignore` Covers Sensitive Files**: Verification that `.env`, `application-local.properties`, and keystores are ignored by Git.
- [ ] **Error Handling Does Not Expose Internals**: Global `@ControllerAdvice` in place, returning standard error payloads (`timestamp`, `status`, `error`, `message`, `path`) without stack traces.

---

> 📘 **Next Steps**: Keep these security principles in mind during data modeling (Stage 2) and API design (Stage 4). Implementation will take place in **Stage 7**.
