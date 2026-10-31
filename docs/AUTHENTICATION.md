# Planned Authentication & Authorization Architecture

> ⚠️ **STATUS: PLANNED — NOT YET IMPLEMENTED**  
> This document describes the planned authentication and authorization design for the **C.K.Traders Laboratory & Pathology Equipment Distribution Management System (labdistrib)**. None of these security mechanisms are implemented in Stage 1. They will be implemented in subsequent stages (specifically Stage 7 with Spring Security & JWT).

---

## 1. Overview: Authentication vs. Authorization

When building a multi-user web application like C.K.Traders, security revolves around two distinct concepts: **Authentication** and **Authorization**. Beginners often mix them up, but they answer two completely different questions.

```
                    ┌─────────────────────────┐
                    │   User Enters System    │
                    └────────────┬────────────┘
                                 │
                                 ▼
                    ┌─────────────────────────┐
                    │     AUTHENTICATION      │
                    │    "Who are you?"       │
                    └────────────┬────────────┘
                                 │ Verified!
                                 ▼
                    ┌─────────────────────────┐
                    │      AUTHORIZATION      │
                    │ "What can you do here?" │
                    └────────────┬────────────┘
                                 │ Permitted!
                                 ▼
                    ┌─────────────────────────┐
                    │     Perform Action      │
                    └─────────────────────────┘
```

### Authentication ("Who are you?")
- **Definition**: The process of verifying that a user is truly who they claim to be.
- **Real-World Analogy**: Showing your government ID card or passport at an airport or building entrance. The security guard checks your photo and details to confirm your identity.
- **In C.K.Traders**: When a customer, father (Business Admin), or developer (Super Admin) enters their email address and password on the login screen, the system verifies those credentials.

### Authorization ("What are you allowed to do?")
- **Definition**: The process of checking what permissions or access privileges an authenticated user has.
- **Real-World Analogy**: After entering the building, your keycard only opens certain doors. A guest badge cannot unlock the server room or executive offices, while a facility manager's keycard can open almost everything.
- **In C.K.Traders**:
  - A **CUSTOMER** can browse equipment catalog items and submit enquiry forms, but **CANNOT** create new products or view other customers' enquiries.
  - A **BUSINESS_ADMIN** can add/edit products, update categories, and review customer enquiries, but **CANNOT** manage user accounts or modify system settings.
  - A **SUPER_ADMIN** has full administrative access, including user management and system audit logs.

---

## 2. Planned Authentication Flow

The system will use a stateless **Token-Based Authentication** architecture powered by **JWT (JSON Web Tokens)** and **Spring Security**.

### Step-by-Step Flow Diagram

```
User enters email + password
         ↓
Frontend sends POST /api/auth/login
         ↓
AuthController receives the request
         ↓
AuthService verifies credentials
         ↓
Password checked against BCrypt hash in database
         ↓
If valid → Generate JWT token
         ↓
JWT token sent back to frontend
         ↓
Frontend stores JWT token
         ↓
All future requests include JWT in Authorization header
         ↓
Backend validates JWT on every protected request
         ↓
Role extracted from JWT → Authorization check
         ↓
Access granted or denied
```

### Detailed Walkthrough of Each Step

1. **User Credentials Input**: The user visits the login page (`/login.html`) and enters their email and password into the HTML form.
2. **Frontend Request**: JavaScript captures the form submission and makes an asynchronous HTTP POST request to `/api/auth/login` with a JSON payload:
   ```json
   {
     "email": "admin@cktraders.com",
     "password": "mySecurePassword123"
   }
   ```
3. **Controller Handling**: `AuthController` in Spring Boot maps the `/api/auth/login` endpoint, deserializes the JSON into a Login Request DTO (Data Transfer Object), and passes it to `AuthService`.
4. **Service Verification**: `AuthService` queries the database via `UserRepository` to look up the user by email. If no user is found, login fails immediately.
5. **BCrypt Password Check**: If the user exists, Spring Security's `PasswordEncoder` compares the entered plain-text password with the stored BCrypt hash.
6. **Token Generation**: If the password matches, the backend builds a signed JWT containing:
   - User ID (Subject)
   - User Email
   - Assigned Role (`CUSTOMER`, `BUSINESS_ADMIN`, or `SUPER_ADMIN`)
   - Expiration timestamp
