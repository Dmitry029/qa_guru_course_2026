package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TextBoxPage {

    private final SelenideElement pageTitle = $(".text-center");
    private final SelenideElement userNameInput = $("#userName");
    private final SelenideElement userEmailInput = $("#userEmail");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement permanentAddressInput = $("#permanentAddress");
    private final SelenideElement submitButton = $("#submit");

    private final SelenideElement outputName = $("#name");
    private final SelenideElement outputEmail = $("[id=output] [id=email]");
    private final SelenideElement outputCurrentAddress = $("[id=output] [id=currentAddress]");
    private final SelenideElement outputPermanentAddress = $("[id=output] [id=permanentAddress]");
    private final SelenideElement outputWindow = $("#output");

    public TextBoxPage openPage() {
        Selenide.open("/text-box");
        pageTitle.shouldHave(text("Text Box"));
        return this;
    }

    public TextBoxPage typeUserName(String value) {
        userNameInput.setValue(value);
        return this;
    }

    public TextBoxPage typeEmail(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    public TextBoxPage typeCurrentAddress(String value) {
        currentAddressInput.setValue(value);
        return this;
    }

    public TextBoxPage typePermanentAddress(String value) {
        permanentAddressInput.setValue(value);
        return this;
    }

    public void submitForm() {
        submitButton.scrollTo().click();
    }

    public String getUserName() {
        return outputName.getText();
    }

    public String getUserEmail() {
        return outputEmail.getText();
    }

    public String getUserCurrentAddress() {
        return outputCurrentAddress.getText();
    }

    public String getUserPermanentAddress() {
        return outputPermanentAddress.getText();
    }

    public boolean isOutputWindowVisible() {
        return outputWindow.isDisplayed();
    }
}