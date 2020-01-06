import org.junit.Before;
import org.junit.Test;

public class IrasuKurimas {

    @Before
    public void setup(){
        Main.setupFilmaiLt();
    }

    @Test
    public void sekmingasIrasymas(){
        Main.sekmingasIrasoKurimas();
    }

    @Test
    public void nesekmingasIrasymas(){
        Main.nesekmingasIrasoKurimas();
    }


}
