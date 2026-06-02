package teats;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests extends BaseTest {

    @Test
    void successfulFillTest() {
        open("/text-box");

        $("[id=userName]").val("Alex Black");
        $("[id=userEmail]").val("alex@black.com");
        $("[id=currentAddress]").val("first address 1");
        $("[id=permanentAddress]").val("second address 2");
        $("[id=submit]").click();

        $("#name").shouldHave(text("Alex Black"));
        $("[id=output] [id=email]").shouldHave(text("alex@black.com"));
        $("[id=output] [id=currentAddress]").shouldHave(text("first address 1"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("second address 2"));

    }
}
