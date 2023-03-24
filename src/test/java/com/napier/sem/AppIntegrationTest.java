package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {

    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 0);

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



    // Emma Need test for connection to database without checking specific return values or inserting values to database
    @Test
      static void databaseTest() {
        if (app.connect("localhost:3306",3000) = null) {
            app.disconnect();
            System.out.println("sorry please try and reconnect to the database");
            return;
        } else {
            app.connect("localhost:3306", 3000);
        }
    }
    // Emma Need test to check returned values are as expected

    // Andy to do - Need test to check reports are outputted correctly from docker container

    // Need test to check reports are outputted correctly from docker container

    // Test getAllCountries (and any others which fall into same bracket) does not return null when reportType is empty (to generate world report)

    /**
     * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
     * after trying to get country information from database when using
     * an empty string for report type (should generate world report objects list)
     */
    @Test
    void testWorldCities() {
        // gets world countries information for world report using empty report type
        ArrayList<City> worldCities = app.getAllCities("", "");

        if(worldCities != null) {
            assertTrue(worldCities.size() > 0);
            assertNotNull(worldCities.get(0).name);
            assertNotNull(worldCities.get(0).country);
            assertNotNull(worldCities.get(0).district);
            assertNotEquals(-1, worldCities.get(0).population);
        } else {
            fail("getAllCities returning Null pointer");
        }
    }

    /**
     * Tests object and attributes are not null after trying to get language information from database
     */
 //   @Test
//    void testLanguage() {
        // gets world countries information for world report using empty report type
//        Language language = app.getLanguage();



//        if(language != null) {
//            assertNotNull(language);
//            assertNotNull(language.countryCode);
//            assertNotNull(language.languageName);
//            assertNotEquals(-1, language.population);
 //       } else {
//            fail("language returning Null pointer");
//        }
//   }



    /**
     * Disconnects from database
     */
    @AfterAll
    static void disconnect() {
        app.disconnect();
    }
}