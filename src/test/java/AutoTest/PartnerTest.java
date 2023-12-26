package AutoTest;

import MDM.POJO.PartnerPojo;
import Specifications.GetPositivedataprovider;
import Specifications.Specifications;
import io.qameta.allure.*;


import io.restassured.module.jsv.JsonSchemaValidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.Assert;
import org.testng.annotations.Test;
//import static org.hamcrest.MatcherAssert.assertThat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
@Epic("Контрагенты")
public class PartnerTest {
   // @ParameterizedTest
  //  @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создание пользователя")
    public  void postPartner() {
        installSpec(requestSpecification(), responseSpecification());
        UUID uuid = UUID.randomUUID();
        HashMap<String, Object> postPartner = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        postPartner.put("autor", autor);
        postPartner.put("data", data);
        postPartner.put("type", 1);


        data.put("inn", "7728212268");
        data.put("kpp", "7728212260");
        data.put("kppAdditional", "770101001");
        autor.put("fio", "Ivanov ivan");
        autor.put("email", "ivanov@yandex.ru");
        given()
                .body(postPartner)
                .when()
                .post("partner")
                .then().log().all()
                .assertThat()
                .body("guid", hasLength((36)));
    }

    /////////////////////////////////Получение списка Partner /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Partner")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Partner")
    public void getPartnerList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/partner")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Partner")
    @Owner("Малышев")
    @Description("Получение массива Partner, поле Step пустое")
    public void getPartnerListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("partner")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Partner")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива partnerConcernType")
    public void getPartnerListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("partner")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Partner по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Partner")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Partner по Гуид, валидация при помощи схемы Json")
    public void getPartnerGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "b5f973d0-9f7c-11ee-b5b3-005056013b0c")
                .get("/partner/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Partner")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Partner по Гуид, несуществующий Гуид")
    public void getPartnerGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("partner/{guid}")
                .then().log().all();
    }

    /////////////////////////////////Получение списка partnerConcernType /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение partnerConcernType")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка partnerConcernType")
    public void getPartnerConcernTypeList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .get("/partner-concern-type")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerConcernTypeList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение partnerConcernType")
    @Owner("Малышев")
    @Description("Получение массива partnerConcernType, поле Step пустое")
    public void getPartnerConcernTypeListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .get("partner-concern-type")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerConcernTypeList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение partnerConcernType")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива partnerConcernType")
    public void getPartnerConcernTypeListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("partner-concern-type")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение partnerConcernType по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение partnerConcernType")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение partnerConcernType по Гуид, валидация при помощи схемы Json")
    public void getPartnerConcernTypeGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .pathParam("guid", "f08adf73-9d14-11ee-b5b3-005056013b0c")
                .get("/partner-concern-type/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getPartnerConcernTypeGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение partnerConcernType")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение partnerConcernType по Гуид, несуществующий Гуид")
    public void getPartnerConcernTypeGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("partner-concern-type/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Contact /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Contact")
    public void getContactList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .queryParam("step", step)
                .get("/contact")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Contact")
    @Owner("Малышев")
    @Description("Получение массива Contact, поле Step пустое")
    public void getContactListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .get("contact")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива Contact")
    public void getContactMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("contact")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Contact по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Contact")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Contact по Гуид, валидация при помощи схемы Json")
    public void getContactGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "ed4f63bd-8a1b-11ee-b5b1-005056013b0c")
                .get("/contact/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест, получение Contact по Гуид, несуществующий Гуид")
    public void getContactGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("contact/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Contact-roles /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact-roles")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Contact-roles")
    public void getContactRolesList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .get("contact-roles")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactRolesList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Contact-roles")
    @Owner("Малышев")
    @Description("Получение массива Contact-roles, поле Step пустое")
    public void getContactRolesListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("contact-roles")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactRolesList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact-roles")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест, получение массива Contact-roles")
    public void getContactRolesMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("contact-roles")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Contact-roles по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Contact-roles")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Contact-roles по Гуид, валидация при помощи схемы Json")
    public void getContactRolesGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "13a5f0a4-8960-11ee-b5b1-005056013b0c")
                .get("/contact-roles/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getContactRolesGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Contact-roles")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Contact-roles по Гуид, несуществующий Гуид")
    public void getContactRolesGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("contact-roles/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Bank Account /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Bank Account")
    public void getBankAccountList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when().log().uri()
                .queryParam("step", step)
                .get("/bank-accounts")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Bank Account")
    @Owner("Малышев")
    @Description("Получение массива Bank Account, поле Step пустое")
    public void getBankAccountStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("bank-accounts")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест, получение массива Bank Account")
    public void getBankAccountMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("bank-accounts")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Bank Account по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Bank Account")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Bank Account по Гуид, валидация при помощи схемы Json")
    public void getBankAccountGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "fe3562f8-8ebe-11ee-b5b1-005056013b0c")
                .get("/bank-accounts/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Bank Account по Гуид, несуществующий Гуид")
    public void getBankAccountGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("bank-accounts/{guid}")
                .then().log().all();
    }

    /////////////////////////////////Получение списка Bank Account Type /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account Type")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка Bank Account Type")
    public void getBankAccountTypeList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .get("bank-accounts-type")
                .then().log().all()
                .body("size()", lessThanOrEqualTo(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountTypeList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение Bank Account Type")
    @Owner("Малышев")
    @Description("Получение массива Bank Account Type, поле Step пустое")
    public void getBankAccountTypeStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("bank-accounts-type")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountTypeList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account Type")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест, получение массива Bank Account Type")
    public void getBankAccountTypeMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("bank-accounts-type")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Bank Account Type по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение Bank Account Type")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение Bank Account по Гуид, валидация при помощи схемы Json")
    public void getBankAccountTypeGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "3ef31610-1614-11e7-810d-005056a71cd1")
                .get("/bank-accounts-type/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBankAccountTypeGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение Bank Account Type")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение Bank Account Type по Гуид, несуществующий Гуид")
    public void getBankAccountTypeGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("bank-accounts-type/{guid}")
                .then().log().all();
    }
}

