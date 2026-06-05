package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TextBoxTests extends BaseTest {

    @BeforeEach
    public void setUpTextBoxTests() {
        Selenide.open("/text-box");
    }

    @Test
    void successFulFillTest() {

        $("[id=userName]").val("Alex Black");
        $("[id=userEmail]").val("alex@black.com");
        $("[id=currentAddress]").val("first address 1");
        $("[id=permanentAddress]").val("second address 2");
        $("#submit").scrollTo().click();

        $("#name").shouldHave(text("Alex Black"));
        $("[id=output] [id=email]").shouldHave(text("alex@black.com"));
        $("[id=output] [id=currentAddress]").shouldHave(text("first address 1"));
        $("[id=output] [id=permanentAddress]").shouldHave(text("second address 2"));
    }

    @Test
    void oneFieldFillTest() {

        $("[id=userName]").val("Alex Black");
        $("#submit").scrollTo().click();
        $("#name").shouldHave(text("Alex Black"));
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {
        $("#submit").scrollTo().click();
        $("#output").shouldNotBe(Condition.visible);
    }
}
