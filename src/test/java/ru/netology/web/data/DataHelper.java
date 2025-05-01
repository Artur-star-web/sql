package ru.netology.web.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getVerificationCodeFor(String login) {
        return DbUtils.getVerificationCodeFor(login);
    }
    public static String getInvalidVerificationCode() {
        return "000000";
    }

}
