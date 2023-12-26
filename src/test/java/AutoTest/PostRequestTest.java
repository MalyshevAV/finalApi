package AutoTest;

import Models.PojoPost;
import Models.Responsible;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testng.annotations.BeforeClass;

import static Specifications.Specifications.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static io.restassured.RestAssured.given;



@Epic("Создание заявки на изменение, удаление")


public class PostRequestTest {

    UUID uuid = UUID.randomUUID();



  //  @Test
    @Description("using POJO")
    public void postNomenclatureChangeRequestPOJO() {
        PojoPost data = PojoPost.builder().build();
        Responsible rs = Responsible.builder().build();
        data.setComment("Комментарий к заявке");
        data.setResponsibl(rs);
        //String userArray[] = {"fio", "Иванов Иван Иванович","email", "ivanov@yandex.ru"};
        //data.setResponsibl(userArray);
        rs.setEmail("ivanov@yandex.ru");
        rs.setFio("Иванов Иван");
        given()
                .auth().basic("Administrator", "1234567809").and()
                .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("nomenclature/937a6068-3d9b-11ee-918f-7824af8ab721")
                .then().log().all()
                .statusCode(200)
                //.assertThat()
                .body("comment", notNullValue(), hasSize(lessThan(1024)))
                .body("responsible.fio", notNullValue(), hasSize(lessThan(100)))
                .body("responsible.email", notNullValue(), hasSize(lessThan(100)));
    }

