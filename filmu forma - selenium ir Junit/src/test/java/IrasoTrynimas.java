import org.junit.Before;
import org.junit.Test;

public class IrasoTrynimas {
    @Before
    public void setup(){
        Main.setupFilmaiLt();
    }

    @Test
    public void irasoTrynimas(){
        Main.trinti();
    }
}
