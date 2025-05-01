package test;

import com.codeborne.selenide.Condition;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASSWORD = "pass";
    private static final QueryRunner runner = new QueryRunner();

    private static String getLatestVerificationCode(String login) throws SQLException {
        String getUserIdSQL = "SELECT id FROM users WHERE login = ?";
        String getCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String userId = runner.query(conn, getUserIdSQL, new ScalarHandler<>(), login);
            if (userId == null) {
                throw new RuntimeException("User not found: " + login);
            }
            return runner.query(conn, getCodeSQL, new ScalarHandler<>(), userId);
        }
    }

    @Test
    void shouldLoginSuccessfully() {
        String login = "vasya";
        String password = "qwerty123";

        open("http://localhost:9999");

        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();

        $("[data-test-id=code] input").shouldBe(Condition.visible, Duration.ofSeconds(10));

        String code = null;
        try {
            code = getLatestVerificationCode(login);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Ошибка при получении кода из БД: " + e.getMessage());
        }

        if (code == null) {
            fail("Код подтверждения не найден в базе");
        }

        $("[data-test-id=code] input").setValue(code);
        $("[data-test-id=action-verify]").click();

        $(".heading").shouldHave(Condition.text("Личный кабинет"));
    }

}
