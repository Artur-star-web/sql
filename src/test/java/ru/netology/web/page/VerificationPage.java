package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public void validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public void shouldShowError(String message) {
        $("[data-test-id=error-notification]").shouldHave(text(message));
    }

}
