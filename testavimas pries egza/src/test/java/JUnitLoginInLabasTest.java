import org.junit.Before;
import org.junit.Test;

public class JUnitLoginInLabasTest {

    @Before
    public void openLabas(){
        Main.setupLabas();
    }

    @Test
    public void loginLabas(){
        Main.loginLabas();
    }
}
