package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }

    /**
     * Test to check tests actually pass on GitHub actions
     */
    @Test
    void unitTest() {
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

    // Andy to do - Test getAllCountries (and any others which fall into same bracket) does not return null when reportType is empty (to generate world report)

    /**
     * Disconnects from database
     */
    @AfterAll
    static void disconnect() {
        app.disconnect();
    }
}