7. **Response to Client**: The backend returns HTTP `200 OK` with the token in the response body:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     "role": "BUSINESS_ADMIN",
     "fullName": "Chimanlal K. Patel"
   }
   ```
8. **Client Storage**: Frontend JavaScript stores the token securely (in `localStorage` or `sessionStorage`).
9. **Subsequent API Requests**: When calling protected endpoints (e.g., creating a product or fetching enquiry details), the frontend includes the token in the HTTP `Authorization` header:
   ```http
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```
10. **Filter Validation**: A Spring Security filter (`JwtAuthenticationFilter`) intercepts every incoming HTTP request, decodes the token, checks the digital signature, and verifies it hasn't expired.
11. **Role Extraction & Authorization**: The filter extracts the user's role and sets the `SecurityContext`. Spring Security then checks if that role is authorized for the requested endpoint.
12. **Decision**:
    - **Valid & Authorized**: Request proceeds to the controller method (`200 OK` or `201 Created`).
    - **Invalid / Expired Token**: Request rejected with `401 Unauthorized`.
    - **Valid Token but Insufficient Role**: Request rejected with `403 Forbidden`.

---

## 3. What is JWT (JSON Web Token)?

### Core Concepts

- **JWT** stands for **JSON Web Token** (pronounced like the English word "jot").
- It is a compact, URL-safe means of transferring claims (data) between two parties (the frontend client and the Spring Boot backend).
- It acts like a **digital ID card** or passport that the server issues to you after you prove who you are.
- Once issued, the server does **not** need to store sessions in memory or database. Every request is self-contained ("stateless").

### What Does a JWT Contain?

A JWT consists of three parts separated by dots (`.`): `Header.Payload.Signature`

1. **Header**: Contains the type of token (`JWT`) and the hashing algorithm used (such as `HMAC SHA256`).
2. **Payload (Claims)**: Contains non-sensitive user identity details and role information:
   - `sub` (Subject / User ID)
   - `email` (User email)
   - `role` (e.g., `ROLE_BUSINESS_ADMIN`)
   - `iat` (Issued at timestamp)
   - `exp` (Expiration timestamp)
   > ⚠️ **CRITICAL NOTE**: The payload is only base64-encoded, NOT encrypted! Anyone who has the token can read the payload. Therefore, **NEVER** put passwords, credit cards, or secret keys inside a JWT payload!
3. **Signature**: A cryptographic signature generated by taking the encoded Header, the encoded Payload, and a private secret key known ONLY to the backend server. If an attacker tampers with the payload (e.g., changing their role from `CUSTOMER` to `SUPER_ADMIN`), the signature check fails, and the server rejects the request.

### Key Characteristics

- **Stateless**: The server does not keep a session table in memory; it verifies the signature cryptographically on every request.
- **Expiration Time**: Tokens will have an expiration (e.g., 2 hours or 24 hours). Once expired, the token is automatically rejected, requiring the user to log in again.
- **When We Implement This**: Full JWT implementation (generation, parsing, validation, filter integration) will be developed in **Stage 7**.

---

## 4. What is BCrypt?

### Core Concepts

- **BCrypt** is an adaptive, one-way cryptographic password hashing algorithm based on the Blowfish cipher.
- When a user registers or changes their password, BCrypt converts the human-readable password into a scrambled string of fixed length (60 characters).
- **One-Way Function**: Unlike two-way encryption, a hash **CANNOT** be reversed or decrypted back into the original plain text password.
- **Salt Built-in**: BCrypt automatically incorporates a random salt (random string) into every hash. This means even if two users have the identical password `"password123"`, their BCrypt hashes will look completely different, preventing rainbow table attacks.
- **Work Factor (Cost)**: BCrypt allows configuring the number of hashing rounds (e.g., 10 or 12). This makes brute-force attacks computationally expensive and slow for attackers while remaining fast enough for a single legitimate login.

### Why Is BCrypt Critical for C.K.Traders?

- Even if a malicious attacker manages to steal a backup of the MySQL database, they will only see scrambled hashes, not the actual customer or admin passwords.
- Developers and system administrators cannot see user passwords in database logs, queries, or tables.

### How Verification Works

```
User registers:
"hello123" ──► BCrypt(salt + "hello123") ──► "$2a$10$N9qo8uLOickgx2ZMRZoMye..." (Saved to DB)

User logs in:
"hello123" entered
       ↓
BCrypt extracts salt from stored hash and hashes "hello123" with that salt
       ↓
