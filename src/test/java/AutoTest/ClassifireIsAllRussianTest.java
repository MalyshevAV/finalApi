package AutoTest;


import MDM.POJO.OkpdPojo;
import MDM.POJO.TnvdPojo;
import Specifications.Specifications;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.testng.AssertJUnit.assertTrue;
@Epic("Общероссийские классификаторы")

public class ClassifireIsAllRussianTest {
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКПД2")
    @Step ("Валидный Step = {step}")
    @Owner("Малышев")
    @Description("Получение списка ОКПД из 200 объектов")
    public void getOkpdList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<OkpdPojo> response  =
                given()
                        .when()
                        .queryParam("step", step)
                        .get("/okpd2")
                        .then().log().all()
                        .extract().body().jsonPath().getList(".", OkpdPojo.class);
        Assertions.assertEquals(response.size(), step);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 12));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 150));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Feature("Получение ОКПД2")
    @Owner("Малышев")
    @Description("Получение массива ОКПД, поле Step пустое")
    public void getOkpdListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("okpd2")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКПД2")
    @Step("Невалидный тест Степ = {step}")
    @Owner("Малышев")
    @Description("Негативный тест Получение массива ОКПД")
    public void getOkpdListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("okpd2")
                .then().log().all();
        deleteSpec();
    }

    ///////////////////////////Получение ОКПД2 по Гуид//////////////////////////////////////

    @Test
    @Feature("Получение ОКПД2")
    @Step("Валидный Guid")
    @Owner("Малышев")
    @Description("Получение ОКПД2 по Гуид, валидация при помощи схемы Json")
    public void getOkpdGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
       given()
                .when()
                .pathParam("guid", "15841c5e-1973-11ee-b5ac-005056013b0c")
                .get("/okpd2/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
                // поля в ответе Оквед и ОКПД одинаковые;
        deleteSpec();
    }
    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКПД2")
    @Step("Невалидный Guid = {guid}")
    @Owner("Малышев")
    @Description("Негативный тест Получение ОКПД2 по Гуид, несуществующий Гуид")
    public void getOkpdGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("okpd2/{guid}")
                .then().log().all();
    }
    ///////////////////////// Получение списка Okved  /////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКВЕД2")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка OKVED2")
    public void getOkvedList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
                given()
                        .when()
                        .queryParam("step", step)
                        .get("/okved2")
                        .then().log().all()
                        .body("size()", is(step))
                        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение ОКВЕД2")
    @Owner("Малышев")
    @Description("Получение массива OKVED, поле Step пустое")
    public void getOkvedListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("okved2")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКВЕД2")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива OKVED")
    public void getOkvedListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("okved2")
                .then().log().all();
        deleteSpec();
    }


///////////////////////// Получение Okved по Гуид, /////////////////////////////////////
    @Test
    @Feature("Получение ОКВЕД2")
    @Owner("Малышев")
    @Step("Валидный Guid = {guid}")
    @Description("Получение Okved по Гуид, валидация при помощи схемы Json")
    public void getOkvedGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "1ed68f25-9d9c-11ee-b5b3-005056013b0c")
                .get("/okved2/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkvedGuid.json"));
        deleteSpec();
    }
    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКВЕД2")
    @Owner("Малышев")
    @Step("Невалидный guid = {guid}")
    @Description("Негативный тест Получение Okved по Гуид, несуществующий Гуид")
    public void getOkvedGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("okved2/{guid}")
                .then().log().all();
    }

