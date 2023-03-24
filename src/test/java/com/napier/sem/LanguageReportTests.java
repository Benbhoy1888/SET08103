package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
public class LanguageReportTests {
    static App app;

    /**
     * Creates a new app
     */
    @BeforeAll
    static void init() {
        app = new App();
    }

    /**
     *Tests if object passed is null and error is not thrown
     */
    @Test
    void outputLanguageReportNullObject() {
        Language language = null;

        app.outputLanguageReport(language);
    }

    /**
     * Tests if a field is null an error is not thrown
     */
    @Test
    void outputLanguageReportNullField() {
        Language language = new Language();

        language.countryCode = null;
        language.languageName = null;
        language.population = 0;


        app.outputLanguageReport(language);
    }

    /**
     * Test when object passed as expected an error is not thrown
     */
    @Test
    void outputLanguageReport() {
        Language language = new Language();

        language.countryCode = "GBR";
        language.languageName = "English";
        language.population = 93.7;

        app.outputLanguageReport(language);
    }


    /**
     * Clears up after tests
     * Deletes reports directory
     */
    @AfterAll
    static void clearUp() {
        // deletes reports directory
        File directory = new File("./reports");
        File[] entries = directory.listFiles();
        if (entries != null) {
            for (File entry : entries) {
                entry.delete();
            }
            directory.delete();
        }
    }
}

