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
                .pathParam("guid", "377593d9-168f-11ee-b5ab-a0dc07f9a67b")
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
                .pathParam("guid", "8c496b15-23e3-11ee-b5ac-005056013b0c")
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
}

