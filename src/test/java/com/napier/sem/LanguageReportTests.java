package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

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

        app.outputLanguageReport(null, "test");
    }


    /**
     * Test when object passed as expected an error is not thrown
     */
    @Test
    void outputLanguageReport() {
        ArrayList<Language> languages = new ArrayList<>();
        Language language = new Language();

        language.languageName = "English";
        language.population = 93;

        app.outputLanguageReport(null,"test");
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

