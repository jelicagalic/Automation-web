import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest {

    @Test
    public void main() throws Exception{

        //Pozivanje i konfiguracija web browsera
        System.setProperty("webdriver.chrome.driver", "/Users/devfromfuture/Desktop/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.emmezeta.hr/");
        driver.manage().window().maximize();

        //Klik na Moj profil za prijavu korisnika
        driver.findElement(By.className("title")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Prijava')]")).click();
        Thread.sleep(2000);

        //Prijava korisnika s nepostojećim accountom
        WebElement element1 = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Registrirani korisnik?')]")));
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@mailinator.com");
        driver.findElement(By.xpath("//fieldset[@class='fieldset login']//input[@id='pass']")).sendKeys("Lozinka123");

        //Scroll do Google button
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//form[@id='login-form']//li[@class='sociallogin-button googleplus']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.findElement(By.xpath("//fieldset[@class='fieldset login']//button[@id='send2']")).click();
        Thread.sleep(5000);

        //Usporedba očekivane i stvarne vrijednosti.
        // Budući da se logiramo s krivim emailom, ovaj case predstavlja pogled usera koji ne zna da je logiram
        // s krivim emailom i očekuje da mu bude prikazano ime i prezime ispod ikone Moj Profil.
        // Tu očekivanu vrijednost uspoređujemo sa stvarnom.
        String expectedText = "Jelena Test";
        String actualText = driver.findElement(By.xpath("//span[@class='customer-name'][contains(text(),'Jelena Test')]")).getText();
        Assert.assertEquals(actualText, expectedText);
        System.out.println("expectedText" + expectedText);
        System.out.println("actualText" + actualText);

        //Zatvaranje web browsera
        driver.close();


        }

    }


