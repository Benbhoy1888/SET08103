package com.napier.sem;

import com.napier.sem.App;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

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



    // Need test for connection to database without checking specific return values or inserting values to database

    // Need test to check returned values are as expected

    // Need test to check reports are outputted correctly from docker container

    // test sql queries are correct

    /**
     * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
     * after trying to get country information from database when using
     * an empty string for report type (should generate world report objects list)
     */
    @Test
    void testWorldCountries() {
        // gets world countries information for world report using empty report type
        ArrayList<Country> worldCountries = app.getAllCountries("", "");

        if(worldCountries != null) {
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

    /**
     * Tests object and attributes are not null after trying to get total population information from database when using
     * an empty string for report type (should generate world report object)
     */
//    @Test
//    void testTotalPopulation() {
//        // gets world countries information for world report using empty report type
//        TotalPopulation totalPopulation = app.getTotalPopulation("", "");
//
//        assertNotNull(totalPopulation);
//        assertEquals("World", totalPopulation.reportType);
//        assertNotNull(totalPopulation.name);
//        assertNotEquals(-1.0, totalPopulation.population);
//    }

    /**
     * Disconnects from database
     */
    @AfterAll
    static void disconnect() {
        app.disconnect();
    }
}