package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CapitalReportTests {
    static App app;

    /**
     * Creates a new app
     */
    @BeforeAll
    static void init()
    {
        app = new App();
    }

    /**
     * Tests if ArrayList passed is null, an error is not thrown
     */
    @Test
    void outputCapitalReportsTestNull() {
        app.outputCapitalReport(null, -1, "test");
    }

    /**
     *Tests if ArrayList passed is empty, an error is not thrown
     */
    @Test
    void outputCapitalReportsEmptyListTest() {
        ArrayList<Capital> capitals = new ArrayList<>();

        app.outputCapitalReport(capitals, -1, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null, an error is not thrown
     */
    @Test
    void outputCapitalReportsListContainsNull() {
        ArrayList<Capital> capitals = new ArrayList<>();

        capitals.add(null);

        app.outputCapitalReport(capitals, -1, "test");
    }

    /**
     * Tests if a field is null, an error is not thrown
     */
    @Test
    void outputCapitalReportsNullFieldTest() {
        ArrayList<Capital> capitals = new ArrayList<>();
        Capital capital = new Capital();

        capital.name = null;
        capital.country = null;
        capital.population = 0;
        capitals.add(capital);

        app.outputCapitalReport(capitals, -1, "test");
    }

    /**
     * Test for when ArrayList passed has elements as expected, an error is not thrown
     */
    @Test
    void outputCapitalReports() {
        ArrayList<Capital> capitals = new ArrayList<>();
        Capital capital = new Capital();

        capital.name = "Edinburgh";
        capital.country = "United Kingdom";
        capital.population = 450180;
        capitals.add(capital);

        app.outputCapitalReport(capitals, -1, "test");
    }

    /**
     * Tests for if displayN provided is greater than elements in list provided, an error is not thrown
     */
    @Test
    void outputCapitalReportsN_MoreThanInListTest() {
        ArrayList<Capital> capitals = new ArrayList<>();
        Capital capital = new Capital();

        capital.name = "Edinburgh";
        capital.country = "United Kingdom";
        capital.population = 450180;
        capitals.add(capital);

        app.outputCapitalReport(capitals, 2, "test");
    }

    /**
     * Tests for if empty string provided for filename, does not create a file
     */
    @Test
    void outputCapitalReportsEmptyFileNameTest() {
        ArrayList<Capital> capitals = new ArrayList<>();
        Capital capital = new Capital();

        capital.name = "Edinburgh";
        capital.country = "United Kingdom";
        capital.population = 450180;
        capitals.add(capital);
        app.outputCapitalReport(capitals, -1, "");

        File file = new File("./reports/capital_reports/.md");
        if(file.exists()){
            fail("File with empty name created");
        }
    }

    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getAllCapitalsTypeNotRecognised() {
        assertNull(app.getAllCapitals("aaaaa", "Funky Town"));
    }

    /**
     * Tests when choice is empty for report types other than world, returns null
     */
    @Test
    void getAllCapitalsChoiceEmpty() {
        assertNull(app.getAllCapitals("r", ""));
        assertNull(app.getAllCapitals("c", ""));
    }

    /**
     * Clears up after tests
     * Deletes reports directory including any files created in tests
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