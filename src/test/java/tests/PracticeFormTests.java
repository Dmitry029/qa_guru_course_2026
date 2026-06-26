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
    String firstName;
    String lastName ;
    String testEmail;
    String gender;
    String mobile;
    String dayOfBirth;
    String monthOfBirth;
    String yearOfBirth;
    String subject;
    String hobby;
    String fileName;
    String state;
    String city;
    String address;


    @BeforeEach
    public void setUpPracticeFormTests() {
        Selenide.open("/automation-practice-form");

        firstName = "John";
        lastName = "Deer";
        testEmail = "test@test.com";
        gender = "Male";
        mobile = "0123456789";
        dayOfBirth = "02";
        monthOfBirth = "February";
        yearOfBirth = "2000";
        subject = "Maths";
        hobby = "Music";
        fileName = "smile.jpg";
        state = "Haryana";
        city = "Panipat";
        address = "220 LA Richardson 12";
    }

    @Test
    void fillAllFieldsOfTheFormTest() {

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

        //$(byText(subject));
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
        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userNumber").val(mobile);
        $("#submit").scrollTo().click();

        String colour = $("[for=gender-radio-1]").getCssValue("color");
        assertThat(colour.contains("rgba(220, 53, 69, 1)"));
    }

    @Test
    void negativeDoNotFillFirstNameTest() {
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
