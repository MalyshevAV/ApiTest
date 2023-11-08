package AutoTest;

import Specifications.Specifications;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
@Epic("Номенклатура")

public class NomenclatureTest {


    @DataProvider
    public static Object[][] test() {
        return new Object[][]{
                {5, 0, "Болт"},
                {1, 1, "Болт"},
                {100, 2, "01сб"},
                //{2, 3, "4d0e1f7b-5149-11ee-b5b0-005056013b0c1"},
                {6, 4, "00"},
                {176, 5, "Болт"}
        };
    }
    @DataProvider
    public static Object[][] nomenclatureSearchEmptyBody() {
        return new Object[][]{
                {5, 0, "Bolt"},
                {5, 1, "Bolt"},
                {1, 2, "Bolt"},
                {1, 3, "4d0e1f7b-5149-11ee-b5b0-005056013b0c1"},
                {1, 4, "Bolt"},
                {5, 5, "Bolt"},
                {5, 0, 0},
                {5, 1, "<script>alert( 'Hello world' );</script>"},
                {5, 2, "select*From users"},
                {5, 4, Integer.MAX_VALUE},
                {5, 5, -100},
                {5, 1, Double.MAX_VALUE},
                {5, 2, "iiiiiiiiiiiiiiiiiiiiiiii"},
                {5, 3, "йййййййййййййййййййййййй"},
                {5, 4, "2.1"},
        };
    }
    @DataProvider
    public static Object[][] testNegative() {
        return new Object[][]{
                {100, 5, " "},
                {1, -1, "Болт"},
                {5, -0.1, "01сб"},
                {5, 6, "01сб"},
                {5, "\"select*from users\"", "00"},
                {5, Integer.MAX_VALUE, "00"},
                {5, " 9 ", "Болт"},
                {5, "sыgf123&* ", "00"},
                {4, 4.0, "Болт"},
                {4.0, 0, "Болт"},
                {0, 1, "Болт"},
                {0, 2, "Болт"},
                {0, 3, "Болт"},
                {0, 4, "Болт"},
                {0, 5, "Болт"},
                {4.0, 5, "Болт"},
                {201, 3, "Болт"},
                {201.1, 1, "Болт"},
                {0, 1, "01сб"},
                {-1, 4, "00"},
                {-0.1, 5, "00"},
        };
    }

    @Test
    @Feature("Получение базовой услуги по Гуид")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение базовой услуги по Гуид")
    public void getBasicServicesGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .pathParam("guid", "843dabce-3c42-11ee-b5b0-005056013b0c")
                .get("basic-services/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"));
        deleteSpec();
    }
    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Step("Проверка невалидного Гуид = {guid}")
    @Feature("Получение базовой услуги по Гуид")
    @Owner("Малышев")
    @Description("Получение базовой услуги по Гуид, негативные тесты")
    public void getBasicServicesGuidDataProvider(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("basic-services/{guid}")
                .then().log().all();
        deleteSpec();
    }

////////////////////////////////////////////"Получение номенклатуры по Гуид////////////////////////////////////////
    @Test
    @Feature("Получение номенклатуры по Гуид")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение номенклатуры по Гуид, позитивный тест")
    public void getNomenclatureGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .pathParam("guid", "13513a3e-36d6-11ee-b5b0-005056013b0c")
                .get("nomenclature/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение номенклатуры по Гуид")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Получение нономенклатуры по Гуид, негативные тесты")
    public void getNomenclatureGuidDataProvider(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("nomenclature/{guid}")
                .then().log().all();
        deleteSpec();
    }


//////////////////////////////////Поиск номенклатуры/////////////////////////////////////////////////////

    @Test(dataProvider = "test")
    @Feature("Поиск номенклатуры")
    @Step("Количество возвращаемых элементов = {step}, Тип поиска = {type}, Поисковый запрос = {data}")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, type" )
    public void getNomenclatureSearchDataProvider(Object step, Object type, Object data) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .queryParam("type", type)
                .queryParam("data", data)
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        System.out.println("Количество возвращаемых элементов : " + step+ ", " + " Тип поиска: " + type + ", " + " Поисковой запрос:  " + data );
        deleteSpec();
    }

    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, step = пустое поле")
    public void getNomenclatureSearchType1() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", "")
                .queryParam("type", 1)
                .queryParam("data", "Болт")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(173))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }

    @Test(dataProvider = "nomenclatureSearchEmptyBody")
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Step("Степ = {step}, Тип запроса = {type}, Параметр запроса = {data}")
    @Description("Поиск номенклатуры, получение пустого тела, неверный параметр запроса {data} = Bolt ")
    public void getNomenclatureSearchBodyIsEmpty(int step, int type, Object data) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .queryParam("type", type)
                .queryParam("data", data)
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }


    ////////////////////////Поиск номенклатуры, негативные тесты////////////////////////////////
    @Test(dataProvider = "testNegative")
    @Feature("Поиск номенклатуры")
    @Step("Количество возвращаемых элементов = {step}, Тип поиска = {type}, Поисковый запрос = {data}")
    @Owner("Малышев")
    @Description("Негативные тесты")
    public void getNomenclatureSearchNegative(Object step, Object type, Object data) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given().log().uri()
                .when()
                .queryParam("step", step)
                .queryParam("type", type)
                .queryParam("data", data)
                .get("nomenclature/search")
                .then().log().all();
        deleteSpec();

    }
}
