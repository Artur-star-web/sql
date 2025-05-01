package test;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest2 {

    @Test
    void shouldBlockUserAfterThreeInvalidVerificationAttempts() {
        String login = "vasya";
        String password = "qwerty123";

        open("http://localhost:9999");

        // Вводим логин и пароль
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();

        // Ждём появления формы ввода кода
        $("[data-test-id=code] input").shouldBe(visible, Duration.ofSeconds(10));

        // Три раза вводим неверный код
        for (int i = 0; i < 3; i++) {
            $("[data-test-id=code] input").setValue("000000"); // неправильный код
            $("[data-test-id=action-verify]").click();
        }

        // После 3 попыток пользователь должен быть заблокирован
        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));
    }
}
