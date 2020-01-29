package com.etilize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Represents the Application class which houses the main entry-point to run the application
 *
 * @author Faisal Feroz
 *
 */
@SpringBootApplication
public class EmbeddedRethinkdbDatabaseApplication {

    /**
     * protected constructor
     */
    EmbeddedRethinkdbDatabaseApplication() {
    }

    /**
     * main entry-point
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EmbeddedRethinkdbDatabaseApplication.class, args);
    }
}
