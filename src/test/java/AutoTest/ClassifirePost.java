package AutoTest;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.UUID;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ClassifirePost {
    UUID uuid = UUID.randomUUID();
    @Test
    public void okpfPost(){
        installSpec(requestPostSpecification(), responsePostSpecification());
        HashMap<String, Object> postOkpf = new HashMap<>();
        postOkpf.put("guid", uuid);
        postOkpf.put("name", "test");
        postOkpf.put("nameFull", "");
        postOkpf.put("code", "");
        given()
                .body(postOkpf)
                .when()
                .post("mdmexchangeokopf/")
                .then().log().all()
                .body("result", equalTo("OK"))
                .body("RecordChange.type", lessThan(50))
                .body("guid", hasSize(36));
    }
    @Test
    public void currencyPost(){
        installSpec(requestPostSpecification(), responsePostSpecification());
        HashMap<String, Object> postCurrency = new HashMap<>();
        postCurrency.put("guid", uuid);
        postCurrency.put("name", "test");
        postCurrency.put("code", "");
        postCurrency.put("codeSymbolic", "");
        given()
                .body(postCurrency)
                .when()
                .post("currency/")
                .then().log().all()
                .body("result", equalTo("OK"))
                .body("RecordChange.type", lessThan(50))
                .body("guid", hasSize(36));
    }
    @Test
    public void countryPost(){
        installSpec(requestPostSpecification(), responseSpecification());
        HashMap<String, Object> postCountry = new HashMap<>();
        postCountry.put("guid", uuid);
        postCountry.put("name", "test");
        postCountry.put("code", "");
        postCountry.put("codeSymbolic", "");
        given()
                .body(postCountry)
                .when()
                .post("mdmexchangecountry/")
                .then().log().all()
                .body("result", equalTo("OK"))
                .body("RecordChange.type", lessThan(50))
                .body("guid", hasSize(36));
    }


}
