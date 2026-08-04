package pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ResultOfFillingOutTheFormComponent {
    private final SelenideElement resultComponent = $(".table-responsive");
    private final SelenideElement closeModalWindow = $("#closeLargeModal");
    private final SelenideElement tableBody = $(".table-responsive tbody");

    @Step("Check that result form is visible")
    public boolean isResultFormPresent() {
        SelenideElement table = resultComponent.shouldBe(visible, ofSeconds(6));
        return table.isDisplayed();
    }

    @Step("Close modal window")
    public void closeLargeModal() {
        closeModalWindow.click();
    }

    @Step("Check that field \"key\" contains \"value\"")
    public void checkResult(String key, String value) {
        tableBody.$$("tr").findBy(text(key)).shouldHave(text(value));
    }
}
