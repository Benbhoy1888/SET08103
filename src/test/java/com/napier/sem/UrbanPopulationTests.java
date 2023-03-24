package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        ArrayList<Urbanisation> urbanisations = new ArrayList<>();
        app.outputUrbanPopulationReport(urbanisations, "test");
    }

    /**
     * Tests for if an element in ArrayList passed is null, an error is not thrown
     */
    @Test
    void outputUrbanReportsListContainsNull() {
        ArrayList<Urbanisation> urbanisations = new ArrayList<>();

        urbanisations.add(null);

        app.outputUrbanPopulationReport(urbanisations, "test");
    }


    /**
     * Tests if reportType is not recognised, returns null
     */
    @Test
    void getAllUrbanReportTypesNotRecognised() {
        assertNull(app.getTotalUrbanRuralPopulation("Report"));}

    /**
     * Tests if a field is null, an error is not thrown
     */
    @Test
    void outputUrbanReportsNullFieldTest() {
        ArrayList<Urbanisation> urbanisations = new ArrayList<>();
        Urbanisation urban = new Urbanisation();
        urban.Name=null;
        urban.totalPopulation=0;
        urban.cityPopulation=0;
        urban.nonCityPopulation=0;
        urban.cityPopulationPercentage=1.1;
        urban.nonCityPopulationPercentage=1.1;
        urbanisations.add(urban);

        app.outputUrbanPopulationReport(urbanisations, "test");
    }

    /**
     * Test for when ArrayList passed has elements as expected, an error is not thrown
     */
    @Test
    void outputCityReports() {
        ArrayList<Urbanisation> urbanisations = new ArrayList<>();
        Urbanisation urban = new Urbanisation();

        urban.Name= "Edinburgh";
        urban.Name=null;
        urban.totalPopulation=0;
        urban.cityPopulation=0;
        urban.nonCityPopulation=0;
        urban.cityPopulationPercentage=1.1;
        urban.nonCityPopulationPercentage=1.1;

        app.outputUrbanPopulationReport(urbanisations, "test");
    }

    /**
     * Tests for if empty string provided for filename, does not create a file
     */
    @Test
    void outputUrbanReportsEmptyFileNameTest() {
        ArrayList<Urbanisation> urbanisations = new ArrayList<>();
        Urbanisation urban = new Urbanisation();

        urban.Name= "Edinburgh";
        urban.Name=null;
        urban.totalPopulation=0;
        urban.cityPopulation=0;
        urban.nonCityPopulation=0;
        urban.cityPopulationPercentage=1.1;
        urban.nonCityPopulationPercentage=1.1;

        app.outputUrbanPopulationReport(urbanisations, "test");

        File file = new File("./reports/urban_reports/.md");
        if(file.exists()){
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
