package tests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import tests.testdata.TestData;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PracticeFormTests extends BaseTest {
    private final TestData data = new TestData();


    private final String firstName = data.getFirstName();
    private final String lastName = data.getLastName();
    private final String email = data.getEmail();
    private final String gender = data.getGender();
    private final String mobile = data.getMobile();
    private final String dateOfBirth = data.getDateOfBirth();
    private final String subject = data.getSubject();
    private final String hobby = data.getHobby();
    private final String fileName = data.getFile();
    private final String address = data.getAddress();
    private final String state = data.getState();
    private final String city = data.getCity(state);

    @Test
    void fillAllFieldsOfTheFormTest() {

        Map<String, String> expectedResults = getExpectedResults();

        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .typeUserEmail(email)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .setDateOfBirth(dateOfBirth)
            .selectSubject(subject)
            .setHobby(hobby)
            .uploadPicture(fileName)
            .setAddress(address)
            .setStateAndCity(state, city)
            .submitForm();

        assertThat(resultComponent.isResultFormPresent()).isTrue();
        expectedResults.forEach((key, value) -> resultComponent.checkResult(key, value));
        resultComponent.closeLargeModal();
    }

    private Map<String, String> getExpectedResults() {
        String dateOfBirthForValidation = "Date of Birth " + dateOfBirth.replaceFirst(" (\\d{4})$", ",$1");

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("Student Name", firstName + " " + lastName);
        expectedResults.put("Student Email", email);
        expectedResults.put("Gender", gender);
        expectedResults.put("Mobile", mobile);
        expectedResults.put("Date of Birth", dateOfBirthForValidation);
        expectedResults.put("Subjects", subject);
        expectedResults.put("Hobbies", hobby);
        expectedResults.put("Picture", fileName);
        expectedResults.put("Address", address);
        expectedResults.put("State and City", state + " " + city);
        return expectedResults;
    }

    @Test
    void fillOnlyRequiredFieldsTest() {

        Map<String, String> expectedResults = new HashMap<>();
        expectedResults.put("Student Name", firstName + " " + lastName);
        expectedResults.put("Gender", gender);
        expectedResults.put("Mobile", mobile);

        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .submitForm();

        assertThat(resultComponent.isResultFormPresent()).isTrue();

        expectedResults.forEach((key, value) -> resultComponent.checkResult(key, value));

        resultComponent.closeLargeModal();
    }

    @Test
    void negativeLessThanTenDigitsIntoThePhoneFieldTest() {
        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .submitForm();

        String backgroundImage = registrationPage.getElementUserNumberCssValue("background-image");
        assertThat(backgroundImage.contains("circle"));
    }

    @Test
    void negativeDoNotSelectGenderTest() {

        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .typePhoneNumber(mobile)
            .submitForm();

        String colour = registrationPage.getElementGenderCssValue("color");
        assertThat(colour.contains("(220, 53, 69, 1)"));
    }

    @Test
    void negativeDoNotFillFirstNameTest() {
        registrationPage.openPage()
            .typeLastName(lastName)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .submitForm();

        String backgroundImage = registrationPage.getElementFirstNameCssValue("background-image");
        assertThat(backgroundImage.contains("circle"));
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {

        registrationPage.openPage()
            .submitForm();

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
    }
}
