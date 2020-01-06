import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static javax.swing.text.html.CSS.getAttribute;
import static org.junit.Assert.assertTrue;


public class Main {

    static WebDriver chromeBrowser;

    public static void main(String args[]){


    }

    /**
     * Funkcija, kuri suranda naršyklės Chrome draiverius, tuomet sukuriamas naršyklės objektas,
     * kuris bus naudojamas atidarant tinklalapius.
     * Naršyklė atidaroma inPrivate lange
     */
    public static void setup(){
        //Sukuria Chrome naršyklės objektą
        ChromeOptions opts = new ChromeOptions();
        //į sukurtą objektą įdeda reikšmę, kurios dėka naršyklė bus atidaryta inPrivate lange
        opts.addArguments("-private");
        /*naršyklės draiveriai randami automatiškai - nereikia jų pačiam parsisiųsti ir nurodyti kelio.
         * Tai galima dėl to, nes pom.xml faile įdėtas webdrivermanager dependency*/
        WebDriverManager.chromedriver().setup();
        chromeBrowser = new ChromeDriver(opts); //kaip argumentą įdedam objektą, kuris nustatytas naršyklę atidaryti inPrivate lange
    }

    /**
     * Funkcija, kuri atidaro testuojamą tinklalapį
     */
    public static void setupFilmaiLt(){
        setup();
        chromeBrowser.get("http://localhost/selenium/filmai.php");
    }

    /**
     * Funkcija, kuri suranda id laukelį ir įrašo nurodytą id
     * @param nr nurodomas id (String)
     */
    public static void id(String nr){
        chromeBrowser.findElement(By.name("id")).sendKeys(nr);
    }

    /**
     * Funkcija, kuri suranda pavadinimo laukelį ir įrašo nurodytą pavadinimą
     * @param pav nurodomas pavadinimas
     */
    public static void pavadinimas(String pav){
        chromeBrowser.findElement(By.xpath("//*[@id=\"form\"]/form/input[2]")).sendKeys(pav);
    }

    /**
     * Funkcija, kuri suranda žanro laukelį ir įrašo nurodytą žanrą
     * @param zanras nurodomas žanras
     */
    public static void zanras(String zanras){
        chromeBrowser.findElement(By.name("zanras")).sendKeys(zanras);
    }

    /**
     * Funkcija, kuri suranda režisieriaus laukelį ir įrašo nurodytą režisierių
     * @param rez nurodomas režisierius
     */
    public static void rezisierius(String rez){
        chromeBrowser.findElement(By.name("rezisierius")).sendKeys(rez);
    }

    /**
     * Funkcija, kuri suranda insert mygtuką ir jį paspaudžia
     */
    public static void clickInsert(){
        chromeBrowser.findElement(By.name("insert")).click();
    }

    /**
     * Funkcija, kuri suranda delete mygtuką ir jį paspaudžia
     */
    public static void clickDelete(){
        chromeBrowser.findElement(By.name("delete")).click();
    }

    /**
     * Funkcija, kuri suranda update mygtuką ir jį paspaudžia
     */
    public static void clickUpdate(){
        chromeBrowser.findElement(By.name("update")).click();
    }


    /**
     * Funkcija, tikrinanti sėkmingą įrašo sukūrimą.
     * Įrašome pavadinimą, žanrą ir režisierių. Tuomet surandamas ir aspaudžiamas insert mygtukas.
     * Palyginamas gautas sėkmės pranešimas.
     */
    public static void sekmingasIrasoKurimas(){
        pavadinimas("Testų karai");
        zanras("Trileris");
        rezisierius("Algirdas");
        clickInsert();
        waitForElementByXpath("/html/body/div[2]");
        String name = chromeBrowser.findElement(By.xpath("/html/body/div[2]")).getText();
        Assert.assertEquals(name.toLowerCase(), "duomenys irasyti sekmingai");

    }

    /**
     * Funkcija, kuri patikrina nesėkmingą įrašo įvykdymą
     */
    public static void nesekmingasIrasoKurimas(){
        pavadinimas("1");
        zanras("Siaubas");
        rezisierius("as");
        clickInsert();
        waitForElementByXpath("/html/body/div[2]");
        String name = chromeBrowser.findElement(By.xpath("/html/body/div[2]")).getText();
        Assert.assertEquals(name.toLowerCase(), "blogai ivesti duomenys kuriant nauja irasa");
    }

    /**
     * Funkcija, kuri atlieka įrašo trynimą.
     * Nurodome įrašo id, kurį norime trinti, tuomet paspaudžiamas delete mygtukas.
     * Palyginamas gautas sėkmės pranešimas
     */
    public static void trinti(){
        id("522");
        clickDelete();
        String name = chromeBrowser.findElement(By.xpath("/html/body/div[2]")).getText();
        Assert.assertEquals(name.toLowerCase(), "irasas istrintas sekmingai");
    }

    /**
     * Funkcija, atliekanti sėkmingą įrašo redagavimą.
     * Įvedamas įrašo id, kurį norime redaguoti, tuomet įrašom naujas pavadinimo, žanro ir/ar režisieriaus reikšmes.
     * Surandamas ir paspaudžiamas update mygtukas ir tuomet palyginamas gautas sėkmės pranešimas.
     */
    public static void sekmingasRedagavimas(){
        id("88");
        pavadinimas("AMD");
        zanras("Kovinis");
        rezisierius("Rinka");
        clickUpdate();
        String name = chromeBrowser.findElement(By.xpath("/html/body/div[2]")).getText();
        Assert.assertEquals(name.toLowerCase(), "irasas redaguotas sekmingai");
    }

    /**
     * Funkcija, atliekanti nesėkmingą įrašo redagavimą.
     * Pvz., nenurodom įrašo id, kurį norim redaguoti, todėl redagavimas bus nesėkmingas.
     * Paspaudus update mygtuką palyginamas nesėkmės pranešimas.
     */
    public static void nesekmingasRedagavimas(){
        pavadinimas("AMD");
        zanras("1");
        rezisierius("Rinka");
        clickUpdate();
        String name = chromeBrowser.findElement(By.xpath("/html/body/div[2]")).getText();
        Assert.assertEquals(name.toLowerCase(), "blogai ivesti duomenys redaguojant irasa");
    }

    /**
     * Funkcija, kuri palaukia kol užsikraus nurodytas laukelis
     * @param xpath pasirinkto laukelio kelias
     */
    private static void waitForElementByXpath(String xpath){
        WebDriverWait waitas = new WebDriverWait(chromeBrowser, 2);
        waitas.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }



}
