package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class ResultOfFillingOutTheFormComponent {
    private final SelenideElement resultComponent = $(".table-responsive");
    private final SelenideElement closeModalWindow = $("#closeLargeModal");
    private final SelenideElement tableBody = $(".table-responsive tbody");

    public boolean isResultFormPresent() {
        SelenideElement table = resultComponent.shouldBe(visible, ofSeconds(6));
        return table.isDisplayed();
    }

    public void closeLargeModal() {
        closeModalWindow.click();
    }

    public void checkResult(String key, String value) {
        tableBody.$$("tr").findBy(text(key)).shouldHave(text(value));
    }
}
