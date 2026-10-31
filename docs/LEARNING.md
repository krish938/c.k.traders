# Learning Diary

This document tracks what I learn at each stage of building LabDistrib.

---

## Stage 1 — Project Architecture & Documentation

### What is a production application?

A production application is software that real people use for real work — not just a homework exercise or a tutorial project. It needs to:
- Work reliably (not crash when someone enters unexpected data)
- Be secure (protect user data and prevent unauthorized access)
- Be maintainable (other developers, or future-you, can understand and modify the code)
- Handle errors gracefully (show helpful error messages, not stack traces)

Our application is a production-style project because it will be used by a real business (C.K.Traders) to manage their product catalogue and customer enquiries.

### What is the frontend?

The frontend is everything the user sees and interacts with in their web browser.

- **HTML (HyperText Markup Language)**: Defines the structure and content of a web page. Think of it as the skeleton — headings, paragraphs, images, buttons, forms.
- **CSS (Cascading Style Sheets)**: Controls how the HTML looks — colors, fonts, spacing, layout. Think of it as the clothing and makeup.
- **JavaScript**: Makes the page interactive — responding to clicks, loading data from the server, updating the page without refreshing. Think of it as the muscles and brain.

We're using "vanilla" (plain) JavaScript instead of frameworks like React or Angular because:
1. It's simpler to understand
2. There's no extra build step
3. You learn the fundamentals first

### What is the backend?

The backend is the server-side code that the user never sees. It:
- Receives requests from the frontend
- Processes business logic (rules like "only admins can add products")
- Reads and writes data to the database
- Sends responses back to the frontend

We're using **Java** because you already know it, and **Spring Boot** because it's the most popular Java framework for building web applications. Spring Boot eliminates a lot of boilerplate code — things you'd have to write manually with plain Java servlets.

### What is an API?

API stands for Application Programming Interface. It's a set of rules that defines how two pieces of software communicate.

Simple analogy: Imagine a restaurant.
- You (the frontend/customer) look at the menu and tell the waiter what you want
- The waiter (the API) takes your order to the kitchen
- The kitchen (the backend) prepares the food
- The waiter brings the food back to you

You don't go into the kitchen yourself. You don't need to know how the food is prepared. You just use the waiter (API) as the middleman.

In our project:
- The frontend sends a request like `GET /api/products`
- The backend receives it, fetches products from the database
- The backend sends back the product data as JSON
- The frontend displays it on the page

### What is a database?

A database is organized storage for your application's data. Without a database, all data would be lost when the application restarts.

**MySQL** is a relational database — it stores data in tables (like spreadsheets) with rows and columns. For example:

| id | name | description | category_id |
|----|------|-------------|-------------|
| 1 | Example Microscope | A basic microscope | 1 |
| 2 | Example Centrifuge | A lab centrifuge | 2 |

We use MySQL because:
- It's free and open-source
- It's widely used in production
- It works well with Spring Boot and Hibernate
- It's relational — perfect for our structured data (products, categories, users)

### What is architecture?

Architecture is how you organize your code. It's the big-picture structure of your application.

Why do we separate responsibilities?

Imagine building a house:
- The electrician handles wiring
- The plumber handles pipes
- The carpenter handles wood
- The painter handles paint

You wouldn't ask the electrician to do plumbing. Each person has a specific job.

Software architecture works the same way:
- Controllers handle incoming requests
- Services handle business logic
- Repositories handle database access
- Entities represent database data

This makes code easier to read, debug, test, and modify.

### What I Learned in Stage 1

- How to plan a project before writing code
- The difference between frontend and backend
- What REST APIs are and why we need them
- How databases store application data
- Why architecture matters
- How to write project documentation
- The importance of separating concerns

### What I Will Learn Next

- **Stage 4**: Connecting to MySQL with JPA and Hibernate
- **Stage 5**: Building REST API endpoints
- **Stage 6**: Making the frontend talk to the backend
- **Stage 7**: Authentication with JWT and Spring Security

---

## Stage 2 — Frontend Foundation

### What I Learned

- **HTML5 semantic elements** — Using `<header>`, `<nav>`, `<main>`, `<section>`, `<footer>` instead of `<div>` everywhere makes code readable and helps accessibility
- **CSS custom properties** — Variables like `--color-primary: #1B2A4A` defined once and used everywhere. Change one value, update the whole site.
- **CSS Grid and Flexbox** — Two layout systems: Grid for 2D layouts (rows + columns), Flexbox for 1D layouts (row OR column)
- **Responsive design** — Using `@media (max-width: 768px)` to change layouts on smaller screens
- **BEM naming** — Block__Element--Modifier pattern for CSS class names (e.g., `header__nav-link--active`)
- **Mobile-first approach** — Design for mobile, then add complexity for larger screens

