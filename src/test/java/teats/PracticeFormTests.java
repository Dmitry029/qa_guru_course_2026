package teats;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;

public class PracticeFormTests extends BaseTest {

    @Test
    void fillAllFieldsOfTheFormTest() {
        Selenide.open("/automation-practice-form");
        String firstName = "John";
        String lastName = "Deer";
        String testEmail = "test@test.com";
        String mobile = "0123456789";
        String dateOfBirth = "02 Feb 2000";
        String subject = "maths";
        String fileName = "smile.jpg";
        String address = "220 LA Richardson 12";

        $("#firstName").val(firstName);
        $("#lastName").val(lastName);
        $("#userEmail").val(testEmail);
        $("#gender-radio-1").click();
        $("#userNumber").val(mobile);
        $("#dateOfBirthInput").val(dateOfBirth);
        $("#subjectsInput").val(subject).pressEnter();
        $("#hobbies-checkbox-2").click();
        // select picture
        $("#uploadPicture").uploadFromClasspath(fileName);
        $("#currentAddress").val(address);
        // select state
        $("#state").click();
        $("#react-select-3-option-1").click();
        // select city
        $("#city").click();
        $("#react-select-4-option-1").click();
        // submit and close
        $("#submit").click();
        $("#closeLargeModal").click();
    }
}
