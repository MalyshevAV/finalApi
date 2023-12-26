package AutoTest;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class AuxiliaryTest {

    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] data() {
        return new Object[][]{
                {"nomenclature", 5, ""},
                {"basic-services", 1, ""},
                {"unified-classifier", 1, ""},
                {"eop", 1, ""},
                {"units", 1, ""},
                {"okpd2", 1, ""},
                {"okved2", 1, ""},
                {"tnved", 5, ""},
                {"nomenclature", 5, "2020-07-21"},
                {"basic-services", 1, "2021-01-21"},
                {"unified-classifier", 1, "2010-07-21"},
                {"eop", 1, "2017-07-21"},
                {"units", 1, "2017-07-21"},
                {"okpd2", 1, "2017-07-21"},
                {"okved2", 1, "2017-07-21"},
                {"tnved", 5, "2017-07-21"}
        };
    }
    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] dataIsEmpty() {
        return new Object[][]{
                {"nomenclature", 5},
                {"basic-services", 1},
                {"unified-classifier", 1},
                {"eop", 1},
                {"units", 1},
                {"okpd2", 1},
                {"okved2", 1},
                {"tnved", 5},
                {"nomenclature", 5},
                {"basic-services", 1},
                {"unified-classifier", 1},
                {"eop", 1},
                {"units", 1},
                {"okpd2", 1},
                {"okved2", 1},
                {"tnved", 5}
        };
    }
    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] dataNegativeDate() {
        return new Object[][]{
                {"nomenclature", 5, "20.03.20"},
                {"basic-services", 1, "20 марта 2020 "},
                {"unified-classifier", 1, "20/02/19"},
                {"eop", 1, "20/09/2023"},
                {"units", 1, "00/00/00"},
                {"okpd2", 1, "2017-07-2"},
                {"okved2", 1, "2017-07-00"},
                {"tnved", 5, "2017-07-001"},
                {"tnved", 5, "20-07-2017"},
        };
    }
    @DataProvider
    @Description("Позитивные тесты с использованием DataProvider")
    public static Object[][] typeNegative() {
        return new Object[][]{
                {"nomenclatur", 5, ""},
                {"", 1, ""},
                {" ", 1, ""},
                {0, 1, ""},
                {-1, 1, ""},
                {1.2, 1, ""},
                {Integer.MAX_VALUE, 1, ""},
                {"<script>alert( 'Hello world' );</script>", 5, ""},
                {"!@#$%^&*(){}[]':;/<>|№", 5, ""},
                {"select*from users", 5, ""},
        };
    }
        @DataProvider
        @Description("Негативные тесты с использованием DataProvider")
        public static Object[][] stepNegative() {
            return new Object[][]{
                 //   {Integer.MAX_VALUE, 5},
                  //  {" ", 5},
                    {"nomenclature", 0, "2017-07-21"},
                    {"basic-services", 201, "2017-07-21"},
                    {"unified-classifier", 1.2, "2017-07-21"},
                    {"eop", Integer.MAX_VALUE, "2017-07-21"},
                    {"units", Double.MAX_VALUE, "2017-07-21"},
                    {"okpd2", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta", "2017-07-21"},
                    {"eop", "!@#$%^&*(){}[]\"':;/<>\\|№\n", "2017-07-21"},
                    {"eop", "select*from users", "2017-07-21"},
                    {"units", -100, "2017-07-21"},
                    {"tnved", "<script>alert( 'Hello world' );</script>", "2017-07-21"}
            };
    }


    @Test
    @Feature("Вспомогательные")
    @Owner("Малышев")
    @Description("Проверка доступа и авторизации")
    public void getPing() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("/ping")
                .then().log().all();
        deleteSpec();
    }

    @Test(dataProvider = "data")
    @Feature("Вспомогательные")
    @Owner("Малышев")
    @Step("Тип изменяемых объектов = {type}, Количество возвращаемых элементов = {step}, Поле Дата = {data}")
    @Description("Проверка списка изменений, с датой")
    public void getListOfChangesDate(String type, Integer step, String date) {
        installSpec(requestSpecification(), responseSpecification());
        given().log().uri()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .queryParam("date", date)
                .get("list-of-changes/{type}")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(200))
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getListOfChanges.json"));
        deleteSpec();

    }

    @Test(dataProvider = "dataIsEmpty")
    @Feature("Вспомогательные")
    @Owner("Малышев")
    @Step("Тип изменяемых объектов = {type}, Количество возвращаемых элементов = {step}")
    @Description("Проверка списка изменений, с датой")
    public void getListOfChangesDateIsEmpty(String type, Integer step) {
        installSpec(requestSpecification(), responseSpecification());
        given().log().uri()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .get("list-of-changes/{type}")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(200))
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getListOfChanges.json"));
        deleteSpec();
    }

    @Test(dataProvider = "stepNegative")
    @Feature("Вспомогательные")
    @Owner("Малышев")
    @Step("Невалидные значения Количество возвращаемых элементов = {step} ")
    @Description("Невалидные значения Степ Проверка списка изменений")
    public void getListOfChangesNegative(Object type, Object step, String date) {
        installSpec(requestSpecification(), responseSpecification400());
        given().log().uri()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .queryParam("date", date)
                .get("list-of-changes/{type}")
                .then().log().all();
        deleteSpec();
    }

        @Test(dataProvider = "dataNegativeDate")
        @Feature("Вспомогательные")
        @Owner("Малышев")
        @Step("Невалидная Дата = {date}, Тип изменяемых объектов = {type}, Количество возвращаемых элементов = {step} ")
        @Description("Невалидные значения Даты, Проверка списка изменений")
        public void getListOfChangesNegativeDate(Object type, Object step, String date) {
            installSpec(requestSpecification(), responseSpecification400());
            given().log().uri()
                    .when()
                    .pathParam("type", type)
                    .queryParam("step", step)
                    .queryParam("date", date)
                    .get("list-of-changes/{type}")
                    .then().log().all();
            deleteSpec();
    }
    @Test(dataProvider = "typeNegative")
    @Feature("Вспомогательные")
    @Owner("Малышев")
    @Step("Невалидный Тип изменяемых объектов = {type}")
    @Description("Невалидные значения Type , Проверка списка изменений")
    public void getListOfChangesNegativeType(Object type, Object step, String date) {
        installSpec(requestSpecification(), responseSpecification400());
        given().log().uri()
                .when()
                .pathParam("type", type)
                .queryParam("step", step)
                .queryParam("date", date)
                .get("list-of-changes/{type}")
                .then().log().all();
        deleteSpec();
    }
}