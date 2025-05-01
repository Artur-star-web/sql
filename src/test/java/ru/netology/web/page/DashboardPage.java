package ru.netology.web.page;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    public DashboardPage() {
        $("[data-test-id=dashboard]").shouldHave(text("Личный кабинет"));
    }
}
