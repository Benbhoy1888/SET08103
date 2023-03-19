package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for App class
 */
public class CountryReportTests {
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
    void outputCountryReportsTestNull() {
        app.outputCountryReport(null, -1, "test");
    }

    /**
     *Tests if ArrayList passed is empty, an error is not thrown
     */
    @Test
    void outputCountryReportsEmptyListTest() {
        ArrayList<Country> countries = new ArrayList<>();

        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null, an error is not thrown
     */
    @Test
    void outputCountryReportsListContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();

        countries.add(null);

        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Tests if a field is null, an error is not thrown
     */
    @Test
    void outuputCountryReportsNullFieldTest() {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();

        country.code = null;
        country.name = null;
        country.continent = null;
        country.region = null;
        country.population = 0; // int field
        country.capital = null;

        countries.add(country);

        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Test for when ArrayList passed has elements as expected, an error is not thrown
     */
    @Test
    void outputCountryReports() {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();

        country.code = "AUS";
        country.name = "Australia";
        country.continent = "Oceania";
        country.region = "Australia and New Zealand";
        country.population = 18886000;
        country.capital = "Canberra";

        countries.add(country);

        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Tests for if displayN provided is greater than elements in list provided, an error is not thrown
     */
    @Test
    void outputCountriesReportsN_MoreThanInListTest() {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();

        country.code = "AUS";
        country.name = "Australia";
        country.continent = "Oceania";
        country.region = "Australia and New Zealand";
        country.population = 18886000;
        country.capital = "Canberra";

        countries.add(country);

        app.outputCountryReport(countries, 2, "test");
    }

    /**
     * Tests for if empty string provided for filename, does not create a file
     */
    @Test
    void outputCountriesReportsEmptyFileNameTest() {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country();

        country.code = "AUS";
        country.name = "Australia";
        country.continent = "Oceania";
        country.region = "Australia and New Zealand";
        country.population = 18886000;
        country.capital = "Canberra";

        countries.add(country);

        app.outputCountryReport(countries, -1, "");

        File file = new File("./reports/country_reports/.md");
        if(file.exists()){
            fail("File with empty name created");
        }
    }

    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getAllCountriesTypeNotRecognised() {
        assertNull(app.getAllCountries("aaaaa", "Asia"));
    }

    /**
     * Tests when choice is empty for report types other than world, returns null
     */
    @Test
    void getAllCountriesChoiceEmpty() {
        assertNull(app.getAllCountries("r", ""));
        assertNull(app.getAllCountries("c", ""));
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