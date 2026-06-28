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
import static org.assertj.core.api.Assertions.assertThat;
import static tests.testdata.TestData.*;

public class PracticeFormTests extends BaseTest {

    @BeforeEach
    public void setUpPracticeFormTests() {
        Selenide.open("/automation-practice-form");
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
        $("#subjectsInput").sendKeys(subject.substring(0, 2));
        $("[class~=subjects-auto-complete__menu]").click();
        $("#hobbiesWrapper").$(byText(hobby)).click();
        $("#uploadPicture").uploadFromClasspath(fileName);
        $("#currentAddress").val(address);
        $("#state").click();
        $("#state").$(byText(state)).click();
        $("#city").click();
        $("#city").$(byText(city)).click();
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

        String backgroundImageFirstName = $("#firstName").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageFirstName).contains("circle");

        String backgroundImageLastName = $("#lastName").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageLastName).contains("circle");

        String backgroundImageMobileNumber = $("#userNumber").getCssValue("background-image");
        softAssertions.assertThat(backgroundImageMobileNumber).contains("circle");

        String colour = $("[for=gender-radio-1]").getCssValue("color");
        softAssertions.assertThat(colour).isEqualTo("rgba(220, 53, 69, 1)");

        softAssertions.assertAll();
    }
}
