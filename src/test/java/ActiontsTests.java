import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static Mamaev.Constants.BASE_URL;

public class ActiontsTests {
    WebDriver driver;

    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterEach
    void close() {driver.close(); }

    @Test
    void  inputText () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputById = driver.findElement(By.id("my-text-id"));
        textInputById.sendKeys("textInputId");
        Thread.sleep(1000);
        WebElement textInputByName = driver.findElement(By.name("my-password"));
        textInputByName.sendKeys("textInputPassword");
        Thread.sleep(1000);
        Assertions.assertEquals("textInputPassword", textInputByName.getAttribute("value"));
    }
    @Test
    void
    inputTextToTextAria () throws InterruptedException {
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByName = driver.findElement(By.xpath("/html/body/main/div/form/div/div[1]/label[3]/textarea"));
        textInputByName.sendKeys("textInputToTextarea");
        Thread.sleep(1000);
        Assertions.assertEquals("textInputToTextarea", textInputByName.getAttribute("value"));
    }
    @Test
    void inputTextToDisabled () {
        driver.get(BASE_URL);
        WebElement disabled = driver.findElement(By.name("my-disabled"));
        assertEquals("", disabled.getAttribute("value"));
        ElementNotInteractableException thrown = assertThrows(
                ElementNotInteractableException.class,
                () -> disabled.sendKeys("Test")
        );
        assertThat(thrown.getMessage()).contains("element not interactable");
    }

    @Test
    void checkInputReadOnly () {
        driver.get(BASE_URL);
        WebElement disabled = driver.findElement(By.name("my-readonly"));
        assertEquals("Readonly input", disabled.getAttribute("value"));
    }

    @Test
    void selectFromListTests() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-select"));
        Select select = new Select(dropdownSelectMenu);
        Thread.sleep(3000);
        select.selectByVisibleText("Three");
        Assertions.assertEquals("Three", select.getFirstSelectedOption().getText());
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());
    }

    @Test
    void fileUploadTest() throws IOException, InterruptedException {
        String filePath = "src/test/resources/test.txt";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println("Содержимое файла: " + content);
        URL url = ActiontsTests.class.getClassLoader().getResource("test.txt");

        String absolutePath = null;
        if (url != null) {
            // Получаем абсолютный путь к файлу
            absolutePath = new File(url.getPath()).getAbsolutePath();
            System.out.println("Абсолютный путь к файлу: " + absolutePath);
        } else {
            System.out.println("Ресурс не найден.");
        }
        driver.get(BASE_URL);
        WebElement fileUpload = driver.findElement(By.name("my-file"));
        fileUpload.sendKeys(absolutePath);
        Thread.sleep(5000);
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
        Thread.sleep(5000);
        assertThat(driver.getCurrentUrl()).contains("test.txt");
    }

    @Test
    void selectFromDataListTests() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement dropdownSelectMenu = driver.findElement(By.name("my-datalist"));
        dropdownSelectMenu.click();
        dropdownSelectMenu.sendKeys("Seattle");
        Thread.sleep(3000);
        Assertions.assertEquals("Seattle", dropdownSelectMenu.getAttribute("value"));
    }

    @Test
    void  selectCheckBoxTest() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement checkBoxCeched = driver.findElement(By.id("my-check-1"));
        checkBoxCeched.click();
        WebElement checkBox = driver.findElement(By.id("my-check-2"));
        checkBox.click();
        Assertions.assertEquals("on", checkBox.getAttribute("value"));

    }

    @Test
    void  selectRadioButtonTest() throws InterruptedException {
        driver.get(BASE_URL);
        WebElement RadioButton = driver.findElement(By.id("my-radio-2"));
        RadioButton.click();
        Assertions.assertEquals("on", RadioButton.getAttribute("value"));
    }

    @Test
    void navigationTest () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");
        WebElement disabled = driver.findElement(By.linkText("Previous"));
        assertEquals(null, disabled.getAttribute("value"));
    }

    @Test
    void navigationTest1 () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");
        WebElement ButtonNavigation = driver.findElement(By.linkText("2"));
        ButtonNavigation.click();
    }

    @Test
    void navigationTest3 () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");
        WebElement ButtonNavigationNext = driver.findElement(By.linkText("Next"));
        ButtonNavigationNext.click();
        WebElement ButtonNavigationNextNext = driver.findElement(By.linkText("Next"));
        ButtonNavigationNextNext.click();
        WebElement disabled = driver.findElement(By.linkText("Next"));
        assertEquals(null, disabled.getAttribute("value"));
        WebElement ButtonNavigationPrevious = driver.findElement(By.linkText("Previous"));
        ButtonNavigationPrevious.click();
    }

    @Test
    void leftButtonClickTest () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        WebElement Button = driver.findElement(By.id("my-dropdown-1"));
        new Actions(driver)
                .click(Button)
                .perform();
    }

    @Test
    void clickButtonTest () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        WebElement Button = driver.findElement(By.id("my-dropdown-2"));
        new Actions(driver)
                .contextClick(Button)
                .perform();
    }
    @Test
    void rightButtonClickTest () {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        WebElement rightButton = driver.findElement(By.id("my-dropdown-3"));
        new Actions(driver)
                .doubleClick(rightButton)
                .perform();
    }

    @Test
    void DragAndDropTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        Thread.sleep(3000);
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("target"));
        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();

    }

}
