package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static tests.testdata.TestData.*;

public class TextBoxTests extends BaseTest {

    @BeforeEach
    public void setUpTextBoxTests() {
        Selenide.open("/text-box");
    }

    @Test
    void successFulFillTest() {
        $("[id=userName]").val(userName);
        $("[id=userEmail]").val(userEmail);
        $("[id=currentAddress]").val(firstAddress);
        $("[id=permanentAddress]").val(secondAddress);
        $("#submit").scrollTo().click();

        $("#name").shouldHave(text(userName));
        $("[id=output] [id=email]").shouldHave(text(userEmail));
        $("[id=output] [id=currentAddress]").shouldHave(text(firstAddress));
        $("[id=output] [id=permanentAddress]").shouldHave(text(secondAddress));
    }

    @Test
    void oneFieldFillTest() {

        $("[id=userName]").val(userName);
        $("#submit").scrollTo().click();
        $("#name").shouldHave(text(userName));
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {
        $("#submit").scrollTo().click();
        $("#output").shouldNotBe(Condition.visible);
    }
}
