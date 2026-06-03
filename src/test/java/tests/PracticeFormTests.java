package tests;

import com.codeborne.selenide.Selenide;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class PracticeFormTests extends BaseTest {
    @BeforeEach
    public void beforeEach() {
        Selenide.open("/automation-practice-form");
    }

    @Test
    void fillAllFieldsOfTheFormTest() {
        String firstName = "John";
        String lastName = "Deer";
        String testEmail = "test@test.com";
        String mobile = "0123456789";
        String dateOfBirth = "02 Feb 2000";
        String subject = "maths";
        String fileName = "smile.jpg";
        String address = "220 LA Richardson 12";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(testEmail);
        $("#gender-radio-1").click();
        $("#userNumber").val(mobile);
        $("#dateOfBirthInput").val(dateOfBirth);
        $("#subjectsInput").val(subject).pressEnter();
        $("#hobbies-checkbox-2").click();
        // select picture
        $("#uploadPicture").uploadFromClasspath(fileName);
        $("#currentAddress").val(address);
        // select state
        $("#state").click();
        $("#react-select-3-option-1").click();
        // select city
        $("#city").click();
        $("#react-select-4-option-1").click();
        // submit and close
        $("#submit").click();
        $("#closeLargeModal").click();
    }

    @Test
    void fillOnlyRequiredFieldsTest() {
        String firstName = "Mary";
        String lastName = "Tompson";
        String mobile = "0123456789";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#gender-radio-1").click();
        $("#userNumber").val(mobile);

        $("#submit").scrollTo().click();
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
