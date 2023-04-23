package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TotalPopulationTests {
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
    void outputTotalPopulationNullObject() {
        TotalPopulation totalPopulation = null;

        app.outputTotalPopulationReport(totalPopulation);
    }

    /**
     * Tests if a field is null an error is not thrown
     */
    @Test
    void outputTotalPopulationNullField() {
        TotalPopulation totalPopulation = new TotalPopulation();

        totalPopulation.reportType = null;
        totalPopulation.name = null;
        totalPopulation.population = null;


        app.outputTotalPopulationReport(totalPopulation);
    }

    /**
     * Test when object passed as expected an error is not thrown
     */
    @Test
    void outputTotalPopulationReport() {
        TotalPopulation totalPopulation = new TotalPopulation();

        totalPopulation.reportType = "World";
        totalPopulation.name = "World";
        totalPopulation.population = 6078749.45;

        app.outputTotalPopulationReport(totalPopulation);
    }

    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getTotalPopulationTypeNotRecognised() {
        assertNull(app.getTotalPopulation("aaaaa", "Asia"));
    }

    /**
     * Tests when choice is empty for report types other than world, return is null
     */
    @Test
    void getTotalPopulationChoiceEmpty() {
        assertNull(app.getTotalPopulation("con", ""));
        assertNull(app.getTotalPopulation("r", ""));
        assertNull(app.getTotalPopulation("cou", ""));
        assertNull(app.getTotalPopulation("d", ""));
        assertNull(app.getTotalPopulation("ci", ""));
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