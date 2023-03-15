package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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



    // Need test for connection to database without checking specific return values or inserting values to database

    // Need test to check returned values are as expected

    /**
     * Disconnects from database
     */
    @AfterAll
    static void disconnect() {
        app.disconnect();
    }
}