package tests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tests.testdata.TestData;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static org.assertj.core.api.Assertions.assertThat;

public class PracticeFormTests extends BaseTest {
    private final TestData data = new TestData();

    @Test
    void fillAllFieldsOfTheFormTest() {

        Map<String, String> expectedResults = getExpectedResults();

        step("Open registration page", () -> {
            registrationPage.openPage();
        });

        step("Fill registration form", () -> {
            registrationPage
                .typeFirstName(data.firstName)
                .typeLastName(data.lastName)
                .typeUserEmail(data.email)
                .setGender(data.gender)
                .typePhoneNumber(data.mobile)
                .setDateOfBirth(data.dateOfBirth)
                .selectSubject(data.subject)
                .setHobby(data.hobby)
                .uploadPicture(data.fileName)
                .setAddress(data.address)
                .setStateAndCity(data.state, data.city)
                .submitForm();
        });

        step("Check that result form is present", () -> {
            assertThat(resultComponent.isResultFormPresent()).isTrue();
        });

        step("Check that all form fields are filled in correctly", () -> {
            expectedResults.forEach((key, value) -> resultComponent.checkResult(key, value));
            resultComponent.closeLargeModal();
        });
    }


    private Map<String, String> getExpectedResults() {
        String dateOfBirthForValidation = "Date of Birth " + data.dateOfBirth.replaceFirst(" (\\d{4})$", ",$1");

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("Student Name", data.firstName + " " + data.lastName);
        expectedResults.put("Student Email", data.email);
        expectedResults.put("Gender", data.gender);
        expectedResults.put("Mobile", data.mobile);
        expectedResults.put("Date of Birth", dateOfBirthForValidation);
        expectedResults.put("Subjects", data.subject);
        expectedResults.put("Hobbies", data.hobby);
        expectedResults.put("Picture", data.fileName);
        expectedResults.put("Address", data.address);
        expectedResults.put("State and City", data.state + " " + data.city);
        return expectedResults;
    }


    @Test
    void fillOnlyRequiredFieldsTest() {

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("Student Name", data.firstName + " " + data.lastName);
        expectedResults.put("Gender", data.gender);
        expectedResults.put("Mobile", data.mobile);

        step("Open registration page", () -> {
            registrationPage.openPage();
        });

        step("Fill registration form", () -> {
            registrationPage
                .typeFirstName(data.firstName)
                .typeLastName(data.lastName)
                .setGender(data.gender)
                .typePhoneNumber(data.mobile)
                .submitForm();
        });

        step("Check that result form is present", () -> {
            assertThat(resultComponent.isResultFormPresent()).isTrue();
        });

        step("Check that all form fields are filled in correctly", () -> {
            expectedResults.forEach((key, value) -> resultComponent.checkResult(key, value));
            resultComponent.closeLargeModal();
        });
    }

    @Test
    void negativeLessThanTenDigitsIntoThePhoneFieldTest() {
        step("Open registration page", () -> {
            registrationPage.openPage();
        });

        step("Fill registration form", () -> {
            registrationPage
                .typeFirstName(data.firstName)
                .typeLastName(data.lastName)
                .setGender(data.gender)
                .typePhoneNumber("12345")
                .submitForm();
        });

        step("Check that Mobile field has circle", () -> {
            String backgroundImage = registrationPage.getElementUserNumberCssValue("background-image");
            assertThat(backgroundImage.contains("circle"));
        });
    }

    @Test
    void negativeDoNotSelectGenderTest() {

        step("Open registration page", () -> {
            registrationPage.openPage();
        });

        step("Fill registration form", () -> {
            registrationPage
                .typeFirstName(data.firstName)
                .typeLastName(data.lastName)
                .typePhoneNumber(data.mobile)
                .submitForm();
        });

        step("Check that Gender field has red color", () -> {
            String colour = registrationPage.getElementGenderCssValue("color");
            assertThat(colour.contains("(220, 53, 69, 1)"));
        });
    }

    @Test
    void negativeDoNotFillFirstNameTest() {
        step("Open registration page", () -> {
            registrationPage.openPage();
        });

        step("Fill registration form", () -> {
            registrationPage
                .typeLastName(data.lastName)
                .setGender(data.gender)
                .typePhoneNumber(data.mobile)
                .submitForm();
        });

        step("Check that First name field has circle", () -> {
            String backgroundImage = registrationPage.getElementFirstNameCssValue("background-image");
            assertThat(backgroundImage.contains("circle"));
        });
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {

        step("Open registration page", () -> {
            registrationPage.openPage();
        });
        registrationPage.submitForm();

        step("Check errors images", () -> {

            SoftAssertions softAssertions = new SoftAssertions();

            String backgroundImageFirstName = registrationPage.getElementFirstNameCssValue("background-image");
            softAssertions.assertThat(backgroundImageFirstName).contains("circle");

            String backgroundImageLastName = registrationPage.getElementLastNameCssValue("background-image");
            softAssertions.assertThat(backgroundImageLastName).contains("circle");

            String backgroundImage = registrationPage.getElementUserNumberCssValue("background-image");
            assertThat(backgroundImage.contains("circle"));

            String colour = registrationPage.getElementGenderCssValue("color");
            softAssertions.assertThat(colour).contains("(220, 53, 69, 1)");

            softAssertions.assertAll();
        });
    }
}
