package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

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
    void outputCountryPopulationReportsTestNull() {
        app.outputUrbanPopulationReport(null, "test");
    }

    /**
     * Test if ArrayList passed is empty
     */
    @Test
    void outputCountryPopulationReportsEmptyListTest() {
        ArrayList<Population> populations = new ArrayList<Population>();
        app.outputUrbanPopulationReport(populations, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null
     */
    @Test
    void outputCountryReportsListContainsNull() {
        ArrayList<Population> populations = new ArrayList<>();
        populations.add(null);
        app.outputUrbanPopulationReport(populations, "test");
    }

    /**
     * Test if a field is null
     */
    @Test
    void outputPopulationReportsNullFieldTest() {
        ArrayList<Population> populationCountry = new ArrayList<>();
        Population population = new Population();
        population.Name = null;
        populationCountry.add(population);
        app.outputUrbanPopulationReport(populationCountry, "test");
    }

    /**
     * Test if a field is filled as expected
     */
    @Test
    void outputPopulationReports() {
        ArrayList<Population> urbanPopulation = new ArrayList<>();
        Population population = new Population();
        population.Name = "United Kingdom";
        urbanPopulation.add(population);
        app.outputUrbanPopulationReport(urbanPopulation, "test");
    }


    /**
     * Test if a field is filled with non existing-country
     */
    @Test
    void outputPopulationReportsNonExistingCountry() {
        ArrayList<Population> populationCountry = new ArrayList<>();
        Population population = new Population();
        population.Name = "United Kingdom";
        populationCountry.add(population);
        app.outputUrbanPopulationReport(populationCountry, "test");
    }

    /**
     * Tests for if empty string provided for filename, crates a file
     *
     */

    @Test
    void outputPopulationReportsEmptyFileNameTest() {
        ArrayList<Population> populationCountry = new ArrayList<>();
        Population population = new Population();
        population.Name = "United Kingdom";
        populationCountry.add(population);
        app.outputUrbanPopulationReport(populationCountry, "test");

        File file = new File("./reports/.md");
        if(file.exists()) {
            fail("File with empty name created");
        }
    }

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
