package pages.components;

import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    private final SelenideElement monthSelect = $(".react-datepicker__month-select");
    private final SelenideElement yearSelect = $(".react-datepicker__year-select");

    public void setDate(String date) {
        List<String> splitDate = Arrays.asList(date.split("\\s|,"));
        monthSelect.selectOption(splitDate.get(1));
        yearSelect.selectOption(splitDate.get(2));
        $(String.format(".react-datepicker__day--0%s:not(.react-datepicker__day--outside-month)", splitDate.get(0)))
            .click();
    }
}