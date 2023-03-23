package com.napier.sem;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {

    static App app;

    @BeforeAll
    static void init() {
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

    // Emma Need test to check returned values are as expected

    // Andy to do - Need test to check reports are outputted correctly from docker container

    // Need test to check reports are outputted correctly from docker container

    // Test getAllCountries (and any others which fall into same bracket) does not return null when reportType is empty (to generate world report)

    /**
     * Disconnects from database
     */
    @AfterAll
    static void disconnect() {
        app.disconnect();
    }
}