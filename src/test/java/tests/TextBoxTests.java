package tests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.testdata.TestData.*;

public class TextBoxTests extends BaseTest {

    @Test
    void successFulFillTest() {

        textBoxPage.openPage()
            .typeUserName(userName)
            .typeEmail(userEmail)
            .typeCurrentAddress(firstAddress)
            .typePermanentAddress(secondAddress)
            .submitForm();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(textBoxPage.getUserName().contains(userName));
        softAssertions.assertThat(textBoxPage.getUserEmail().contains(userEmail));
        softAssertions.assertThat(textBoxPage.getUserCurrentAddress().contains(firstAddress));
        softAssertions.assertThat(textBoxPage.getUserPermanentAddress().contains(secondAddress));
        softAssertions.assertAll();
    }

    @Test
    void oneFieldFillTest() {
        textBoxPage.openPage()
            .typeUserName(userName)
            .submitForm();
        assertThat(textBoxPage.getUserName().contains(userName));
    }

    @Test
    void negativeNoneOfTheFormFieldsAreFilledInTest() {
        textBoxPage.openPage()
            .submitForm();
        assertThat(textBoxPage.isOutputWindowVisible()).isFalse();
    }
}
