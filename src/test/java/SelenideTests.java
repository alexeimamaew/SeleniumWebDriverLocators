import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTests {

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1920x1080";
        open("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
    }

    @Test
        void baseLocatorsTests() {
            SelenideElement textInputByClass = $(".display-4");
            assertEquals("Hands-On Selenium WebDriver with Java", title());
        }

    @Test
    void baseLocatorsTests1() {
        SelenideElement textInputById = $("#my-text-id");
        textInputById.sendKeys("textInputId");

        SelenideElement textInputByName = $("[name='my-password']");
        textInputByName.sendKeys("textInputPassword");
    }


    @Test
    void inputText() {
        $("input#my-text-id").setValue("textInputId");
        $("[name='my-password']").setValue("textInputPassword");
        $("[name='my-password']").shouldHave(value("textInputPassword"));
    }

    @Test
    void inputTextToTextAria() {
        $x("//html/body/main/div/form/div/div[1]/label[3]/textarea").setValue("textInputToTextarea");
        $x("//html/body/main/div/form/div/div[1]/label[3]/textarea").shouldHave(value("textInputToTextarea"));
    }

    @Test
    void checkInputReadOnly() {
        $("[name='my-readonly']").shouldHave(value("Readonly input"));
    }

    @Test
    void selectFromListTests() {
        SelenideElement dropdownSelectMenu = $("[name='my-select']");
        dropdownSelectMenu.selectOption("Three");
        dropdownSelectMenu.getSelectedOption().shouldHave(text("Three"));
        dropdownSelectMenu.getSelectedOption().shouldBe(selected);
    }
}