import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {

    static WebDriver browser;
    static final String WEBPAGE_SEARCH_FIELD_ID = "sb_form_q";
    static final int WEBPAGE_LOAD_TIME_IN_SEC = 2;

    static public void setup(){
        System.setProperty("webdriver.opera.driver", "drivers\\operadriver.exe"); //nurodomas kelias iki chromo draiverio
        browser = new OperaDriver();
        browser.get("https://www.bing.com/");
    }

    public static void close(){
        browser.close();
    }

    static public void searchByKeyword(String keyword){
        //setup();

        //palaukiam kol bus užkrautas elementas
        waitForElementById();

        //kintamasis pagal kurį ieškosime Binge
//        String keyword = "Baranauskas";

        //Surandamas paieškos laukelis puslapyje, į kurį įrašysime suformuot užklausą
        WebElement searchField = browser.findElement(By.id(WEBPAGE_SEARCH_FIELD_ID));

        //įrašoma suformuota paieškos užklausa į paieškos laukelį
        searchField.sendKeys(keyword);
        //simuliuojamas Enter paspaudimas
        searchField.sendKeys(Keys.ENTER);

        //palaukiam kol bus užkrautas elementas
        waitForElementById();

       // close();
    }

    /**
     * Funkcija, kuri palaukia kol užsikrauna elementas
     *
     */
    static private void waitForElementById(){
        WebDriverWait waitas = new WebDriverWait(browser, WEBPAGE_LOAD_TIME_IN_SEC);
        waitas.until(ExpectedConditions.elementToBeClickable(By.id(WEBPAGE_SEARCH_FIELD_ID)));
    }

    /**
     * Funkcija, kuri palaukia kol užsikrauna elementas
     * @param name elemento pavadinimas iš HTML kodo
     */
    static private void waitForElementByName(String name){
        WebDriverWait waitas = new WebDriverWait(browser, WEBPAGE_LOAD_TIME_IN_SEC);
        waitas.until(ExpectedConditions.elementToBeClickable(By.name(name)));
    }



    public static void main(String[] args){
        System.out.println("Selenium");
        //searchByKeyword("Baranauskas");
    }

}
