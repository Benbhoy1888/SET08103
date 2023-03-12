import com.napier.sem.App;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Project tests
 * Run using Maven by opening Maven view ->
 * Select "test" lifecycle stage
 *
 * Tests should be written in a separate file for each class.
 * Ensure to add file to main.yml for Unit tests run line(s)
 */
public class ExampleTests
{
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

    // can add a message to a test
    @Test
    void unitTest2()
    {
        assertEquals(5, 5, "Messages are equal");
    }

    // to test floating point values with an error range
    @Test
    void unitTest3()
    {
        assertEquals(5.0, 5.01, 0.02);
    }

    // to compare array contents in a test
    @Test
    void unitTest4()
    {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        assertArrayEquals(a, b);
    }

    // to test if a value is true
    @Test
    void unitTest5()
    {
        assertTrue(5 == 5);
    }

    // to test if a value is false
    @Test
    void unitTest6()
    {
        assertFalse(5 == 4);
    }

    // to test if a value is null
    @Test
    void unitTest7()
    {
        assertNull(null);
    }

    // to test if a value is not null
    @Test
    void unitTest8()
    {
        assertNotNull("Hello");
    }

    // to test if a method throws an exception
    // By default, any exception thrown fails a test if assertThrows doesn't match
    @Test
    void unitTest9()
    {
        assertThrows(NullPointerException.class, this::throwsException);
    }

    void throwsException() throws NullPointerException
    {
        throw new NullPointerException();
    }

}