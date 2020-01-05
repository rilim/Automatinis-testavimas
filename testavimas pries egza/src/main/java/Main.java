import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;


public class Main {

    static WebDriver foxBrowser;
    static String telNr = "123456";
    static String slaptazodis = "slaptas";
    static String[] keywords = {"baranauskas", "selenium", "s10", "kitm"};

    public static void main(String args[]){
//        keywordsSearch(keywords);
//        loginLabas();
    }

    /**
     * Funkcija, kuri suranda naršyklės Firefox draiverius, tuomet sukuriamas naršyklės objektas,
     * kuris bus naudojamas atidarant tinklalapius
     */
    public static void setup(){
        //Sukuria Firefox naršyklės objektą
        FirefoxOptions opts = new FirefoxOptions();
        //į sukurtą objektą įdeda reikšmę, kurios dėka naršyklė bus atidaryta inPrivate lange
        opts.addArguments("-private");
        /*naršyklės draiveriai randami automatiškai - nereikia jų pačiam parsisiųsti ir nurodyti kelio.
        * Tai galima dėl to, nes pom.xml faile įdėtas webdrivermanager dependency*/
        WebDriverManager.firefoxdriver().setup();
        foxBrowser = new FirefoxDriver(opts); //kaip argumentą įdedam objektą, kuris nustatytas naršyklę atidaryti inPrivate lange
    }

    /**
     * Funkcija, kuri atidaro google.lt tinklalapį
     */
    public static void setupGoogle(){
        setup();
        foxBrowser.get("https://www.google.lt");
    }
    /**
     * Funkcija, kuri atidaro mano.labas.lt savitarną
     */
    public static void setupLabas(){
        setup();
        foxBrowser.get("https://mano.labas.lt/prisijungti");
    }

    /**
     * Funkcija, prijungianti prie savitarnos paskyros.
     * Iš pradžių ši funkcija paprašo terminale įvesti telefono numerį ir slaptažodį. Tuomet, atidarytame tinklalapyje
     * surandami atitinkami laukeliai į kuriuos įrašomi šie duomenys ir paspaudžiamas mygtukas prisijungti.
     * Tada dar paspaudžiama nuoroda, nukreipianti į pradinį paskyros puslapį.
     */
    public static void loginLabas(){
//        setupLabas();

//        Scanner s = new Scanner(System.in);
//        System.out.println("Įveskite telefono numerį");
//        String telNr = s.nextLine();
//        System.out.println("Įveskite slaptažodį");
//        String slaptazodis = s.nextLine();


        //Surandamas telefono numerio laukelis ir į jį įrašomas nurodytas tel. numeris
        foxBrowser.findElement(By.id("user-phone")).sendKeys(telNr);;
        //Surandamas slaptažodžio laukelis į kurį įrašomas nurodytas slaptažodis
        foxBrowser.findElement(By.id("user-password")).sendKeys(slaptazodis);
        //Surandamas mygtukas "prisijungti"
        WebElement submit = foxBrowser.findElement(By.className("btn-register-submit"));
        submit.click(); // mygtuko "prisijungti" paspaudimas

        WebElement clickMenu = foxBrowser.findElement(By.id("user-menu-toggle"));
        clickMenu.click();
        WebElement clickProfilis = foxBrowser.findElement(By.xpath("/html/body/div[1]/nav/div/div[2]/div[2]/ul/li[1]/a"));
        clickProfilis.click();
        //Surandamas vartotojo vardo laukelis, paimama jo reikšmė ir išsaugoma kintamajame
        String name = foxBrowser.findElement(By.id("user-name")).getAttribute("value");
        /*abu assert būdai palygina rastą ir nurodyta reikšmes. Rastos reikšmės raides padaro mažosiomis*/
//        assertTrue(name.equalsIgnoreCase("nesakysiu"));
        Assert.assertEquals(name.toLowerCase(), "nesakysiu");

    }

    /**
     * Funkcija, kuri pagal URL adresą patikrina ar pavyko prisijungti.
     * Palyginami URL adresai: įrašytas adresas, kuris turi būti, jei prisijungti pavyksta ir adresas,
     * kuris gaunamas įvykdžius programą.
     * @param url adresas, kuris turi būti sėkmingo prisijungimo rezultate
     */
    public static void assertUrls(String url){
        String expectedUrl = url;
        //Išsaugomas URL adresas gautas prisijungus/neprisijungus
        String currentUrl= foxBrowser.getCurrentUrl();
        //Palyginami URL adresai
        Assert.assertEquals(expectedUrl, currentUrl);
    }

    /**
     * Funkcija atliekanti paiešką pagal raktažodžius iš masyvo.
     * Main klasėje sukurtas masyvas su raktažodžiais, kurie perduodami į šią funkciją,
     * kur for cikle atliekami veiksmai su kiekvienu raktažodžiu:
     * 1. surandamas paieškos laukelis; 2. įvedamas atitinkamas raktažodis iš masyvo ir pradedama paieška;
     * 3. palaukiama kol užsikraus paieškos rezultato laukelis ir iš jo nuskaitoma reikšmė;
     * 4. nuskaityta reikšmė atspausdinama kiekvienam masyvo raktažodžiui atskirai.
     *
     * P.S. programa tikrai veikia, bet kai kuriais atvejais neprasisuka visas ciklas ar atspausdinama besikartojanti reikšmė.
     * Tai vyksta dėl to, kad, kaip tikėtina, kažkuriuo metu pasikeičia paieškos laukelio id
     * @param raktai raktažodžiai iš masyvo keywords
     */
    public static void keywordsSearch(String[] raktai) {
//        setupGoogle();

        for (int i = 0; i < raktai.length; i++) {
            //Surandamas paieškos laukelis į kurį įrašomas raktažodis iš masyvo
            foxBrowser.findElement(By.name("q")).sendKeys(raktai[i]);
            foxBrowser.findElement(By.name("q")).sendKeys(Keys.ENTER); //paspaudžiama ieškoti

            //Palaukia, kol pilnai užsikraus paieškos rezultato laukelis
            waitForElementByXpath("//*[@id=\"resultStats\"]");
            //Surandamas paieškos rezultatų laukelis
            WebElement searchResult = foxBrowser.findElement(By.xpath("//*[@id=\"resultStats\"]"));
            //Rastas laukelio rezultatas išsaugomas String kintamajame
            String results = searchResult.getText().replaceAll("[. A-Za-z]", "");
            System.out.println("Raktažodžio " + "\"" + raktai[i] + "\", " + "paieškos rezultatas: " + results);

            //Paieškos laukelis išvalomas, kad vėl būtų galima įrašyti naują raktažodį
            foxBrowser.findElement(By.name("q")).clear();
        }
    }

    /**
     * Funkcija, kuri palaukia kol užsikraus nurodytas laukelis
     * @param xpath pasirinkto laukelio kelias
     */
    private static void waitForElementByXpath(String xpath){
        WebDriverWait waitas=new WebDriverWait(foxBrowser, 2);
        waitas.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

}