### Key Takeaway

The frontend is completely independent from the backend. You can open HTML files directly in a browser without any server. This separation is what makes REST APIs powerful — the frontend doesn't care what language the backend uses.

---

## Stage 3 — Spring Boot Foundation

### What is Maven?

Maven is a build tool that manages your project's dependencies. Instead of manually downloading JAR files, you list what you need in `pom.xml` and Maven downloads everything automatically.

The **Maven Wrapper** (`mvnw.cmd`) is a script bundled with the project so you don't need Maven installed globally. Anyone who clones the project can build it immediately.

### What is Spring Boot?

Spring Boot is a framework that eliminates boilerplate code. Without it, building a Java web application requires configuring a web server, servlet mappings, XML files, etc. With Spring Boot, one annotation (`@SpringBootApplication`) and one line (`SpringApplication.run(...)`) starts everything.

### What is @SpringBootApplication?

A shortcut combining three things:
1. `@Configuration` — This class can define settings
2. `@EnableAutoConfiguration` — Spring configures everything automatically
3. `@ComponentScan` — Spring finds all your Controllers, Services, etc.

### What is @RestController?

Marks a Java class as a web request handler. Spring Boot routes HTTP requests to the correct method based on the URL.

```java
@RestController           // "This class handles web requests"
@RequestMapping("/api")   // "All URLs start with /api"
public class TestController {
    @GetMapping("/test")  // "GET /api/test → run this method"
    public Map<String, String> test() {
        return Map.of("status", "success");  // Automatically becomes JSON
    }
}
```

### What is application.properties?

A simple key=value settings file. Controls server port, logging, database connection, etc. No Java code needed — just change the settings file.

### What I Learned

- How Maven manages dependencies via `pom.xml`
- How Spring Boot starts a web server with one annotation
- How `@RestController` and `@GetMapping` handle HTTP requests
- How Spring Boot automatically converts Java objects to JSON
- How `application.properties` configures the application
- How to run a Spring Boot application with `.\mvnw.cmd spring-boot:run`

---

## Stage 4 — MySQL + JPA + Hibernate

### What is JPA?

JPA (Java Persistence API) is a **specification** — a set of rules for mapping Java objects to database tables. It defines annotations like `@Entity`, `@Id`, `@Column`, but doesn't do the actual work.

### What is Hibernate?

Hibernate is the **implementation** of JPA — the engine that actually:
- Creates database tables from your Java classes
- Translates method calls into SQL queries
- Maps database rows back into Java objects

Analogy: JPA is the **driving license** (rules). Hibernate is the **car** (does the driving).

### What is @Entity?

Marks a Java class as a database table. Each instance = one row.

```java
@Entity                         // "This class = a database table"
@Table(name = "categories")     // "Call the table 'categories'"
public class Category {
    @Id                         // "This field = primary key"
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // "Auto-increment"
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;        // → VARCHAR(100) NOT NULL UNIQUE
}
```

### What is @MappedSuperclass?

A parent class that shares fields (id, createdAt, updatedAt) with all entities **without creating its own table**. DRY principle — Don't Repeat Yourself.

### What are Entity Relationships?

- `@ManyToOne` — Many products belong to ONE category (FK lives in products table)
- `@OneToMany` — One category HAS many products (inverse side, no FK here)
- `FetchType.LAZY` — Don't load related data until you actually ask for it (performance)

### What is a Repository?

An interface that gives you database operations with **zero SQL**:

```java
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByActiveTrue();  // Spring generates the SQL automatically!
}
```

Spring reads the method name and builds: `SELECT * FROM categories WHERE active = true`

### What is Dependency Injection?

Instead of YOU creating objects, Spring creates them and gives them to you:

```java
// Plain Java — you create everything manually:
CategoryRepository repo = new CategoryRepositoryImpl(dataSource);

// Spring — you just ask for it, Spring provides it:
@Bean
CommandLineRunner initData(CategoryRepository repo) { ... }
```

### What I Learned

- How JPA annotations map Java classes to database tables
- How `@ManyToOne` / `@OneToMany` create foreign key relationships
- How Repositories provide CRUD operations without writing SQL
- How `spring.jpa.hibernate.ddl-auto=update` auto-creates tables
- How `CommandLineRunner` seeds data on startup
- How Dependency Injection works — Spring provides what you need
- How `application-local.properties` keeps secrets out of source code
