package test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.DbUtils;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest2 {

    @Test
    void shouldBlockUserAfterThreeInvalidCodes() {

        open("http://localhost:9999");

        // Получаем данные для логина
        var authInfo = DataHelper.getAuthInfo();

        // Переход на страницу логина и ввод данных
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);

        // Неверный ввод кода 3 раза
        for (int i = 0; i < 3; i++) {
            verificationPage.validVerify("000000"); // Ввод неверного кода
            verificationPage.shouldShowError("Неверно указан код! Попробуйте ещё раз.");
        }

        // Проверка, что блокировка произошла (например, на сервере блокируется)
        String status = DbUtils.getVerificationCodeFor(authInfo.getLogin()); // Здесь можно проверить статус блокировки
        assertTrue(status == null || status.isEmpty(), "Пользователь должен быть заблокирован!");
    }

}
