package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.components.CalendarComponent;
import pages.components.ResultOfFillingOutTheFormComponent;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;

public class PracticeFormTests extends BaseTest {
    @BeforeEach
    public void setUpPracticeFormTests() {
        Selenide.open("/automation-practice-form");
    }

    @Test
    void fillAllFieldsOfTheFormTest() {
        String firstName = "John";
        String lastName = "Deer";
        String testEmail = "test@test.com";
        String gender = "Male";
        String mobile = "0123456789";
        String dayOfBirth = "02";
        String monthOfBirth = "February";
        String yearOfBirth = "2000";
        String subject = "Maths";
        String hobby = "Music";
        String fileName = "smile.jpg";
        String state = "Haryana";
        String city = "Panipat";
        String address = "220 LA Richardson 12";

        List<String> expectedData = List.of(
            firstName + " " + lastName,
            testEmail,
            gender,
            mobile,
            dayOfBirth + " " + monthOfBirth + " " + yearOfBirth,
            subject,
            hobby,
            fileName,
            address,
            state + " " + city
        );

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(testEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").val(mobile);
        new CalendarComponent().setDateOfBirthBySelect(dayOfBirth, monthOfBirth, yearOfBirth);
        // $("#subjectsInput").click();
        $("#subjectsInput").sendKeys(subject.substring(0, 2));
        $("[class~=subjects-auto-complete__menu]").click();

        ;//$(byText(subject));
        $("#hobbiesWrapper").$(byText(hobby)).click();
        // select picture
        $("#uploadPicture").uploadFromClasspath(fileName);
        $("#currentAddress").val(address);
        // select state
        $("#state").click();
        $x(String.format("//*[@id and text()='%s']", state)).click();
        // select city
        $("#city").click();
        $x(String.format("//*[@id and text()='%s']", city)).click();
        // submit and close
        $("#submit").click();
        SelenideElement table = $(".table-responsive").shouldBe(Condition.visible, Duration.ofSeconds(6));
        assertThat(table.isDisplayed()).isTrue();
        new ResultOfFillingOutTheFormComponent().checkFormIsFilledOutCorrectly(expectedData);
        $("#closeLargeModal").click();
    }

    @Test
    void fillOnlyRequiredFieldsTest() {
        String firstName = "Mary";
        String lastName = "Tompson";
        String gender = "Male";
        String mobile = "0123456789";
        List<String> expectedData = List.of(
            firstName + " " + lastName,
            gender,
            mobile
        );

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").val(mobile);

        $("#submit").scrollTo().click();
        SelenideElement table = $(".table-responsive").shouldBe(Condition.visible, Duration.ofSeconds(6));
        assertThat(table.isDisplayed()).isTrue();
        new ResultOfFillingOutTheFormComponent().checkFormIsFilledOutCorrectly(expectedData);
        $("#closeLargeModal").click();
    }

    @Test
    void negativeLessThanTenDigitsIntoThePhoneFieldTest() {
        String firstName = "Mary";
        String lastName = "Tompson";
        String mobile = "012345678";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#gender-radio-1").click();
        $("#userNumber").val(mobile);
        $("#submit").scrollTo().click();

        String backgroundImage = $("#userNumber").getCssValue("background-image");
        assertThat(backgroundImage.contains("circle"));
    }

    @Test
    void negativeDoNotSelectGenderTest() {
        String firstName = "Mary";
        String lastName = "Tompson";
        String mobile = "012345678";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userNumber").val(mobile);
        $("#submit").scrollTo().click();

        String colour = $("[for=gender-radio-1]").getCssValue("color");
        assertThat(colour.contains("rgba(220, 53, 69, 1)"));
    }

    @Test
    void negativeDoNotFillFirstNameTest() {
        String lastName = "Tompson";
        String mobile = "0123456780";

        $("#lastName").val(lastName);
        $("#gender-radio-1").click();
        $("#userNumber").val(mobile);
        $("#submit").scrollTo().click();

        String backgroundImage = $("#firstName").getCssValue("background-image");
        assertThat(backgroundImage.contains("circle"));
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {

        $("#submit").scrollTo().click();

        SoftAssertions softAssertions = new SoftAssertions();

        // Получение значений CSS
        String backgroundImageFirstName = $("#firstName").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageFirstName).contains("circle");

        String backgroundImageLastName = $("#lastName").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageLastName).contains("circle");

        String backgroundImageMobileNumber = $("#userNumber").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageMobileNumber).contains("circle");

        String colour = $("[for=gender-radio-1]").getCssValue("color");
        softAssertions.assertThat(colour).isEqualTo("rgba(220, 53, 69, 1)");

        // Выполняем все проверки
        softAssertions.assertAll();
    }
}
