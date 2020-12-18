package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class Generate {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        @BeforeAll
        static void setUpAll(TestMode testMode) {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(testMode) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

    public static TestMode createActive() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new TestMode(login, password, "active"));
        return new TestMode(login, password, "active");
    }

    public static TestMode createBlocked() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        setUpAll(new TestMode(login, password, "blocked"));
        return new TestMode(login, password, "blocked");
    }

    public static TestMode invalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new TestMode("Любовь", password, status));
        return new TestMode("invalidLogin", password, status);
    }

    public static TestMode  invalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String status = "active";
        setUpAll(new TestMode(login, "пароль", status));
        return new TestMode(login, "invalidPassword", status);
    }
}

