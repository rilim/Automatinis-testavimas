import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {
    static final int WEBPAGE_LOAD_TIME_IN_SEC = 2;
    static final String WEBPAGE_SEARCH_FIELD_ID = "sb_form_q";
    static final String WEBPAGE_SEARCH_BUTTON_ID = "sb_form_go";
    static WebDriver browser;

    public static void setup() {
        // nurodomas kelias iki failo chromedriver.exe (kokią naršyklę paleidžiame)
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\moksleivis\\Desktop\\Naujas aplankas\\selenium69\\driver\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://www.bing.com");
    }

    public static void close() {
        browser.close();
    }

    public static void searchByKeyword(String keyword) {
        // Palaukiame kol bus užkrautas elementas
        waitForElementById(WEBPAGE_SEARCH_FIELD_ID);

        //surandamas paieškos laukelis puslapyje, į kurį įrašysime suformuotą užklausą
        WebElement searchField = browser.findElement(By.id(WEBPAGE_SEARCH_FIELD_ID));

        // įrašoma suformuota paieškos užklausa į paieškos laukelį
        searchField.sendKeys(keyword);
        // simuliacija enter paspaudimo arba tada reiktų ieškoti mygtuko (pele)

        // Palaukiame kol bus užkrautas elementas
        //waitForElementById(WEBPAGE_SEARCH_BUTTON_ID);
       // WebElement paieskosMygtukas = browser.findElement(By.id(WEBPAGE_SEARCH_FIELD_ID));

        searchField.sendKeys(Keys.ENTER);

        //paieskosMygtukas.sendKeys(Keys.ENTER);

        waitForElementByXpath("//*[@id=\"b_tween\"]/span");
        WebElement resultField = browser.findElement(By.xpath("//*[@id=\"b_tween\"]/span"));

        // 116,000 Rezultatai -> 116000
        String results = resultField.getText().replaceAll("[, A-Za-z]", "");

        int results2 = Integer.parseInt(results);
        if (results2 > 0){
            System.out.println("Paieška veikia");
        }   else {
            System.out.println("Paieška neveikia");
        }
        System.out.println (results2);

    }

    static private void waitForElementById(String id){
        WebDriverWait waitas=new WebDriverWait(browser,WEBPAGE_LOAD_TIME_IN_SEC);
        waitas.until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    static private void waitForElementByName(String name){
        WebDriverWait waitas=new WebDriverWait(browser,WEBPAGE_LOAD_TIME_IN_SEC);
        waitas.until(ExpectedConditions.elementToBeClickable(By.name(name)));
    }

    static private void waitForElementByXpath(String xpath){
        WebDriverWait waitas=new WebDriverWait(browser,WEBPAGE_LOAD_TIME_IN_SEC);
        waitas.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static void main(String args[]) {
        System.out.println("Selenium");
        //searchByKeyword("Galvanauskas");
    }
}
