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
                   // {"!$%^&*(){}[]':;/<>|№*******$$$!@@##"},
                    {-100},
                    {Integer.MAX_VALUE},
                    {Double.MAX_VALUE},
                   // {"<script>alert( 'Hello world' );</script>"},
                    {"Q91MXkSBG2w4bDK9Z9nprYeT4Pd69TGUdDOqWKDrlSKkIZ3JHqi0rA1G5LAfCZ54yEJ3adXLSmgtm4Z5hXMNT3ZqxkqMyqQhE9fze353egOMYAf0tESKpQtqdOzmrqiyvTjC6tCVc6Iqxgyq3TkICV3Hhk7ffbIYkIYXqk6Inktqt9xKmNqCPsemWzKVaXCiQ299HurLBuVTvZeFWYrqnyjl46h1AKLjfkZOMb0vRari1MFJz48qkpFR6RLTTBS2EtLY1rAj7OIw6zACkXgsJkUkMShenn19tEeZKsl3nAwnt4Qk1P1nzHlnSw6Kdl1jvGflS6aLfxrRoqIM0W1TDlUfCfXehzCemTTui7BddecX6aUTcvYHj3eQSYb4tiErgIdN6PMpizjNO4iZjJLTdBh6xtQC9DQKCj1gM8QKUtDYP5sO1SlEcKcjPIC0Q3jQ4yY27NCuLwAiCqdqdiMVjGYsOd90xcdRBtX5tREE7ATqk21riVMXtAIHmBAGZ2jYQ6ZDO86ohend0RPlqMbjg1G3oliIwx5gNX1solpXlUnu1hmA1TgI3mB2qF1d7zgLw9yXykzScvCtOVsvqOAShLQ7GmR9cFJ7jfHN8APVBFMkXUKEVl6NkQhAQ4ApA7ehLXapgDI4JLuaNAWwlos9gEF2eS9VJ4j8F44fksKySH1IdSkcKR0fk9KX5pIxUQ7KWfWL6aALwY9hXvTtlHWBS62rAPT2VliYrbt9rCz8UVYGyxF9Dm43WvR6xrht8fFrOCVzhRvBreXHsyqwAE4Mzg7NMG48OXKLbo7ENp2bN7L1ppoLfF75wEDx5ecbTuFEg3YS4yDtKNdreHOei2bh1moaos3Zzum6WXZWHhrzFHtris4t8QZygCaNUTeaaONxRuFZtpz91ynjBF0gNFo5G0avIZHo0L5m5SYjXi41iVh8UOHAw2LxqpsVBaXDZ22nM2CWw1fmCgGsK1Jq6QDjEzul2GGZse3qwLxIokcqlVzKuGLrLJ2DDwhoWBovx2du"}
            };
        }


    @Test
    @Feature("Получение базовой услуги по Гуид")
    @Owner("Малышев")
    @Description("Получение базовой услуги по Гуид")
    public void getBasicServicesGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("basic-services/843dabce-3c42-11ee-b5b0-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getBasicServicesGuid.json"));
        deleteSpec();
    }
    @Test(dataProvider = "guidNegative")
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
        System.out.println("Значение Гуид: " + guid);
    }

////////////////////////////////////////////"Получение номенклатуры по Гуид////////////////////////////////////////
    @Test
    @Feature("Получение номенклатуры по Гуид")
    @Owner("Малышев")
    @Description("Получение номенклатуры по Гуид, позитивный тест")
    public void getNomenclatureGuid() {
        installSpec(requestSpecification(), responseSpecification());
        given()
                .when()
                .get("nomenclature/13513a3e-36d6-11ee-b5b0-005056013b0c")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative")
    @Feature("Получение номенклатуры по Гуид")
    @Owner("Малышев")
    @Description("Получение нономенклатуры по Гуид, негативные тесты")
    public void getNomenclatureGuidDataProvider(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("nomenclature/{guid}")
                .then().log().all();
        System.out.println("Гуид равен " + guid);
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

    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt ")
    public void getNomenclatureSearchBodyIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 0)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type=1 ")
    public void getNomenclatureSearchBodyIsEmpty1() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 1)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type=2 ")
    public void getNomenclatureSearchBodyIsEmpty2() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 2)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела, data = Невалидный Гуид type= 3 ")
    public void getNomenclatureSearchBodyIsEmpty3() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 3)
                .queryParam("data", "f3ec794a-35d5-11ee-918f-7824af8ab7")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));

        deleteSpec();
    }
    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела, data = Bolt type= 4 ")
    public void getNomenclatureSearchBodyIsEmpty4() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 4)
                .queryParam("data", "Bolt")
                .get("nomenclature/search")
                .then().log().all()
                .body("size()", is(0))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getNomenclatureSearch.json"));
        deleteSpec();
    }
    @Test
    @Feature("Поиск номенклатуры")
    @Owner("Малышев")
    @Description("Поиск номенклатуры, получение пустого тела data = Bolt type= 5 ")
    public void getNomenclatureSearchBodyIsEmpty5() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", 5)
                .queryParam("type", 5)
                .queryParam("data", "Bolt")
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
