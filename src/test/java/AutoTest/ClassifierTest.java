package AutoTest;

import MDM.POJO.UnifiedClassifirePojo;
import MDM.POJO.UnitsPojo;
import Specifications.Specifications;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

@Epic("Классификаторы")

public class ClassifierTest {
    @DataProvider
    public static Object[][] value() {
        return new Object[][]{
                {1},
                {5},
                {23}
        };
    }
    @DataProvider
    public static Object[][] positiveData() {
        return new Object[][]{
                {1},
                {2},
                {10},
                {100},
                {199},
                {200}
        };
    }
    @DataProvider
    public static Object[][] negativeData() {
        return new Object[][]{
                {201},
                {Integer.MAX_VALUE},
                {Double.MAX_VALUE},
                {"   "},
                {" 200 "},
                {2.1},
                {0},
                {"2 1"},
                {"Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent lupta"},
                {"123DWQ"},
                {"!@#$%^&*(){}[]\"':;/<>\\|№\n"},
                {"select*From users"},
                {-100},
                {"<script>alert( 'Hello world' );</script>"},
                {"2 1"}
        };
    }
    @DataProvider()
    public static Object[][] guidNegative() {
        return new Object[][]{

                {"13513a3e-36d6-11ee-b5b0-005026013b0c1"},
                {"13513a3e-36d6-11ee-b5b0-005056013b0"},
                {"43dabce-3c42-11ee-b5b0-005096013b1c1"},
                //  {"         "}, 403 ошибка
                {" 13513a3e-36d6-11ee-b5b0-005056013b0c "},
                {"13513a3e-3 6d6-11ee-b5 b0-005056013b0c"},
                {"1кк13a3e-36d6-11ee-b5b0-00)&056 013b0c"},
              //  {"!$%^&*(){}[]':;/<>|№*******$$$!@@##"}, Path parameters were not correctly defined
                {-100},
                {Integer.MAX_VALUE},
                {Double.MAX_VALUE},
                {"select*From Users"},
              //  {"<script>alert( 'Hello world' );</script>"}, 404 ошибка
                {"Q91MXkSBG2w4bDK9Z9nprYeT4Pd69TGUdDOqWKDrlSKkIZ3JHqi0rA1G5LAfCZ54yEJ3adXLSmgtm4Z5hXMNT3ZqxkqMyqQhE9fze353egOMYAf0tESKpQtqdOzmrqiyvTjC6tCVc6Iqxgyq3TkICV3Hhk7ffbIYkIYXqk6Inktqt9xKmNqCPsemWzKVaXCiQ299HurLBuVTvZeFWYrqnyjl46h1AKLjfkZOMb0vRari1MFJz48qkpFR6RLTTBS2EtLY1rAj7OIw6zACkXgsJkUkMShenn19tEeZKsl3nAwnt4Qk1P1nzHlnSw6Kdl1jvGflS6aLfxrRoqIM0W1TDlUfCfXehzCemTTui7BddecX6aUTcvYHj3eQSYb4tiErgIdN6PMpizjNO4iZjJLTdBh6xtQC9DQKCj1gM8QKUtDYP5sO1SlEcKcjPIC0Q3jQ4yY27NCuLwAiCqdqdiMVjGYsOd90xcdRBtX5tREE7ATqk21riVMXtAIHmBAGZ2jYQ6ZDO86ohend0RPlqMbjg1G3oliIwx5gNX1solpXlUnu1hmA1TgI3mB2qF1d7zgLw9yXykzScvCtOVsvqOAShLQ7GmR9cFJ7jfHN8APVBFMkXUKEVl6NkQhAQ4ApA7ehLXapgDI4JLuaNAWwlos9gEF2eS9VJ4j8F44fksKySH1IdSkcKR0fk9KX5pIxUQ7KWfWL6aALwY9hXvTtlHWBS62rAPT2VliYrbt9rCz8UVYGyxF9Dm43WvR6xrht8fFrOCVzhRvBreXHsyqwAE4Mzg7NMG48OXKLbo7ENp2bN7L1ppoLfF75wEDx5ecbTuFEg3YS4yDtKNdreHOei2bh1moaos3Zzum6WXZWHhrzFHtris4t8QZygCaNUTeaaONxRuFZtpz91ynjBF0gNFo5G0avIZHo0L5m5SYjXi41iVh8UOHAw2LxqpsVBaXDZ22nM2CWw1fmCgGsK1Jq6QDjEzul2GGZse3qwLxIokcqlVzKuGLrLJ2DDwhoWBovx2du"}
        };
    }
    @Feature("Единый Классификатор")
    @Test
    @Owner("Малышев")
    @Description("Получение массива всех категорий Единый классификатор")
    public void getUnifiedClassifierList() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnifiedClassifirePojo> response =
                given()
                        .when()
                        .queryParam("step", 200)
                        .get("/unified-classifier")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnifiedClassifirePojo.class);
        Assertions.assertEquals(response.size(), 200);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        //Схему до 20 увеличили
        response.forEach(x -> Assert.assertTrue(x.getCode().length() <= 20));
        response.forEach(x -> Assert.assertEquals(x.getParent().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertEquals(x.getOwner().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getOkp().length() <= 25));
       // response.forEach(x -> Assert.assertFalse(x.getOkp().length() == nullValue()));
        response.forEach(x -> Assert.assertEquals(x.getTnved().length(), 36));
        response.forEach(x -> Assert.assertEquals(x.getOkved().length(), 36));
        response.forEach(x -> Assert.assertEquals(x.getOkpd2().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
      //  Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test(dataProvider = "positiveData")
    @Feature("Единый Классификатор")
    @Step("Количество возвращаемых элементов = {step}")
    @Owner("Малышев")
    @Description("Получение массива Единый классификатор из объектов")
    public void getUnifiedClassifierListPositiveStep(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/unified-classifier")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Единый Классификатор")
    @Step("Количество возвращаемых элементов = 200")
    @Owner("Малышев")
    @Description("Получение массива Единый классификатор, поле Step пустое")
    public void getUnifiedClassifierListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("unified-classifier")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }


    @Test(dataProvider = "negativeData")
    @Feature("Единый Классификатор")
    @Step("Невалидное значение = {step}")
    @Owner("Малышев")
    @Description("Негативный тест. Получение массива Единый классификатор")
    public void getUnifiedClassifierListNegativeData(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("unified-classifier")
                .then().log().all();
        deleteSpec();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @Feature("Единый Классификатор")
    @Step("Валидный Guid = {guid}")
    @Description("Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "513916c8-1677-11ee-b5ab-a0dc07f9a67b")
                .get("unified-classifier/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnifiedClassifierGuid.json"));
    }

    @Test(dataProvider = "guidNegative")
    @Feature("Единый Классификатор")
    @Step("Невалидное значение Guid = {guid}")
    @Description("Негативный тест Получение единого классификатора по Гуид")
    public void getUnifiedClassifierGuidNegativeData(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("unified-classifier/{guid}")
                .then().log().all();

    }

///////////////////////////////////////////getEopList//////////////////////////////////////////////////////

    @Test(dataProvider = "positiveData")
    @Feature("Единый ограничительный перечень")
    @Step("Количество возвращаемых элементов = {step}")
    @Description("Получение массива всех категорий Единый ограничительный перечень, валидация Json схема")
    public void getEopList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("eop")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Единый ограничительный перечень")
    @Description("Получение массива всех категорий Единый ограничительный перечень, поле Step пустое")
    public void getEopListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("eop")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopList.json"));
        deleteSpec();
    }
    ///////////////////////////////////////////////////////////////////
    @Test (dataProvider = "negativeData")
    @Feature("Единый ограничительный перечень")
    @Step("Невалидное значение = {step}")
    @Owner("Малышев")
    @Description("Негативный тест Получение массива Единый ограничительный перечень")
    public void getEopListNegativeStep(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("eop")
                .then().log().all();
        deleteSpec();
    }

    ///////////getEopGuid///////////

    @Test
    @Feature("Единый ограничительный перечень")
    @Step("Валидное значение Guid = {guid}")
    @Owner("Малышев")
    @Description("Получение единого ограничительного переченя номенклатуры по Гуид")
    public void getEopGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "698688ac-1c9f-11ee-b5ac-005056013b0c")
                .get("eop/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getEopGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative")
    @Feature("Единый ограничительный перечень")
    @Step("Невалидное значение Guid = {guid}")
    @Owner("Малышев")
    @Description("Негативный тест Получение единого ограничительного перечня по Гуид")
    public void getEopGuidNegativeData(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("eop/{guid}")
                .then().log().all();;
    }
    @Feature("Единицы измерения")
    @Test(dataProvider = "value")
    //@ParameterizedTest
    //@ValueSource(ints = { 1, 5, 23})
    @Step("Валидное значение = {step}")
    @Owner("Малышев")
    @Description("Получение массива единиц измерения")
    public void getUnitsList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<UnitsPojo> response =
                given()
                        .when()
                        .queryParam("step", step)
                        .get("units")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", UnitsPojo.class);
        Assertions.assertEquals(response.size(), step);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getCode().length() <= 4)); // уточнить
        response.forEach(x -> Assert.assertTrue(x.getName().length() <= 25));
        response.forEach(x -> Assert.assertTrue(x.getNameFull().length() <= 100));
        response.forEach(x -> Assert.assertTrue(x.getInternationalReduction().length() <= 3));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
        Assertions.assertNotNull(response);
        deleteSpec();
    }


    @Test
    @Feature("Единицы измерения")
    @Description("Получение массива всех категорий Единый ограничительный перечень из 199 объектов")
    public void getUnitsListStepEqual199() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 199)
                .get("units")
                .then().log().all()
                .body("size()", is(199));
        deleteSpec();
    }

    @Test
    @Feature("Единицы измерения")
    @Description("Получение массива всех категорий Единый ограничительный перечень из 100 объектов")
    public void getUnitsListStepEqual100() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 100)
                .get("units")
                .then().log().all()
                .body("size()", is(100));
        deleteSpec();
    }

    @Test
    @Feature("Единицы измерения")
    @Description("Получение массива всех Единиц измерения, поле Step пустое")
    public void getUnitsListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("units")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }


    @Test(dataProvider = "negativeData")
    @Feature("Единицы измерения")
    @Step("Невалидное значение = {step}")
    @Description("Негативный тест Получение массива всех Единиц измерения")
    public void getUnitsListNegativeStep(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("units")
                .then().log().all();
        deleteSpec();
    }


    ////////////////////////////////getUnitsGuid//////////////////////////////////////////////////////////
    @Test
    @Feature("Единицы измерения")
    @Step("Валидное значение = 85303f5a-e3aa-11e2-91f0-c80aa9301ced")
    @Description("Получение единиц измерения по Гуид")
    public void getUnitsGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "85303f5a-e3aa-11e2-91f0-c80aa9301ced")
                .get("units/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getUnitsGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative")
    @Feature("Единицы измерения")
    @Step("Невалидное значение = {guid}")
    @Description("Негативный тест Получение единиц измерения по Гуид")
    public void getUnitsGuidNegativeData(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("units/{guid}")
                .then().log().all();
    }
}

