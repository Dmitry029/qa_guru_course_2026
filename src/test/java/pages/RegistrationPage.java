package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.CalendarComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage {

    CalendarComponent calendar = new CalendarComponent();

    private final SelenideElement pageTitle = $(".practice-form-wrapper");
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement userEmailInput = $("#userEmail");
    private final SelenideElement genderContainer = $("#genterWrapper");
    private final SelenideElement phoneNumberInput = $("#userNumber");
    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement subjectMenu = $("[class~=subjects-auto-complete__menu]");
    private final SelenideElement hobbiesContainer = $("#hobbiesWrapper");
    private final SelenideElement uploadPicture = $("#uploadPicture");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement stateSelect = $("#state");
    private final SelenideElement citySelect = $("#city");
    private final SelenideElement stateCityContainer = $("#stateCity-wrapper");
    private final SelenideElement submitButton = $("#submit");
    private final SelenideElement genderButtons = $("[for=gender-radio-1]");

    @Step("Open registration page /automation-practice-form")
    public RegistrationPage openPage() {
        open("/automation-practice-form");
        pageTitle.shouldHave(text("Student Registration Form"));
        return this;
    }

    @Step("Type first name \"{value}\"")
    public RegistrationPage typeFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    @Step("Type last name \"{value}\"")
    public RegistrationPage typeLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    @Step("Type user email \"{value}\"")
    public RegistrationPage typeUserEmail(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    @Step("Select gender \"{value}\"")
    public RegistrationPage setGender(String value) {
        genderContainer.$(byText(value)).click();
        return this;
    }

    @Step("Type phone number \"{value}\"")
    public RegistrationPage typePhoneNumber(String value) {
        phoneNumberInput.setValue(value);
        return this;
    }

    @Step("Select date \"{date}\"")
    public RegistrationPage setDateOfBirth(String date) {
        $("#dateOfBirthInput").click();
        calendar.setDate(date);
        return this;
    }

    @Step("Select subject \"{value}\"")
    public RegistrationPage selectSubject(String value) {
        subjectsInput.sendKeys(value.substring(0, 4));
        subjectMenu.click();
        return this;
    }

    @Step("Select hobby \"{value}\"")
    public RegistrationPage setHobby(String value) {
        hobbiesContainer.$(byText(value)).scrollTo().click();
        return this;
    }

    @Step("Upload picture \"{fileName}\"")
    public RegistrationPage uploadPicture(String fileName) {
        uploadPicture.uploadFromClasspath(fileName);
        return this;
    }

    @Step("Type address \"{value}\"")
    public RegistrationPage setAddress(String value) {
        currentAddressInput.setValue(value);
        return this;
    }

    @Step("Select state \"{value}\"")
    public void setState(String value) {
        stateSelect.click();
        stateCityContainer.$(byText(value)).click();
    }

    @Step("Select city \"{value}\"")
    public void setCity(String value) {
        citySelect.click();
        stateCityContainer.$(byText(value)).click();
    }

    @Step("Select state \"{state}\" and city \"{city}\"")
    public RegistrationPage setStateAndCity(String state, String city) {
        setState(state);
        setCity(city);
        return this;
    }

    @Step("Submit form")
    public void submitForm() {
        submitButton.scrollTo().click();
    }

    public String getElementUserNumberCssValue(String propertyName) {
        return phoneNumberInput.getCssValue(propertyName);
    }

    public String getElementFirstNameCssValue(String propertyName) {
        return firstNameInput.getCssValue(propertyName);
    }

    public String getElementLastNameCssValue(String propertyName) {
        return lastNameInput.getCssValue(propertyName);
    }

    public String getElementGenderCssValue(String propertyName) {
        return genderButtons.getCssValue(propertyName);
    }
}
