package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
        String subject = "maths";
        String hobby = "Music";
        String fileName = "smile.jpg";
        String state = "Haryana";
        String city = "Panipat";
        String address = "220 LA Richardson 12";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(testEmail);
        $("#genterWrapper").$(byText(gender)).click();
        $("#userNumber").val(mobile);
        setDateOfBirthBySelect(dayOfBirth, monthOfBirth, yearOfBirth);
        $("#subjectsInput").val(subject).pressEnter();
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
        $("#closeLargeModal").click();
    }

    public void setDateOfBirthBySelect(String day, String month, String year) {
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(String.format(".react-datepicker__day--0%s:not(.react-datepicker__day--outside-month)", day))
            .click();
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
        SelenideElement table = $(".table-responsive").shouldBe(Condition.visible, Duration.ofSeconds(6));
        assertThat(table.isDisplayed()).isTrue();
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
