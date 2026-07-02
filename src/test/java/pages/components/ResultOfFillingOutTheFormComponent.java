package pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.time.Duration.ofSeconds;
import static java.util.stream.Collectors.toList;

public class ResultOfFillingOutTheFormComponent {
    private final SelenideElement resultComponent = $(".table-responsive");
    private final SelenideElement closeModalWindow = $("#closeLargeModal");
    private final ElementsCollection tableData = $$("tbody tr td:last-child");

    public List<String> actualData() {
        return tableData.stream()
            .map(SelenideElement::getText)
            .map(String::trim) // удаляем лишние пробелы по краям
            .filter(text -> !text.isEmpty())// исключаем пустые строки
            .map(text -> text.replace(',', ' '))
            .collect(toList());
    }

    public boolean isResultFormPresent() {
        SelenideElement table = resultComponent.shouldBe(visible, ofSeconds(6));
        return table.isDisplayed();
    }

    public void closeLargeModal() {
        closeModalWindow.click();
    }
}
