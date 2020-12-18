package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestModeTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void statusActive() {
        TestMode testMode = Generate.createActive();
        $("[class='input__box'] [name='login']").setValue(testMode.getLogin());
        $("[class='input__box'] [name='password']").setValue(testMode.getPassword());
        $("[class='button__content']").click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(exist);
    }

    @Test
    void statusBlocked() {
        TestMode testMode = Generate.createBlocked();
        $("[class='input__box'] [name='login']").setValue(testMode.getLogin());
        $("[class='input__box'] [name='password']").setValue(testMode.getPassword());
        $("[class='button__content']").click();
        $(byText("Пользователь заблокирован")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void withInvalidLogin() {
        TestMode testMode = Generate.invalidLogin();
        $("[class='input__box'] [name='login']").setValue(testMode.getLogin());
        $("[class='input__box'] [name='password']").setValue(testMode.getPassword());
        $("[class='button__content']").click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }

    @Test
    void withInvalidPassword() {
        SelenideElement request = $("[action]");
        TestMode userData = Generate.invalidPassword();
        request.$("[class='input__box'] [name='login']").setValue(userData.getLogin());
        request.$("[class='input__box'] [name='password']").setValue(userData.getPassword());
        request.$("[class='button__content']").click();
        $(byText("Неверно указан логин или пароль")).waitUntil(Condition.visible, 15000);
    }
}
