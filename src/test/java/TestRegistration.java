import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestRegistration {

    @Test

    public void main() throws Exception{

        //Pozivanje i konfiguracija web browsera
        System.setProperty("webdriver.chrome.driver", "/Users/devfromfuture/Desktop/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.emmezeta.hr/");
        driver.manage().window().maximize();

        //Klik na Moj profil za registraciju korisnika
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-cookie-allow")));
        driver.findElement(By.id("btn-cookie-allow")).click();
        driver.findElement(By.className("title")).click();
        driver.findElement(By.className("create-account-wrapper")).click();

        //Registracija korisnika
        //Upisivanje email i lozinke
        WebElement element1 = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("create-acc-title")));
        driver.findElement(By.id("email_address")).click();
        driver.findElement(By.id("email_address")).sendKeys("test@mailinator");
        driver.findElement(By.id("password")).sendKeys("Lozinka123");
        driver.findElement(By.id("password-confirmation")).sendKeys("Lozinka123");

        //Popunjavanje osobnih podataka
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//form[@id='form-validate']//span[@class='sociallogin-button-text'][contains(text(),'Google prijava')]"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.findElement(By.id("firstname")).sendKeys("Jelena");
        driver.findElement(By.id("lastname")).sendKeys("Test");

        //Klik na registraciju
        driver.findElement(By.xpath("//button[@class='action submit primary account-button']")).click();
        Thread.sleep(2000);

        //Usporedba očekivane i stvarne vrijednosti.
        // Ovo je test gdje je upisan krivi format Email adrese te user očekuje da mu bude ispisana error poruka.
        //Uspoređuje se očekivana vrijednost sa stvarnom vrijednošću.
        String expectedText = "Unesite valjanu E-mail adresu (npr. ivan.horvat@domain.com).";
        String actualText = driver.findElement(By.xpath("//div[@id='email_address-error']")).getText();
        Assert.assertEquals(actualText, expectedText);
        System.out.println("expectedText" + expectedText);
        System.out.println("actualText" + actualText);

        //Zatvaranje web browsera
        driver.close();


    }
}

