package AutoTest;

import Specifications.Specifications;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Оргструктура")

public class StructureTest {

    /////////////////////////////////Получение списка Organization /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Organization")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Organization")
    public void getOrganizationList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/organization")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOrganizationList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Organization")
    @Owner("Малышев")
    @Description("Получение массива Organization, поле Step пустое")
    public void getOrganizationListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .get("organization")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOrganizationList.json"));
        deleteSpec();
    }
    @Test
    @Feature("Получение Organization")
    @Owner("Малышев")
    @Description("Получение массива Organization, поле Step пустое")
    public void getOrganizationMsfo() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        Response response = given()
                .when()
                .get("organization")
                .then().log().all()
                .body("name", hasItem("Vogel&Noot AgroSrb ad (Сербия)"))  //class.find {it.name == ‘Ivan’}
                .extract().response();
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Organization")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Organization")
    public void getOrganizationListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("organization")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Organization по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Organization")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Organization по Гуид, валидация при помощи схемы Json")
    public void getOrganizationGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .pathParam("guid", "b5bf5253-8aaa-11ee-b5b1-005056013b0c")
                .get("/organization/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOrganizationGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Organization")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Organization по Гуид, несуществующий Гуид")
    public void getOrganizationGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("organization/{guid}")
                .then().log().all();
    }




    /////////////////////////////////Получение списка Divisions /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Divisions")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Divisions")
    public void getDivisionsList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/divisions")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDivisionsList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Divisions")
    @Owner("Малышев")
    @Description("Получение массива Divisions, поле Step пустое")
    public void getDivisionsListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("divisions")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDivisionsList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Divisions")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Divisions")
    public void getDivisionsListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("divisions")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Divisions по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Divisions")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Divisions по Гуид, валидация при помощи схемы Json")
    public void getDivisionsGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .pathParam("guid", "45c669af-894e-11ee-b5b1-005056013b0c")
                .get("/divisions/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDivisionsGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Divisions")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Divisions по Гуид, несуществующий Гуид")
    public void getDivisionsGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("divisions/{guid}")
                .then().log().all();
    }




    /////////////////////////////////Получение списка Sfo /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Sfo")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Sfo")
    public void getSfoList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when().log().uri()
                .queryParam("step", step)
                .get("/sfo")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getSfoList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Sfo")
    @Owner("Малышев")
    @Description("Получение массива Sfo, поле Step пустое")
    public void getSfoListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("sfo")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getSfoList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Sfo")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Sfo")
    public void getSfoListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("sfo")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Divisions по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Sfo")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Sfo по Гуид, валидация при помощи схемы Json")
    public void getSfoGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .pathParam("guid", "62b368cb-895a-11ee-b5b1-005056013b0c")
                .get("/sfo/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getSfoGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Sfo")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Sfo по Гуид, несуществующий Гуид")
    public void getSfoGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("sfo/{guid}")
                .then().log().all();
    }
}


