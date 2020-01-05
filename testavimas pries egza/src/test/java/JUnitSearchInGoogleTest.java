import org.junit.Before;
import org.junit.Test;


public class JUnitSearchInGoogleTest {

    @Before
    public void openGoogle(){
        Main.setupGoogle();
    }

    @Test
    public void searchInGoogle(){
        Main.keywordsSearch(Main.keywords);
    }
}
