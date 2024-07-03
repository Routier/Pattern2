import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void successLogin() {
        var registeredUser = DataGen.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void statusFailLogin() {
        var falseUser = DataGen.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(falseUser.getLogin());
        $("[data-test-id='password'] input").setValue(falseUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(10)).shouldBe(Condition.visible);
   }

    @Test
    void blockedUserFailLogin() {
        var blockedUser = DataGen.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
       $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }

    @Test
    void failLoginWrongLogin() {
        var registeredUser2 = DataGen.Registration.getRegisteredUser("active");
        var wrongLogin = DataGen.getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser2.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }

    @Test
    void failLoginWrongPassword() {
        var registeredUser3 = DataGen.Registration.getRegisteredUser("active");
        var wrongPassword = DataGen.getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser3.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль"), Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }
}
