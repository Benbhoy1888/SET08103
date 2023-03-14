package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for App class
 */
public class AppTests
{
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
     * Test to check tests actually pass on GitHub actions
     */
    @Test
    void unitTest() {
        assertEquals(5, 5);
    }
}