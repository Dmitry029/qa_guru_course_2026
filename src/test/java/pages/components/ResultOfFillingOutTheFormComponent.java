package pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.Assertions;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;
import static java.util.stream.Collectors.toList;

public class ResultOfFillingOutTheFormComponent {
    private final ElementsCollection tableData = $$("tbody tr td:last-child");
    List<String> textsWithoutEmpty = tableData.stream()
        .map(SelenideElement::getText)
        .map(String::trim) // удаляем лишние пробелы по краям
        .filter(text -> !text.isEmpty())// исключаем пустые строки
        .map(text -> text.replace(',', ' '))
        .collect(toList());

    public void checkFormIsFilledOutCorrectly(List<String> expectedData) {
        Assertions.assertThat(textsWithoutEmpty).containsAll(expectedData);
    }
}
