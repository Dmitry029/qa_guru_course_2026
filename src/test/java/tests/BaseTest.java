package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import pages.RegistrationPage;
import pages.components.ResultOfFillingOutTheFormComponent;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    RegistrationPage registrationPage = new RegistrationPage();
    ResultOfFillingOutTheFormComponent resultComponent = new ResultOfFillingOutTheFormComponent();

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920*1080";
        //Configuration.browser = "chrome";
        //Configuration.browserVersion = "149.0";
        Configuration.baseUrl = "https://demoqa.com";
        //Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}