//////////////////////////////////Получение списка ТНВЕД ///////////////////////////////////////////


    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ТНВЕД")
    @Owner("Малышев")
    @Step("Валидный Степ = {step}")
    @Description("Получение списка ТНВEД")
    public void getTnvedList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        List<TnvdPojo> response  =
                given()
                .when()
                .queryParam("step", step)
                .get("/tnved")
                .then().log().all()
                        .extract().body().jsonPath().getList(".", TnvdPojo.class);
        Assertions.assertEquals(response.size(), step);
        response.forEach(x -> Assert.assertEquals(x.getGuid().length(), 36));
        response.forEach(x-> Assert.assertTrue(x.getCode().length() <= 10));
        response.forEach(x-> Assert.assertTrue(x.getName().length() <= 150));
        response.forEach(x-> Assert.assertTrue(x.getNameFull().length() <= 500));
        response.forEach(x -> Assert.assertEquals(x.getUnit().length(), 36));
        response.forEach(x -> Assert.assertTrue(x.getDateOutputArchive().length() <=20));
        Assertions.assertNotNull(response);
        deleteSpec();
    }

    @Test
    @Feature("Получение ТНВЕД")
    @Owner("Малышев")
    @Description("Получение массива ТНВEД, поле Step пустое")
    public void getTnvedListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("tnved")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .extract().body().jsonPath().getList(".", TnvdPojo.class);
        deleteSpec();
    }


    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ТНВЕД")
    @Step("Невалидный Степ = {step}")
    @Owner("Малышев")
    @Description("Негативный тест Получение массива ТНВEД")
    public void getTnvedListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("tnved")
                .then().log().all();
        deleteSpec();
    }
    /////////////////////////////////Получение ТНВЕД по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение ТНВЕД")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение ТНВЕД по Гуид, валидация при помощи схемы Json")
    public void getTnvedGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "84462f2e-9d9c-11ee-b5b3-005056013b0c")
                .get("/tnved/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getTvendGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ТНВЕД")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение ТНВЕД по Гуид, несуществующий Гуид")
    public void getTnvedGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("tnved/{guid}")
                .then().log().all();
    }

    /////////////////////////////////Получение списка OKOPF /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОKOPF")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка OKOPF")
    public void getOkopfList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .queryParam("step", step)
                .get("/okopf")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(step)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkopfList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение ОКOPF")
    @Owner("Малышев")
    @Description("Получение массива ОКOPF, поле Step пустое")
    public void getOkopfListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("okopf")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkopfList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение ОКOPF")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива OKOPF")
    public void getOkopfListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("okopf")
                .then().log().all();
        deleteSpec();
    }
    /////////////////////////////////Получение OKOPF по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение OKOPF")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение OKOPF по Гуид, валидация при помощи схемы Json")
    public void getOkopfGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "69b490d2-feee-11df-940c-001f29e885d8")
                .get("/okopf/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOkopfGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение OKOPF")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение OKOPF по Гуид, несуществующий Гуид")
    public void getOkopfGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("okopf/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Country /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Country")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Country")
    public void getCountryList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .get("/country")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCountryList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Country")
    @Owner("Малышев")
    @Description("Получение массива Country, поле Step пустое")
    public void getCountryListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("country")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCountryList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Country")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Country")
    public void getCountryListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("country")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Country по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Country")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Country по Гуид, валидация при помощи схемы Json")
    public void getCountryGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "4e127564-d74f-4dd9-bced-0327f0f4e061")
                .get("/country/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCountryGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Country")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Country по Гуид, несуществующий Гуид")
    public void getCountryGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("country/{guid}")
                .then().log().all();
    }

    /////////////////////////////////Получение списка Region /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Region")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Region")
    public void getRegionList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .queryParam("step", step)
                .get("/region")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getRegionList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Region")
    @Owner("Малышев")
    @Description("Получение массива Region, поле Step пустое")
    public void getRegionListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("region")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getRegionList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Region")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Region")
    public void getRegionListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("region")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Region по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Region")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Region по Гуид, валидация при помощи схемы Json")
    public void getRegionGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "755e817d-9465-11ee-b5b1-005056013b0c")
                .get("/region/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getRegionGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Region")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Region по Гуид, несуществующий Гуид")
    public void getRegionGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("region/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Bank /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Bank")
    public void getBankList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .queryParam("step", step)
                .get("bank")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Bank")
    @Owner("Малышев")
    @Description("Получение массива Bank, поле Step пустое")
    public void getBankListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .get("bank")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Bank")
    public void getBankListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("bank")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Bank по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Bank")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Bank по Гуид, валидация при помощи схемы Json")
    public void getBankGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .pathParam("guid", "979300ab-d82b-11e8-80bb-08002789bf51")
                .get("/bank/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Bank по Гуид, несуществующий Гуид")
    public void getBankGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("bank/{guid}")
                .then().log().all();
    }

    /////////////////////////////////Получение списка Currency /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Currency")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Currency")
    public void getCurrencyList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/currency")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCurrencyList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Currency")
    @Owner("Малышев")
    @Description("Получение массива Currency, поле Step пустое")
    public void getCurrencyListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("currency")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCurrencyList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Currency")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Currency")
    public void getCurrencyListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("currency")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Currency по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Currency")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Currency по Гуид, валидация при помощи схемы Json")
    public void getCurrencyGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .pathParam("guid", "f7f5d645-2df6-11e0-b48b-1cc1dee64484")
                .get("/currency/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getCurrencyGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Currency")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Currency по Гуид, несуществующий Гуид")
    public void getCurrencyGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("currency/{guid}")
                .then().log().all();
    }
}

