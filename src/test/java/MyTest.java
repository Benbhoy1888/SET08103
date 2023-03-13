import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MyTest {

    @Test
    void unitTest1()
    {
        assertEquals(5,4, "Messages are not equal");
    }

    @Test
    void unitTest2()
    {
        assertEquals(5,5, "Messages are equal");

    }

}

