package com.labdistrib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LabDistribApplication — The Main Entry Point
 * 
 * This is the "front door" of the entire Spring Boot application.
 * When you run this class, Spring Boot:
 * 
 * 1. Starts an embedded web server (Tomcat) — no separate server installation needed
 * 2. Scans the com.labdistrib package for Controllers, Services, Repositories, etc.
 * 3. Auto-configures everything based on the dependencies in pom.xml
 * 4. Starts listening for HTTP requests on port 8080
 * 
 * What does @SpringBootApplication do?
 * It's a shortcut that combines three annotations:
 * - @Configuration     → "This class can define settings"
 * - @EnableAutoConfiguration → "Spring, configure everything automatically"
 * - @ComponentScan     → "Spring, find all my classes in this package"
 * 
 * In plain Java terms:
 * Think of this as your main() method, but instead of reading console input,
 * it starts a web server that listens for HTTP requests from browsers.
 * 
 * @author C.K.Traders Developer
 */
@SpringBootApplication
public class LabDistribApplication {

    public static void main(String[] args) {
        // This single line starts the entire application:
        // - Embedded Tomcat server starts on port 8080
        // - All Controllers become available at their mapped URLs
        // - The application is ready to receive HTTP requests
        SpringApplication.run(LabDistribApplication.class, args);
    }

}
