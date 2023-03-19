package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class PopulationReportTests {

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
    void outputPopulationReportsTestNull() {
        app.outputPopulationReport(null, -1, "test");
    }

    /**
     *Tests if ArrayList passed is empty, an error is not thrown
     */
    @Test
    void outputPopulationReportsEmptyListTest() {
        ArrayList<Population> populations = new ArrayList<>();

        app.outputPopulationReport(populations, -1, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null, an error is not thrown
     */
    @Test
    void outputPopulationReportsListContainsNull() {
        ArrayList<Population> populations = new ArrayList<>();

        populations.add(null);

        app.outputPopulationReport(populations, -1, "test");
    }

    /**
     * Tests if a field is null, an error is not thrown
     */
    @Test
    void outputPopulationReportsNullFieldTest() {
        ArrayList<Population> populations = new ArrayList<>();
        Population population = new Population();

        population.country_Name = null;
        population.country_Continent = null;
        population.country_Region= null;
        population.country_Population = null;
        populations.add(population);

        app.outputPopulationReport(populations, -1, "test");
    }

    /**
     * Test for when ArrayList passed has elements as expected, an error is not thrown
     */
    @Test
    void outputPopulationReports() {
        ArrayList<Population> populations = new ArrayList<>();
        Population population = new Population();

        population.country_Name = "United Kingdom";
        population.country_Continent = "Europe";
        population.country_Region= "British Islands";
        population.country_Population = 59623400;
        populations.add(population);


        app.outputPopulationReport(populations, -1, "test");
    }

    /**
     * Tests for if displayN provided is greater than elements in list provided, an error is not thrown
     */
    @Test
    void outputPopulationReportsN_MoreThanInListTest() {
        ArrayList<Population> populations = new ArrayList<>();
        Population population = new Population();


        population.country_Name = "United Kingdom";
        population.country_Continent = "Europe";
        population.country_Region= "British Islands";
        population.country_Population = 59623400;
        populations.add(population);


        app.outputPopulationReport(populations, 2, "test");
    }

    /**
     * Tests for if empty string provided for filename, does not create a file
     */
    @Test
    void outputPopulationReportsEmptyFileNameTest() {
        ArrayList<Population> populations = new ArrayList<>();
        Population population = new Population();


        population.country_Name = "United Kingdom";
        population.country_Continent = "Europe";
        population.country_Region= "British Islands";
        population.country_Population = 59623400;
        populations.add(population);


        app.outputPopulationReport(populations, -1, "");

        File file = new File("./reports/population_reports/.md");
        if(file.exists()){
            fail("File with empty name created");
        }
    }

    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getAllPopulationsTypeNotRecognised() {
        assertNull(app.getAllPopulations("aaaaa", 0));
    }

    /**
     * Tests when choice is empty for report types other than world, returns null
     */
    @Test
    void getAllPopulationsChoiceEmpty() {
        assertNull(app.getAllPopulations("r", ""));
        assertNull(app.getAllPopulations("c", ""));
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

