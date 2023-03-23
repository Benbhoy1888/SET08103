package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;

public class UrbanPopulationTests {

    static App app;

    /**
     * Creates a new app
     */
    @BeforeAll
    static void init(){
        app = new App();
    }

    /**
     * Test if ArrayList passed is null
     */
    @Test
    void outputUrbanPopulationNullTest() {
        app.outputUrbanPopulationReport(null, "test");
    }

    /**
     * Test if ArrayList passed is empty
     */
    @Test
    void outputUrbanPopulationEmptyTest() {
        ArrayList<Population> populations = new ArrayList<>();
        app.outputUrbanPopulationReport(populations, "test");
    }


    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getAllUrbanReportTypesNotRecognised() {
        assertNull(app.getTotalUrbanRuralPopulation("Report"));}


    /**
     * Clears up post test
     * Deletes created test file
     */
    @AfterAll
    static void clearUp() {
        File file = new File("./reports/test.md");
        if(file.exists()){
            file.delete();
        }
        file = new File("./reports/.md");
        if(file.exists()){
            file.delete();
        }

    }

}
