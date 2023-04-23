package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {

    static App app;

    /**
     * Sets up db connection for tests
     */
    @BeforeAll
    static void init() {
        app = new App();
        app.connect("localhost:33060", 30000);

        // deletes reports directory
        File directory = new File("./reports");
        if (directory.exists()) {
            directory.delete();
        }
    }

    /**
     * Test to check tests actually pass on GitHub actions
     */
    @Test
    void integrationGitHubTest() {
        assertEquals(5, 5);
    }


    /**
     * Tests database connection
     */
    @Test
    public void testConnection() {
        Connection con = app.getConnection();
        try {
            con.isValid(30);
        } catch (SQLException sqle) {
            System.out.println("No connection");
            fail(sqle.getMessage());
        }
    }

    /**
     * <<<<<<< HEAD
     * develop
     * =======
     * <p>
     * >>>>>>> adding_markdownfiles_to_the_app_feature-emma
     * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
     * after trying to get country information from database when using
     * an empty string for report type (should generate world report objects list)
     */
    @Test
    void testWorldCountries() {
        // gets world countries information for world report using empty report type
        ArrayList<Country> worldCountries = app.getAllCountries("", "");

        if (worldCountries != null) {
            assertTrue(worldCountries.size() > 0);
            assertNotNull(worldCountries.get(0).code);
            assertNotNull(worldCountries.get(0).name);
            assertNotNull(worldCountries.get(0).continent);
            assertNotNull(worldCountries.get(0).region);
            assertNotEquals(-1, worldCountries.get(0).population);
            assertNotNull(worldCountries.get(0).capital);
        } else {
            fail("getAllCountries returning Null pointer");
        }
    }


    @Test

    void testWorldCities() {
        // gets world countries information for world report using empty report type
        ArrayList<City> worldCities = app.getAllCities("", "");

        if (worldCities != null) {
            assertTrue(worldCities.size() > 0);
            assertNotNull(worldCities.get(0).name);
            assertNotNull(worldCities.get(0).country);
            assertNotNull(worldCities.get(0).district);
            assertNotEquals(-1, worldCities.get(0).population);
        } else {
            fail("getAllCities returning Null pointer");

        }
    }


    @Test
            void testTotalUrbanRuralPopulation() {
        // gets world countries information for world report using empty report type
        ArrayList<Urbanisation> ruralPopulation = app.getTotalUrbanRuralPopulation("con");

        if (ruralPopulation != null) {
            assertTrue(ruralPopulation.size() > 0);
            Assertions.assertNotNull(ruralPopulation.get(0).Name);
            assertNotEquals(-1, ruralPopulation.get(0).totalPopulation);
            assertNotEquals(-1, ruralPopulation.get(0).cityPopulation);
            assertNotEquals(-1.0, ruralPopulation.get(0).cityPopulationPercentage);
            assertNotEquals(-1, ruralPopulation.get(0).nonCityPopulation);
        } else fail("getTotalUrbanRuralPopulation returning Null pointer");
    }


    /**
     * Tests object and attributes are not null after trying to get total population information from database when using
     * an empty string for report type (should generate world report object)
     */
    @Test
    void testTotalPopulation() {
        // gets world countries information for world report using empty report type
        TotalPopulation totalPopulation = app.getTotalPopulation("", "");

        assertNotNull(totalPopulation);
        assertEquals("World", totalPopulation.reportType);
        assertNotNull(totalPopulation.name);
        assertNotEquals(-1.0, totalPopulation.population);
    }


    /**
     * //     * Tests object and attributes are not null after trying to get total population information from database when using
     * //     * an empty string for report type (should generate world report object)
     * //
     */
    @Test
    void testTotalPopulation() {
        // gets world countries information for world report using empty report type
        TotalPopulation totalPopulation = app.getTotalPopulation("", "");

        assertNotNull(totalPopulation);
        assertEquals("World", totalPopulation.reportType);
        assertNotNull(totalPopulation.name);
        assertNotEquals(-1.0, totalPopulation.population);

    }
        /**
         * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
         * after trying to get country information from database when using
         * an empty string for report type (should generate world report objects list)
         */
        // gets world capital cities information for world report using empty report type
    @Test
    void testWorldCapitalCities () {
            ArrayList<Capital> worldCapitalCities = app.getAllCapital("", "");


            if (worldCapitalCities != null) {
                assertTrue(((ArrayList<Capital>) worldCapitalCities).size() > 0);
                assertNotNull(worldCapitalCities.get(0).name);
                assertNotNull(worldCapitalCities.get(0).country);
                assertNotEquals(-1, worldCapitalCities.get(0).population);

            } else {
                fail("getAllCapitalCities returning Null pointer");

            }
        }


   /**z
     * Tests object and attributes are not null after trying to get language information from database
     */
        @Test
        void testLanguage () {
            // gets world countries information for world report using empty report type
            Language language = app.getLanguage();


            if (language != null) {
                assertNotNull(language);
                assertNotNull(language.countryCode);
                assertNotNull(language.languageName);
                assertNotEquals(-1, language.population);
            } else {
                fail("language returning Null pointer");
            }
        }


        /**
         * Disconnects from database
         */
        @AfterAll
        static void disconnect () {
            app.disconnect();
        }
    }
}
