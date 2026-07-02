package tests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.testdata.TestData.*;

public class PracticeFormTests extends BaseTest {

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

        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .typeUserEmail(testEmail)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .setDateOfBirth(dayOfBirth, monthOfBirth, yearOfBirth)
            .setSubject(subject)
            .setHobby(hobby)
            .uploadPicture(fileName)
            .setAddress(address)
            .setStateAndCity(state, city)
            .submitForm();

        assertThat(resultComponent.isResultFormPresent()).isTrue();
        assertThat(resultComponent.actualData()).containsAll(expectedData);
        resultComponent.closeLargeModal();
    }

    @Test
    void fillOnlyRequiredFieldsTest() {
        List<String> expectedData = List.of(
            firstName + " " + lastName,
            gender,
            mobile
        );

        registrationPage.openPage()
            .typeFirstName(firstName)
            .typeLastName(lastName)
            .setGender(gender)
            .typePhoneNumber(mobile)
            .submitForm();

        assertThat(resultComponent.isResultFormPresent()).isTrue();
        assertThat(resultComponent.actualData()).containsAll(expectedData);
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
