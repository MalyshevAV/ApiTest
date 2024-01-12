package AutoTest;

import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;

@Epic("Заявка")
public class ApplicationTest {


    @Test
    @Feature("Работа с заявками")
    @Owner("Малышев")
    @Description("Получение заявки по Гуид на изменение Type = 5 , валидация по схеме Json")
    public void getChangeRequestGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("change-request/b8ad1a2b-3c8f-459c-a578-a2c84cffbaa6")
                .then().log().all()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getChangeRequest.json"));
        deleteSpec();
    }


    @Test (dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Работа с заявками")
    @Step("Невалидный Гуид = {guid}")
    @Owner("Малышев")
    @Description("Негативные тесты Получение изменеий по заявке по Гуид ")
    public void getChangeRequestGuidNegative(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("change-request/{guid}")
                .then().log().all();
        deleteSpec();
    }
}