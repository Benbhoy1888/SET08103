package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for App class
 */
public class CountryReportTests
{
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
     * Tests if ArrayList passed is null
     */
    @Test
    void outputCountryReportsTestNull() {
        app.outputCountryReport(null, -1, "test");
    }

    /**
     *Tests if ArrayList passed is empty
     */
    @Test
    void outputCountryReportsEmptyListTest() {
        ArrayList<Country> countries = new ArrayList<Country>();
        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null
     */
    @Test
    void outputCountryReportsListContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.outputCountryReport(countries, -1, "test");
    }

    /**
     * Tests if a field is null
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
     * Test for when ArrayList passed has elements as expected
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
     * Tests for if displayN provided is greater than elements in list provided
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
     * Tests for if empty string provided for filename creates a file
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

        File file = new File("./reports/.md");
        if(file.exists()){
            fail("File with empty name created");
        }
    }

    /**
     * Tests when choice is empty for region and continent report types, return is null
     */
    @Test
    void getAllCountriesChoiceEmpty() {
        assertNull(app.getAllCountries("r", ""));
        assertNull(app.getAllCountries("c", ""));
    }

    /**
     * Clears up after tests
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