import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestShop {

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

        //Prijava korisnika s postojećim accountom
        WebElement element1 = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Registrirani korisnik?')]")));
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("test@mailinator.com");
        driver.findElement(By.xpath("//fieldset[@class='fieldset login']//input[@id='pass']")).sendKeys("Lozinka123");
        Thread.sleep(2000);

        //Scroll do Google buttona
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement Element = driver.findElement(By.xpath("//form[@id='login-form']//li[@class='sociallogin-button googleplus']"));
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.findElement(By.xpath("//fieldset[@class='fieldset login']//button[@id='send2']")).click();
        Thread.sleep(7000);

        //Odabir proizvoda iz asortimana
        WebElement menu = driver.findElement(By.linkText("Multimedija"));
        Actions actions = new Actions(driver);
        actions.moveToElement(menu).perform();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Televizori")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//body[@class='page-with-filter page-products categorypath-televizori category-televizori page-layout-2columns-left catalog-category-view page-layout-category-full-width']/div[@class='page-wrapper']/main[@id='maincontent']/div[@class='columns']/div[@class='column main']/div[@class='subcategories top-categories']/a[1]/div[1]")).click();
        Thread.sleep(2000);

        //Dodavanje proizvoda u košaricu
        WebElement Element2 = driver.findElement(By.xpath("//li[4]//div[1]//div[1]//a[1]//span[1]//span[1]//div[1]"));
        js.executeScript("arguments[0].scrollIntoView();", Element2);
        driver.findElement(By.xpath("//a[@class='product-item-link'][contains(text(),'Panasonic TX-58GX700E Ultra HD LED TV')]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[@id='product-addtocart-button']")).click();
        Thread.sleep(2000);
        WebElement Element3 = driver.findElement(By.xpath("//a[@class='action showcart']"));
        js.executeScript("arguments[0].scrollIntoView();", Element3);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//a[@class='action showcart']")).click();
        Thread.sleep(5000);

        //Usporedba očekivane i stvarne vrijednosti. User očekuje odabrani proizvod u košarici
        String expectedArticle = "Panasonic TX-58GX700E Ultra HD LED TV";
        String actualArticle = driver.findElement(By.linkText("Panasonic TX-58GX700E Ultra HD LED TV")).getText();
        Assert.assertEquals(actualArticle, expectedArticle);
        System.out.println("expectedArticle" + expectedArticle);
        System.out.println("actualArticle" + actualArticle);
        Thread.sleep(4000);

        //Brisanje proizvoda iz košarice
        driver.findElement(By.xpath("//span[contains(text(),'Uklonite proizvod')]")).click();
        Thread.sleep(5000);

        //Uspoedba očekivane i stvarne vrijednosti. Dodali smo jedan proizvod i onda ga obrisali. Očekujemo poruku
        //koja će nam reći da nam je košarica prazna jer nemamo ni jedan proizvod u košarici.
        String expectedMessage = "Vaša je košarica prazna.";
        String actualMessage = driver.findElement(By.xpath("//p[@class='empty-title']")).getText();
        Assert.assertEquals(actualMessage, expectedMessage);
        System.out.println("expectedMessage" + expectedMessage);
        System.out.println("actualMessage" + actualMessage);

        //Zatvaranje web browsera
        driver.close();

    }

}

