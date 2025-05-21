import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static Mamaev.Constants.BASE_URL;


public class SeleniumLocatorsTests {
    WebDriver driver;

    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

   @AfterEach
    void close() {driver.close(); }

    @Test
    void    baseLocatorsTests () throws InterruptedException {
        driver.get(BASE_URL);
        WebElement textInputByClass = driver.findElement(By.cssSelector(".display-4"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle()
);
    }

    @Test
    void  baseLocatorsTests1 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputById = driver.findElement(By.id("my-text-id"));
        textInputById.sendKeys("textInputId");
        Thread.sleep(1000);
        WebElement textInputByName = driver.findElement(By.name("my-password"));
        textInputByName.sendKeys("textInputPassword");
        Thread.sleep(1000);
          }

    @Test
    void
        baseLocatorsTests2 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.name("my-textarea"));
        textInputByName.sendKeys("textInputToTextarea");
        Thread.sleep(1000);
    }

    @Test
    void
    baseLocatorsTests3 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.name("my-disabled"));

    }
    @Test
    void
    baseLocatorsTests4 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement inputElement = driver.findElement(By.name("my-disabled"));
        String placeholderValue = inputElement.getAttribute("placeholder");
        Assertions.assertEquals("Disabled input", placeholderValue, "Placeholder value should be 'Disabled input'");

    }
    @Test
    void
    baseLocatorsTests5 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.name("my-readonly"));

    }
    @Test
    void
    baseLocatorsTestsByXpath () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.xpath("//*[@id=\"my-text-id\"]"));
        textInputByName.sendKeys("textInputId");
        Thread.sleep(1000);
    }
    @Test
    void
    baseLocatorsTestsByXpath2 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.xpath("/html/body/main/div/form/div/div[1]/label[2]/input"));
        textInputByName.sendKeys("textInputPassword");
        Thread.sleep(1000);
    }
    @Test
    void
    baseLocatorsTestsByXpath3 () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.xpath("/html/body/main/div/form/div/div[1]/label[3]/textarea"));
        textInputByName.sendKeys("textInputToTextarea");
        Thread.sleep(1000);
    }



}