  //  @Test
    @Description("using JsonFile ")
    public void postNomenclatureChangeJsonFile() throws FileNotFoundException {
        File file = new File("C:\\Users\\Sasha\\TEST\\Auto_test\\MDM\\src\\test\\resources\\postNomenclatureUsingJsonFil.json");
        FileReader fileReader = new FileReader(file);
        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject data = new JSONObject(jsonTokener);

        given()
                .auth().basic("Administrator", "1234567809")
                //.and()
                //.header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(data.toString())
                .when()
                .post("nomenclature/")
                .then().log().all()
                .statusCode(200)
                //.assertThat()
                .body("comment", notNullValue(), hasSize(lessThan(1024)))
                .body("responsible.fio", notNullValue(), hasSize(lessThan(100)))
                .body("responsible.email", notNullValue(), hasSize(lessThan(100)));
    }



    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку запрос существующей записи в МДМ и изменение  Тип = 0, 2")
    public  void postNomenclatureChangeRequestMapRequired(int type) {
        installSpec(requestSpecification(), responseSpecification());
        UUID uuid = UUID.randomUUID();
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid); // меняем
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("weight", 1.500);
        data.put("weightUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку на добавление записи   Тип = 1")
    public void postNomenclatureChangeRequestMapType1(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 2);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.500);
        data.put("weightUnit", uuid);
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Создаем заявку на вывод из обращения  Тип = 3")
    public void postNomenclatureChangeRequestMapType3(int type) {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type);
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку Запрос на изменение с обязательними полями Тип = 0, 2")
    public void postNomenclatureChangeRequestMapType0_2_RequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записи с обязательними полями Тип = 1")
    public void postNomenclatureChangeRequestMapType1_RequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Создаем заявку с обязательнами полями на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3RequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку минимальное значение в в атрибутах Тип = 0 2")
    public void postNomenclatureChangeRequestMinValue(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "К");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "i");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Б");
        data.put("nameFull", "ф");
        data.put("drawingDenotation", "24379.1-2012");
        data.put("ownershipSign",2); // 0 - Покупная, 1 - собственная
        data.put("seriality", 3);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1.3);
        data.put("width", 1.3);
        data.put("length", 1.1);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.1);
        data.put("weightUnit", uuid);
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записи с минимальным количеством Тип = 1")
    public void postNomenclatureChangeRequestMinMapType1(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "К");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "");

        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Б");
        data.put("nameFull", "Б");
        data.put("drawingDenotation", "2");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 2);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 12.330);
        data.put("width", 1.450);
        data.put("length", 9.985);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.500);
        data.put("weightUnit", uuid);
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Создаем заявку на вывод из обращения минимальное количество  Тип = 3")
    public void postNomenclatureChangeRequestMapMinType3(int type) {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "К");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "И");
        autor.put("email", "i");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку c Максимальным значением в атрибутах Тип=0  2")
    public void postNomenclatureChangeRequestMax(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "цу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
        autor.put("email", "7bQPCJrLjGPsIbnPhp9TI1mwUbEFwtcPSsSkDZ1WKJCh86bujE");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "wpq1F9jzlyqGGUXDF2JTYY9D2a0uJXxEaBtH32Jvk5xi8uecvRZ085O02VdjJszbr86yWBiHxiQ2WwB7ymdrtoAcrqcIddm2PatDEJ1eh822McYodW3jnQhwSp51RU04oGIJq5KrrbfbpUpmnln3UX");
        data.put("nameFull", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("drawingDenotation", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1);
        data.put("width", 1);
        data.put("length", 1);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1);
        data.put("weightUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


    @Test
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записи c Максимальным значением в атрибутах Тип = 1")
    public void postNomenclatureChangeRequestMax() {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "цу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
        autor.put("email", "йцу кен гшщ зхъ фва про лдж эя");

        //data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "wpq1F9jzlyqGGUXDF2JTYY9D2a0uJXxEaBtH32Jvk5xi8uecvRZ085O02VdjJszbr86yWBiHxiQ2WwB7ymdrtoAcrqcIddm2PatDEJ1eh822McYodW3jnQhwSp51RU04oGIJq5KrrbfbpUpmnln3UX");
        data.put("nameFull", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("drawingDenotation", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1);
        data.put("width", 1);
        data.put("length", 1);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1);
        data.put("weightUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

    @Test
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Description("Создаем заявку c максимальным значением на вывод из обращения минимальное количество  Тип = 3")
    public void postNomenclatureChangeRequestMapMaxType3() {
        installSpec(requestSpecification(), responseSpecification());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "цу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
        autor.put("email", "йцу кен гшщ зхъ фва про лдж эя");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }

//////////////////////////////////////Negative test

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку c Макс+1 в атрибутах Тип=0  2")
    public void postNomenclatureChangeRequestMaxPlus1(int type) {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "sцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "sйцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
        autor.put("email", "7bQPCJrLjGPsIbnPhp9TI1mwUbEFwtcPSsSkDZ1WKJCh86bujE");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "swpq1F9jzlyqGGUXDF2JTYY9D2a0uJXxEaBtH32Jvk5xi8uecvRZ085O02VdjJszbr86yWBiHxiQ2WwB7ymdrtoAcrqcIddm2PatDEJ1eh822McYodW3jnQhwSp51RU04oGIJq5KrrbfbpUpmnln3UX");
        data.put("nameFull", "sVKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("drawingDenotation", "sVKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1.0);
        data.put("width", 1.0);
        data.put("length", 1.0);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1.0);
        data.put("weightUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", is(nullValue()));
    }


    @Test//(dataProvider = "type", dataProviderClass = GetPositivedataprovider.class)
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записи c Макс+1 в атрибутах Тип = 1")
    public void postNomenclatureChangeRequestMaxPlus1Type1() {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "1zE19lr19NyMCUvmXNGZpFj3FXbgut0cbGgE7uaVeEWI1Dh4W8nB7UExgvhUUMoiW0yCvLQYr4Ou31APCkTR4q0IuOv9IX8F0qcewjeipMxagDTGo4fVQSyGaGYw3WSbqbEv0m4myCTk5d0DFbnO1GJHgkWKYoja7DjFiETUs4PDXkgwhcvLRlg14pZvIfZg705gSKih9T4FNBhyggjU5o31T7ZxPx831ssoEOTd6k0NWiG0hiKQk2ssxTc8lg7udvYqqOjyytqYKjZhdoE3pPG66oNK486c2vcs0U6v5Popil7fFEvJFmizf4aUI5nvVe1Vqypwv16wdHVPlNXeHhzI8ICWcIrcHQq4tDrWPyhYKWo8YudpTl6eWOJdhPYlkxpKV9mwTf0FitH0LTuPagzT3nHpSod0bMHhgUvtkiYZ4ik1LqkhY4YGVhI6sTp0fQX8GJhHfyL40zFJz9JRPF0V7hWNOhKyXN112ItdihtadhSgT4cLKJLbcfecBCR2fjYsdsIdtugg6kIWQUpClJ9pfnjhxZcRwRoPlcewmi1OHzXvFf44VTeMJl80xC0OfZpH9IpcARWLKj1o37BOTfPIIEYVxvPqomBbCa85M7b9ePedUbQJK1mWPqM0nkpJkYYm9UqRv75clsQzRMQW0uKFHBxo4elt2yAIZ51am3ywFByqrguD22exB44CsLHSwcnBaEB4frYBWNIha7SoDrBHqvOMhuqOXwvT3gurK7jLg7Bnn1ZSyWYPMYQiRBPWPgyHLzoM2qyIcnYS57Kp9cKlzCd06UAirzx5oS5B7NWxT5eNYl2OzudVneVp4hS3X2VkMpwXkzAXd3LJatd3dZzgCxJHUafOHSMAMhWCSFxZJihzXDY8fLuQMZlvlAnEX1SIlK0lSSIaUQzvLLcGADfsDf6aNsMwnV7WEC7B3VJLB2wWyLhZENvpp0UL9dckyZMh8ajGlxlpS3DTkU6diIYgDBVrnkbOpUvmFgqfGBmY73YJ5AA78HTOeVIMw9NjW");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "1xYc6oKUDgihYpgtq5DXjwg7JIivnjYJO8mRS3fFOONJqhIhjQGEVyTNYaEr0oZaDDYIQUUOewca3RAGr5lsPpvWJoQADl4QwDjYX");
        autor.put("email", "7bQPCJrLjGPsIbnPhp9TI1mwUbEFwtcPSsSkDZ1WKJCh86bujE");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "wpq1F9jzlyqGGUXDF2JTYY9D2a0uJXxEaBtH32Jvk5xi8uecvRZ085O02VdjJszbr86yWBiHxiQ2WwB7ymdrtoAcrqcIddm2PatDEJ1eh822McYodW3jnQhwSp51RU04oGIJq5KrrbfbpUpmnln3UX");
        data.put("nameFull", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("drawingDenotation", "VKEZr3z6iXqfYTFP3a7gM20suMzEGhiTP5nPkQwthp5v7paFc0SxRvH5UGb0qWNCTpY1It9fpSbnOX7ryJUfr6iDkZ10a0Q95yd3Xi6wlXuMawxwsq9VPHRAxYZFv2StMH6JJRXcvuxNO5Z2fPIq3U9FJp72m3dOXInZzs3Ah2DfKhIY4nWr6FNjhTwhQys4YSkJg1Nh1NocpqyE0K4eN7pk5cguer5kj1pmC9EYJOqBLSlB78fc7fj0mJk2PxC");
        data.put("ownershipSign", 0); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("supplier", "8e055502-9c46-4de7-80d4-1417ee678ab3");
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");
        data.put("height", 1);
        data.put("width", 1);
        data.put("length", 1);
        data.put("dimensionsUnit", uuid);
        data.put("weight", 1);
        data.put("weightUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .assertThat()
                .body("guid", is(nullValue()));
    }

    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку c Макс+1 на вывод из обращения минимальное количество  Тип = 3")
    public void postNomenclatureChangeRequestMapMaxPlusType3() {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("comment", "1цу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()_+/, § $&= @#«» <>~®-;²³ йцу кен гшщ зхъ фва про л");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "1йцу кен гшщ зхъ фва про лдж эяч сми тьб юёъ ЙЦУ КЕН ГШЩ ЗХЪ ФВА ПРО ЛДЖ ЭЯЧ СМИ ТЬБ ЮЁЪ !“№ ;%: ?*()");
        autor.put("email", "1йцу кен гшщ зхъ фва про лдж эя");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("dateOutputArchive", "0001-01-01T00:00:00");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку Запрос и изменение с пустыми обязательними полями Тип = 0, 2")
    public void postNomenclatureChangeRequestMapType0_2_RequiredFieldsIsEmpty(int type) {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "");
        autor.put("email", "");

        data.put("guid", uuid);
        data.put("guidBE", uuid);
        data.put("unifiedClassifier", uuid);
        data.put("name", "");
        data.put("nameFull", "");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }
    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записис пустыми обязательними полями Тип = 1")
    public void postNomenclatureChangeRequestMapType1_RequiredFieldsIsEmpty() {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);
        autor.put("fio", "");
        autor.put("email", "");
        data.put("guid", uuid);
        data.put("guidBE", uuid);
        data.put("unifiedClassifier", uuid);
        data.put("name", "");
        data.put("nameFull", "");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("baseUnit", uuid);

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }


    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку с обязательнами пустыми полями на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3RequiredFieldsIsEmpty() {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "");
        postNomenclature.put("guid",uuid );
        postNomenclature.put("be", uuid);
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "");
        autor.put("email", "");

        data.put("guid", uuid);
        data.put("guidBE", uuid);
        data.put("be", uuid);
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }
    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Пустой объект")
    public void postNomenclatureChangeRequestMapIsEmptyObject() {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку Запрос и изменение с дополнительным  полем Тип = 0, 2")
    public void postNomenclatureChangeRequestMapType0_2_RequiredExtraFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("user", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Формирование заявки на добавление записи с дополнительным полем полями Тип = 1")
    public void postNomenclatureChangeRequestMapType1_ExtraRequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("user", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 1);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));

    }

    @ParameterizedTest
    @ValueSource(ints = {3})
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку с дополнительным полем на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3ExtraRequiredFields(int type) {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("user", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", hasLength((36)))
                .body("result", equalTo("ok"));
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку Запрос и изменение с отсутствующим полем Тип = 0, 2")
    public void postNomenclatureChangeRequestMapType0_2_AbsentFields(int type) {
        installSpec(requestSpecification(), responseSpecification());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", type); // Параметризация значений с помощью Data Provider
       // postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid); //  для типа 2 нужен гуид номенклатуры который мы изменяем
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }
    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку Запрос и изменение с обязательними полями Тип = 1")
    public void postNomenclatureChangeRequestMapType1_AbsentFields() {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 1); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
       // data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }

    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку с обязательнами полями на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3AbsentFields() {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 3); // Параметризация значений с помощью Data Provider
      //  postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 2})
    @Feature("Позитивный тест")
    @Owner("Малышев")
    @Step("Отправка заявки с Типом = {type}")
    @Description("Создаем заявку Запрос и изменение с обязательними полями Тип = 0, 2")
    public void postNomenclatureChangeRequestMapTypeNotExist(int positiveData) {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", positiveData); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }
    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку Запрос и изменение с обязательними полями Тип = 1")
    public void postNomenclatureChangeRequestMapType1NotExist() {
        installSpec(requestSpecification(), responseSpecification400());
        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", 9); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", "49122bef-36d6-11ee-b5b0-005056013b0c");
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("unifiedClassifier", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        data.put("name", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012");
        data.put("nameFull", "Болт 1.1.М20 х 800. ВСт3пс2 ГОСТ 24379.1-2012 (Полное наименование))");
        data.put("ownershipSign", 1); // 0 - Покупная, 1 - собственная
        data.put("seriality", 0);  // 1 2 3 4 5
        data.put("baseUnit", "77b6212f-fd66-11df-ad89-001cc40d947c");

        given()
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }

    @Test
    @Feature("Негативный тест")
    @Owner("Малышев")
    @Description("Создаем заявку с обязательнами полями на вывод из обращения Тип = 3")
    public void postNomenclatureChangeRequestMapType3NotExist() {
        installSpec(requestSpecification(), responseSpecification400());

        HashMap<String, Object> postNomenclature = new HashMap<>();
        HashMap<String, Object> autor = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();

        postNomenclature.put("type", Integer.MAX_VALUE); // Параметризация значений с помощью Data Provider
        postNomenclature.put("content", "Комментари");
        postNomenclature.put("guid", uuid);
        postNomenclature.put("be", "a06d5480-52ee-11ee-b5b0-005056013b0c");
        postNomenclature.put("autor", autor);
        postNomenclature.put("data", data);

        autor.put("fio", "Иванов Иван Иванович");
        autor.put("email", "ivanov@yandex.ru");

        data.put("guid", uuid);
        data.put("guidBE", "8e7275eb-3049-11ee-b5ae-005056013b0c");
        data.put("be", "6c7ac4b5-67fd-11ee-b5b0-005056013b0c");
        given()
                // .header("x-se-hash", "cfcd208495d565ef66e7dff9f98764da")
                .body(postNomenclature)
                .when()
                .post("nomenclature/")
                .then().log().all()
                .body("guid", is(nullValue()));
    }




   // @Test
    @Description("Создаем заявку значения max+1")
    public void postNomenclatureChangeRequestMaxPlus() throws IOException {
        ObjectNode jsonNodes = new ObjectMapper().readValue(new File("src/test/resources/postNomenclatureUsingJsonFil.json"), ObjectNode.class);
        ((ObjectNode) jsonNodes.get("guid")).put("guid", "8e7275eb-3049-11ee-b5ae-005056013b0c");

    }
}

