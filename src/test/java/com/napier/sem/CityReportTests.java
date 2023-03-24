package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class CityReportTests {

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
        void outputCityReportsTestNull() {
            app.outputCityReport(null, -1, "test");
        }

        /**
         *Tests if ArrayList passed is empty, an error is not thrown
         */
        @Test
        void outputCityReportsEmptyListTest() {
            ArrayList<City> cities = new ArrayList<>();

            app.outputCityReport(cities, -1, "test");
        }

        /**
         * Tests for if an element in ArrayList passed is null, an error is not thrown
         */
        @Test
        void outputCityReportsListContainsNull() {
            ArrayList<City> cities = new ArrayList<>();

            cities.add(null);

            app.outputCityReport(cities, -1, "test");
        }

        /**
         * Tests if a field is null, an error is not thrown
         */
        @Test
        void outputCityReportsNullFieldTest() {
            ArrayList<City> cities = new ArrayList<>();
            City city = new City();

            city.name = null;
            city.country = null;
            city.district = null;
            city.population = 0;
            cities.add(city);

            app.outputCityReport(cities, -1, "test");
        }

        /**
         * Test for when ArrayList passed has elements as expected, an error is not thrown
         */
        @Test
        void outputCityReports() {
            ArrayList<City> cities = new ArrayList<>();
            City city = new City();

            city.name = "Edinburgh";
            city.country = "United Kingdom";
            city.district = "Western Europe";
            city.population = 450180;
            cities.add(city);

            app.outputCityReport(cities, -1, "test");
        }

        /**
         * Tests for if displayN provided is greater than elements in list provided, an error is not thrown
         */
        @Test
        void outputCityReportsN_MoreThanInListTest() {
            ArrayList<City> cities = new ArrayList<>();
            City city = new City();

            city.name = "Edinburgh";
            city.country = "United Kingdom";
            city.district = "Western Europe";
            city.population = 450180;
            cities.add(city);

            app.outputCityReport(cities, 2, "test");
        }

        /**
         * Tests for if empty string provided for filename, does not create a file
         */
        @Test
        void outputCityReportsEmptyFileNameTest() {
            ArrayList<City> cities = new ArrayList<>();
            City city = new City();

            city.name = "Edinburgh";
            city.country = "United Kingdom";
            city.district = "Western Europe";
            city.population = 450180;
            cities.add(city);

            app.outputCityReport(cities, -1, "test");

            File file = new File("./reports/city_reports/.md");
            if(file.exists()){
                fail("File with empty name created");
            }
        }

        /**
         * Tests if reportType is not recognised, returns null
         */
        @Test
        void getAllCitiesTypeNotRecognised() {
            assertNull(app.getAllCities("aaaaa", "Funky Town"));
        }

        /**
         * Tests when choice is empty for report types other than world, returns null
         */
        @Test
        void getAllCitiesChoiceEmpty() {
            assertNull(app.getAllCities("r", ""));
            assertNull(app.getAllCities("c", ""));
            assertNull(app.getAllCities("co", ""));
            assertNull(app.getAllCities("d", ""));
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
