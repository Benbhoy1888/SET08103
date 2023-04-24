package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

 class CapitalReportTests {

     static App app;


     /**
      * Creates a new app
      */
     @BeforeAll
     static void init() {
         app = new App();
     }

     /**
      * Tests if ArrayList passed is null
      */
     @Test
     void outputCapitalCitiesReportsTestNull() {
         app.outputCapitalCitiesReport(null, -1, "test");
     }

     /**
      * Tests if ArrayList passed is empty
      */
     @Test
     void outputCapitalCitiesReportsEmptyListTest() {
         ArrayList<Capital> capitalCities = new ArrayList<>();

         app.outputCapitalCitiesReport(capitalCities, -1, "test");

     }

     /**
      * Tests for if an element in ArrayList passed is null
      */
     @Test
     void outputCapitalCitiesReportsListContainsNull() {
         ArrayList<Capital> capitalCities = new ArrayList<>();

         capitalCities.add(null);

         app.outputCapitalCitiesReport(capitalCities, -1, "test");

     }

     /**
      * Tests if a field is null
      */
     @Test
     void outputCapitalCitiesReportsNullFieldTest() {
         ArrayList<Capital> capitalCities = new ArrayList<>();
         Capital capital = new Capital();

         capital.name = null;
         capital.population = 0; // int field
         capital.country = null;
         capitalCities.add(capital);

         app.outputCapitalCitiesReport(capitalCities, -1, "test");
     }

     /**
      * Test for when ArrayList passed has elements as expected
      */
     @Test
     void outputCapitalCitesReports() {
         ArrayList<Capital> capitalCities = new ArrayList<>();
         Capital capital = new Capital();
         capital.name = "Canberra";
         capital.population = 18886000;
         capital.country = "Australia";

         capitalCities.add(capital);

         app.outputCapitalCitiesReport(capitalCities, -1, "test");
     }

     /**
      * Tests for if displayN provided is greater than elements in list provided
      */
     @Test
     void outputCapitalCitiesReportsN_MoreThanInListTest() {
         ArrayList<Capital> capitalCities = new ArrayList<>();
         Capital capital = new Capital();
         capital.name = "Canberra";
         capital.population = 18886000;
         capital.country = "Australia";
         capitalCities.add(capital);
         app.outputCapitalCitiesReport(capitalCities, 2, "test");
     }

     /**
      * Tests for if empty string provided for filename creates a file
      */
     @Test
     void outputCapitalCitiesReportsEmptyFileNameTest() {
         ArrayList<Capital> capitalCities = new ArrayList<>();
         Capital capital = new Capital();
         capital.name = "Canberra";
         capital.population = 18886000;
         capital.country = "Australia";
         capitalCities.add(capital);
         app.outputCapitalCitiesReport(capitalCities, -1, "test");

         File file = new File("./reports/capital_Cities/.md");
         if (file.exists()) {
             fail("File with empty name created");
         }
     }

     /**
      * Tests if reportType is not recognised,return null
      */
     @Test
     void getAllCapitalCitiesTypeNotRecognised() {
         assertNull(app.getAllCapitalCites("", ""));
     }

     /**
      * Tests when choice is empty for region and continent report types, return is null
      */
     @Test
     void getAllCapitalCitiesChoiceEmpty() {
         assertNull(app.getAllCapitalCites("r", ""));
         assertNull(app.getAllCapitalCites("c", ""));
     }

     /**
      * Clears up after tests
      * Deletes created test file
      */
     @AfterAll
     static void clearUp() {
         //deletes reports directory
         File directory = new File("./reports/test.md");
         File[] entries = directory.listFiles();
         if (entries != null) {
             for (File entry : entries) {
                 entry.delete();
             }
                directory.delete();
         }
     }
 }
