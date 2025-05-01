package ru.netology.web.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    // Константы подключения к базе данных
    private static final QueryRunner runner = new QueryRunner();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASSWORD = "pass";

    public static String getVerificationCodeFor(String login) {
        String getUserIdSQL = "SELECT id FROM users WHERE login = ?";
        String getCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String userId = runner.query(conn, getUserIdSQL, new ScalarHandler<>(), login);
            if (userId == null) {
                throw new RuntimeException("User not found: " + login);
            }
            return runner.query(conn, getCodeSQL, new ScalarHandler<>(), userId);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить код для пользователя: " + login, e);
        }
    }
}
