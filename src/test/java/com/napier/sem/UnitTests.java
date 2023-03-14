package com.napier.sem;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project Unit tests
 * Run using Maven by opening Maven view ->
 * Select "test" lifecycle stage
 *
 * Tests should be written in a separate file for each class.
 * Ensure to add file to main.yml for Unit tests run line(s)
 */
public class UnitTests {
    // Use to setup things before test, e.g.:
    @BeforeAll
    static void init() {
        App app = new App();
    }


    //Example unit test: ..........

    // tests if first parameter (the output) is equal to the the 2nd parameter (or method output)
    @Test
    void unitTest() {
        assertEquals(5, 5);
    }
}
