import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.openqa.selenium.Alert;


class SeleniumWaitingTests {
    WebDriver driver;

    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void actionScroll() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
        Thread.sleep(2000);
        WebElement footerLink = driver.findElement(By.className("text-muted"));
        new Actions(driver)
                .scrollToElement(footerLink)
                .scrollByAmount(0, 5)
                .perform();
        Thread.sleep(2000);

    }

    @Test
    void testShadowDon() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/shadow-dom.html");
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.cssSelector("p")));
        WebElement content = driver.findElement(By.id("content"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement textElement = shadowRoot.findElement(By.cssSelector("p"));
        assertThat(textElement.getText()).contains("Hello Shadow DOM");
    }

    @Test
    void cookieTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/cookies.html");
        WebDriver.Options options = driver.manage();
        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);
        Cookie username = options.getCookieNamed("username");
        assertThat(username.getValue()).isEqualTo("John Doe");
        assertThat(username.getPath()).isEqualTo("/");
        driver.findElement(By.id("refresh-cookies")).click();
        Cookie newCookie = new Cookie("new-cookie-key", "new-cookie-value");
        options.addCookie(newCookie);
        String readValue = options.getCookieNamed(newCookie.getName())
                .getValue();
        assertThat(newCookie.getValue()).isEqualTo(readValue);
        cookies = options.getCookies();
        assertThat(cookies).hasSize(3);
        driver.findElement(By.id("refresh-cookies")).click();

    }
    @Test
    void iframeTests() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));
        WebElement iframeElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iframeElement);
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("display-6")));
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet");
        driver.switchTo().defaultContent();
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
    }
    @Test
    void dialogBoxesTests() {
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
            driver.findElement(By.id("my-alert")).click();
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            assertThat(alert.getText()).isEqualTo("Hello world!");
            alert.accept();
            driver.findElement(By.id("my-confirm")).click();
            driver.switchTo().alert().accept();
            assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: true");
            driver.findElement(By.id("my-confirm")).click();
            driver.switchTo().alert().dismiss();
            assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: false");
            driver.findElement(By.id("my-prompt")).click();
            driver.switchTo().alert().sendKeys("Hello world");
            driver.switchTo().alert().accept();
            assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: Hello world");
            driver.findElement(By.id("my-modal")).click();
            WebElement save = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
            wait.until(ExpectedConditions.elementToBeClickable(save));
            save.click();
        }

    @Test
    void testWebStorage() {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-storage.html");
            WebStorage webStorage = (WebStorage) driver;
            LocalStorage localStorage = webStorage.getLocalStorage();
            System.out.printf("Local storage elements: {%s}\n", localStorage.size());
            SessionStorage sessionStorage = webStorage.getSessionStorage();
            sessionStorage.keySet()
                    .forEach(key -> System.out.printf("Session storage: {%s}={%s}\n", key, sessionStorage.getItem(key)));
            assertThat(sessionStorage.size()).isEqualTo(2);
            sessionStorage.setItem("new element", "new value");
            assertThat(sessionStorage.size()).isEqualTo(3);
            driver.findElement(By.id("display-session")).click();
        }

}
