package com.labdistrib.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.labdistrib.entity.Category;
import com.labdistrib.entity.Manufacturer;
import com.labdistrib.entity.Product;
import com.labdistrib.repository.CategoryRepository;
import com.labdistrib.repository.ManufacturerRepository;
import com.labdistrib.repository.ProductRepository;

/**
 * DataInitializer — Seeds the database with sample data on first startup.
 * 
 * WHAT IS THIS?
 * When you start the application for the first time, the database is empty.
 * This class checks if data exists, and if not, inserts sample rows so you
 * can immediately see something when you query the database.
 * 
 * KEY CONCEPTS:
 * 
 * @Configuration — "This class provides configuration/setup for the application"
 *   Spring scans for @Configuration classes and runs them at startup.
 * 
 * @Bean — "This method creates an object that Spring should manage"
 *   The CommandLineRunner bean runs automatically after the application starts.
 * 
 * CommandLineRunner — "Run this code after Spring Boot finishes starting up"
 *   Think of it as code that runs right after main() but with access to
 *   all your repositories and services.
 * 
 * IMPORTANT: This is SAMPLE data for testing.
 * The real products, categories, and manufacturers will be created by the
 * Business Admin through the admin dashboard. Nothing is hardcoded.
 */
@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * Seeds the database with sample data if tables are empty.
     * 
     * Notice the method parameters: Spring automatically provides the repositories.
     * This is called DEPENDENCY INJECTION — you declare what you need,
     * and Spring gives it to you. No manual object creation needed.
     * 
     * In plain Java, you'd write:
     *   CategoryRepository repo = new CategoryRepository();  // You create it
     * 
     * In Spring:
     *   Spring sees the parameter and says: "You need a CategoryRepository?
     *   I already have one. Here you go."
     */
    @Bean
    CommandLineRunner initData(CategoryRepository categoryRepo,
                               ManufacturerRepository manufacturerRepo,
                               ProductRepository productRepo) {
        return args -> {
            // Only seed if database is empty
            if (categoryRepo.count() > 0) {
                log.info("Database already has data. Skipping seed.");
                return;
            }

            log.info("Seeding database with sample data...");

            // -----------------------------------------------------------------
            // Categories
            // -----------------------------------------------------------------
            Category labEquipment = categoryRepo.save(
                new Category("Laboratory Equipment", 
                    "Precision instruments and equipment for modern laboratory setups"));
            
            Category pathEquipment = categoryRepo.save(
                new Category("Pathology Equipment", 
                    "Advanced equipment for pathology departments and histopathology labs"));
            
            Category diagnostics = categoryRepo.save(
                new Category("Diagnostic Products", 
                    "Reliable diagnostic analyzers and instruments for clinical testing"));
            
            Category consumables = categoryRepo.save(
                new Category("Consumables", 
                    "Essential consumables, reagents, and disposable supplies"));

            log.info("Created {} categories", categoryRepo.count());

            // -----------------------------------------------------------------
            // Manufacturers
            // -----------------------------------------------------------------
            Manufacturer mfrA = manufacturerRepo.save(
                new Manufacturer("Manufacturer A", 
                    "Leading manufacturer of laboratory analyzers and diagnostic instruments"));
            
            Manufacturer mfrB = manufacturerRepo.save(
                new Manufacturer("Manufacturer B", 
                    "Specialists in pathology equipment and tissue processing systems"));
            
            Manufacturer mfrC = manufacturerRepo.save(
                new Manufacturer("Manufacturer C", 
                    "Premium microscopes and imaging solutions for research and clinical use"));

            log.info("Created {} manufacturers", manufacturerRepo.count());

            // -----------------------------------------------------------------
            // Products (no prices — only descriptions and specs)
            // -----------------------------------------------------------------
            Product p1 = new Product("Digital Microscope", 
                "High-resolution digital microscope for laboratory analysis and research",
                labEquipment, mfrC);
            p1.setSpecifications("Model: DM-500X\nMagnification: 40x-1000x\nDisplay: 7\" LCD\nCamera: 5MP\nIllumination: LED\nPower: 220V AC\nWeight: 5.2 kg\nWarranty: 1 Year");
            productRepo.save(p1);

            Product p2 = new Product("Centrifuge Machine", 
                "Variable speed centrifuge for sample separation in clinical and research labs",
                labEquipment, mfrA);
            p2.setSpecifications("Model: CF-3000\nSpeed: 300-12000 RPM\nCapacity: 12x15ml\nTimer: 0-99 min\nDisplay: Digital LED\nPower: 220V AC\nWeight: 8.5 kg");
            productRepo.save(p2);

            Product p3 = new Product("Hematology Analyzer", 
                "Automated hematology analyzer for complete blood cell counting and differentiation",
                diagnostics, mfrA);
            p3.setSpecifications("Model: HA-5500\nParameters: 22\nThroughput: 60 samples/hr\nSample Volume: 20μL\nDisplay: 10\" Touchscreen\nPrinter: Built-in\nWeight: 45 kg");
            productRepo.save(p3);

            Product p4 = new Product("Tissue Processor", 
                "Automated tissue processing system for histopathology sample preparation",
                pathEquipment, mfrB);
            p4.setSpecifications("Model: TP-200\nStations: 12\nCapacity: 150 cassettes\nProcess Time: Programmable\nDisplay: LCD\nPower: 220V AC\nWeight: 65 kg");
            productRepo.save(p4);

            Product p5 = new Product("Reagent Kit", 
                "Multi-parameter reagent kit for clinical chemistry analysis",
                consumables, mfrA);
            p5.setSpecifications("Type: Clinical Chemistry\nTests: Glucose, Urea, Creatinine, Cholesterol\nShelf Life: 12 months\nStorage: 2-8°C\nPack Size: 100 tests");
            productRepo.save(p5);

            Product p6 = new Product("Urine Analyzer", 
                "Semi-automated urine analysis system for routine clinical screening",
                diagnostics, mfrB);
            p6.setSpecifications("Model: UA-120\nParameters: 11\nThroughput: 120 strips/hr\nDisplay: 7\" LCD\nMemory: 2000 results\nPower: 220V AC\nWeight: 3.5 kg");
            productRepo.save(p6);

            log.info("Created {} products", productRepo.count());
            log.info("Database seeding complete!");
        };
    }
}
