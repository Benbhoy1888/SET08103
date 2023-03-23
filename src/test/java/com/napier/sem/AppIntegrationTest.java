package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
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
     * Tests ruralPopulation array for the following...
     * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
     * after trying to get country information from database when using
     * an empty string for report type (should generate world report objects list)
     */
    @Test
    void testUrbanPopulation() {
        // gets world countries information for world report using empty report type
        ArrayList<Population> ruralPopulation = app.getTotalUrbanRuralPopulation("");

        if(ruralPopulation != null) {
            assertTrue(ruralPopulation.size() > 0);
            assertNotNull(ruralPopulation.get(0).Name);
            assertNotNull(ruralPopulation.get(0).totalPopulation);
            assertNotNull(ruralPopulation.get(0).cityPopulation);
            assertNotNull(ruralPopulation.get(0).nonCityPopulation);
            assertNotEquals(-1, ruralPopulation.get(0).totalPopulation);
        } else {
            fail("getTotalUrbanRuralPopulation returning Null pointer");
        }
    }

    /**
     * Tests ruralWorldPopulation array for the following...
     * Tests ArrayList is contains at least 1 object and that the first objects attributes are not null
     * after trying to get country information from database when using
     * an empty string for report type (should generate world report objects list)
     */
    @Test
    void testUrbanWorldPopulation() {
        // gets world countries information for world report using empty report type
        ArrayList<Population> ruralWorldPopulation = app.getTotalUrbanRuralPopulationWorld();

        if(ruralWorldPopulation != null) {
            assertTrue(ruralWorldPopulation.size() > 0);
            assertNotNull(ruralWorldPopulation.get(0).totalPopulation);
            assertNotNull(ruralWorldPopulation.get(0).cityPopulation);
            assertNotNull(ruralWorldPopulation.get(0).nonCityPopulation);
            assertNotEquals(-1, ruralWorldPopulation.get(0).totalPopulation);
        } else {
            fail("getTotalUrbanRuralPopulationWorld returning Null pointer");
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