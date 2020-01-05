import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SeleniumTest {

    @BeforeTest
    public void testSetup() {
        Selenium.setup();
    }

    @Test
    public void testSearchByKeyword() {
        Selenium.searchByKeyword("Baranauskas");
    }

    @AfterTest
    public void testClose() {
        //Selenium.close();
    }

}
