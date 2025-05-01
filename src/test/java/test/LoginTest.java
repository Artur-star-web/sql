package test;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;


public class LoginTest {
    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "http://localhost:9999";
    }

    @Test
    void shouldLoginSuccessfully() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        var code = DataHelper.getVerificationCodeFor(authInfo.getLogin());
        verificationPage.validVerify(code);
    }


}