Generated hash matches stored hash? ──► YES ──► Login Success!
```

### Example Hash Structure

A typical BCrypt hash looks like this:
```
$2a$10$N9qo8uLOickgx2ZMRZoMyeijZAgcfl7p92ldGxad68LJZdL17lhWy
│  │  │                     │
│  │  │                     └─ 31-character encrypted hash
│  │  └─ 22-character salt
│  └─ Cost factor (2^10 = 1024 rounds)
└─ BCrypt algorithm identifier
```

---

## 5. Planned Role-Based Authorization (RBAC)

C.K.Traders serves three distinct user roles with specific business responsibilities:

1. **`CUSTOMER`**: External clients (pathology lab technicians, hospital doctors, clinic purchase officers) who explore equipment, download brochures, and request quotation enquiries.
2. **`BUSINESS_ADMIN`**: The business owner/manager (father) who maintains the product catalog, tracks incoming enquiries, reviews client details, and manages business relationships.
3. **`SUPER_ADMIN`**: The technical administrator (student developer) responsible for user accounts, application maintenance, audit logging, and system configurations.

### Access Control Matrix

| Resource / Action | CUSTOMER | BUSINESS_ADMIN | SUPER_ADMIN | Notes |
|-------------------|:--------:|:--------------:|:-----------:|-------|
| **Browse products & categories** | ✅ | ✅ | ✅ | Public catalog access |
| **View product technical specs** | ✅ | ✅ | ✅ | Open to all |
| **Send equipment enquiry** | ✅ | ✅ | ✅ | Primary customer interaction |
| **Add / Edit / Delete products** | ❌ | ✅ | ✅ | Admin catalog management |
| **Add / Edit / Delete categories** | ❌ | ✅ | ✅ | Organization of laboratory items |
| **Add / Edit / Delete manufacturers** | ❌ | ✅ | ✅ | Brand management (Sysmex, Roche, etc.) |
| **View customer enquiries & quotes** | ❌ | ✅ | ✅ | Business administration |
| **Update enquiry status (NEW/REPLIED/CLOSED)** | ❌ | ✅ | ✅ | Managing sales follow-ups |
| **Manage user accounts & roles** | ❌ | ❌ | ✅ | Super Admin exclusive |
| **View audit logs & login history** | ❌ | ❌ | ✅ | Super Admin exclusive |
| **System settings & technical config** | ❌ | ❌ | ✅ | Super Admin exclusive |

---

## 6. What We Will Learn Later (Stage 7 & Beyond)

As the project progresses through its implementation stages, we will study and implement the following Java Spring Boot security components:

1. **Spring Security Architecture**:
   - `SecurityFilterChain` bean configuration in modern Spring Boot 3.x (replacing deprecated `WebSecurityConfigurerAdapter`).
   - Disabling CSRF for stateless REST APIs.
   - Configuring session management as `SessionCreationPolicy.STATELESS`.
2. **BCrypt Password Encoder**:
   - Creating a `PasswordEncoder` bean (`BCryptPasswordEncoder(12)`).
   - Automatically encoding passwords during user registration and seed data initialization.
3. **JWT Utility Class**:
   - Generating signing keys using `io.jsonwebtoken (jjwt)` library.
   - Methods: `generateToken(UserDetails)`, `extractUsername(String token)`, `extractRoles(String token)`, and `isTokenValid(String token, UserDetails)`.
4. **JWT Authentication Filter**:
   - Custom filter extending `OncePerRequestFilter`.
   - Intercepting HTTP requests, checking the `Authorization: Bearer <token>` header, validating tokens, and populating `SecurityContextHolder`.
5. **Method-Level Security**:
   - Using `@EnableMethodSecurity` and `@PreAuthorize("hasRole('BUSINESS_ADMIN') or hasRole('SUPER_ADMIN')")` annotations directly on controller endpoints.
6. **CORS (Cross-Origin Resource Sharing)**:
   - Configuring allowed origins, methods (`GET`, `POST`, `PUT`, `DELETE`), and headers so the HTML/JS frontend can safely communicate with the backend.
7. **Custom Authentication Entry Point**:
   - Handling unauthorized access gracefully with clear JSON error responses instead of default HTML error pages.

---

> 💡 **Reminder**: Keep this plan in mind as the data models and database schemas are defined in Stages 2 & 3. Authentication and authorization will integrate smoothly once the foundations are established.
