package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    private static final String VALID_USER = "user";
    private static final String VALID_PASSWORD = "user";

    By loginImput = By.id("username");
    By passwordInput = By.id("password");
    By submitButton = By.xpath("/html/body/main/div/div[4]/div/form/button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
    }

    public void successfulSignIn() {
        driver.findElement(loginImput).sendKeys(VALID_USER);
        driver.findElement(passwordInput).sendKeys(VALID_PASSWORD);
        driver.findElement(submitButton).click();
    }

    public void SignIn(String user, String password) {
        driver.findElement(loginImput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean successMessageIsPresent() {
        return driver.findElement(By.id("success")).isDisplayed();
    }
}
