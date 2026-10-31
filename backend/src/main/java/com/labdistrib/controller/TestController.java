package com.labdistrib.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController — Your First REST Controller
 * 
 * WHAT IS THIS?
 * A Controller is a class that receives HTTP requests from a browser (or any client).
 * Think of it as the "receptionist" — it greets incoming requests and directs them.
 * 
 * In plain Java, you might have:
 *     Scanner scanner = new Scanner(System.in);
 *     String input = scanner.nextLine();
 * 
 * In Spring Boot, the Controller does the same thing but for web requests:
 *     Someone visits http://localhost:8080/api/test
 *     → Spring routes that request to the test() method below
 *     → The method returns data
 *     → Spring converts it to JSON and sends it back to the browser
 * 
 * KEY ANNOTATIONS:
 * 
 * @RestController
 *   "This class handles web requests and returns data (usually JSON)"
 *   Without this, Spring wouldn't know this class is a Controller.
 * 
 * @RequestMapping("/api")
 *   "All endpoints in this class start with /api"
 *   So @GetMapping("/test") becomes /api/test
 * 
 * @GetMapping("/test")
 *   "When someone sends a GET request to /api/test, run this method"
 *   GET = reading/retrieving data (like opening a webpage)
 * 
 * WHY RETURN Map INSTEAD OF String?
 * Spring Boot automatically converts Java objects to JSON.
 * A Map becomes a JSON object:
 *     Java:  Map.of("status", "success")
 *     JSON:  {"status": "success"}
 * 
 * This is how REST APIs work — Java objects in, JSON out.
 * No manual JSON formatting needed!
 * 
 * THIS CONTROLLER IS TEMPORARY:
 * It exists only to verify that Spring Boot is working correctly.
 * In Stage 5, we'll create real controllers (ProductController, etc.)
 */
@RestController
@RequestMapping("/api")
public class TestController {

    /**
     * Test endpoint — verifies the API is running.
     * 
     * Visit: http://localhost:8080/api/test
     * 
     * Expected response:
     * {
     *   "status": "success",
     *   "message": "LabDistrib API is running!",
     *   "stage": "Stage 3 — Spring Boot Foundation",
     *   "timestamp": "2026-09-22T22:00:00"
     * }
     */
    @GetMapping("/test")
    public Map<String, Object> test() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", "LabDistrib API is running!");
        response.put("application", "C.K.Traders — Laboratory & Pathology Equipment Distribution");
        response.put("stage", "Stage 3 — Spring Boot Foundation");
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    /**
     * Health check endpoint — used to verify the server is alive.
     * 
     * Visit: http://localhost:8080/api/health
     * 
     * In production, monitoring tools ping this endpoint to check
     * if the application is still running.
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("application", "LabDistrib");
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    /**
     * Info endpoint — returns basic application information.
     * 
     * Visit: http://localhost:8080/api/info
     */
    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("name", "LabDistrib");
        response.put("description", "Laboratory & Pathology Equipment Distribution Management System");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("developer", "C.K.Traders");

        Map<String, String> stages = new LinkedHashMap<>();
        stages.put("stage1", "Project Architecture & Documentation ✓");
        stages.put("stage2", "Frontend Foundation ✓");
        stages.put("stage3", "Spring Boot Foundation ✓");
        stages.put("stage4", "MySQL + JPA + Hibernate — NOT STARTED");
        stages.put("stage5", "Product CRUD API — NOT STARTED");
        response.put("roadmap", stages);

        return response;
    }
}
