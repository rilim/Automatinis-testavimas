import org.junit.Before;
import org.junit.Test;

public class IrasoRedagavimas {
    @Before
    public void setup(){
        Main.setupFilmaiLt();
    }

    @Test
    public void sekmingasRedagavimas(){
        Main.sekmingasRedagavimas();
    }

    @Test
    public void nesekmingasRedagavimas(){
        Main.nesekmingasRedagavimas();
    }

}
