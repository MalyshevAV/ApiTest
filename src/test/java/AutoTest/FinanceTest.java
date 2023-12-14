package AutoTest;

import Specifications.Specifications;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static Specifications.Specifications.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
@Epic("Финансовый блок")

public class FinanceTest {

    /////////////////////////////////Получение списка всех статей ДДС /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-articles")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка dds-articles")
    public void getDdsArticlesList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/dds-articles")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-articlesList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение dds-articles")
    @Owner("Малышев")
    @Description("Получение массива dds-articles, поле Step пустое")
    public void getDdsArticlesListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("dds-articles")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-articlesList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-articles")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива dds-articles")
    public void getDdsArticlesListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("dds-articles")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение статьи ДДС по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение dds-articles")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение dds-articles по Гуид, валидация при помощи схемы Json")
    public void getDdsArticlesGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "d8708a2f-8a97-11ee-b5b1-005056013b0c")
                .get("/dds-articles/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-articlesGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-articles")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение dds-articles по Гуид, несуществующий Гуид")
    public void getDdsArticlesGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("dds-articles/{guid}")
                .then().log().all();
    }


    /////////////////////////////////Получение списка Вида статей ДДС /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-vid")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка dds-vid")
    public void getDdsVidList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/dds-vid")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-articlesList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение dds-vid")
    @Owner("Малышев")
    @Description("Получение массива dds-vid, поле Step пустое")
    public void getDdsVidListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("dds-vid")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-vidList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-articles")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива dds-articles")
    public void getDdsVidListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("dds-vid")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение Вида статей ДДС по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение dds-vid")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение dds-vid по Гуид, валидация при помощи схемы Json")
    public void getDdsVidGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "d8708a2f-8a97-11ee-b5b1-005056013b0c")
                .get("/dds-vid/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getDds-vidGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение dds-vid")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение dds-vid по Гуид, несуществующий Гуид")
    public void getDdsVidGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("dds-vid/{guid}")
                .then().log().all();
    }



    /////////////////////////////////Получение списка Всех статей затрат /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение expenses-articles")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка expenses-articles")
    public void getExpensesArticlesList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/expenses-articles")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getExpenses-ArticlesList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение expenses-articles")
    @Owner("Малышев")
    @Description("Получение массива expenses-articles, поле Step пустое")
    public void getExpensesArticlesStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("expenses-articles")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getExpenses-ArticlesList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение expenses-articles")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива expenses-articles")
    public void getExpensesArticlesStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("expenses-articles")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение  Статей затрат по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение expenses-articles")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение expenses-articles по Гуид, валидация при помощи схемы Json")
    public void getExpensesArticlesGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "d8708a2f-8a97-11ee-b5b1-005056013b0c")
                .get("/expenses-articles/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getExpenses-ArticlesGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение expenses-articles")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение expenses-articles по Гуид, несуществующий Гуид")
    public void getExpensesArticlesGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("expenses-articles/{guid}")
                .then().log().all();
    }




    /////////////////////////////////Получение списка Всех элементов прочие расходы и доходы /////////////////////////////////////////
    @Test(dataProvider = "positiveData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение other-income-expenses")
    @Step("Получение массива Степ = {step}")
    @Owner("Малышев")
    @Description("Получение списка other-income-expenses")
    public void getOtherIncomeExpensesList(int step) {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .queryParam("step", step)
                .get("/other-income-expenses")
                .then().log().all()
                .body("size()", is(step))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOther-income-expensesList.json"));
        deleteSpec();
    }

    @Test
    @Feature("Получение other-income-expenses")
    @Owner("Малышев")
    @Description("Получение массива other-income-expenses, поле Step пустое")
    public void getOtherIncomeExpensesListStepIsEmpty() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .get("other-income-expenses")
                .then().log().all()
                .body("size()", is(lessThanOrEqualTo(200)))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOther-income-expensesList.json"));
        deleteSpec();
    }

    @Test(dataProvider = "negativeData", dataProviderClass = ClassifierTest.class)
    @Feature("Получение other-income-expenses")
    @Owner("Малышев")
    @Step("Невалидный Степ = {step}")
    @Description("Негативный тест Получение массива other-income-expenses")
    public void getOtherIncomeExpensesListStepMaxPlus(Object step) {
        installSpec(requestSpecification(), Specifications.responseSpecification400());
        given()
                .when()
                .queryParam("step", step)
                .get("other-income-expenses")
                .then().log().all();
        deleteSpec();
    }

    /////////////////////////////////Получение элементов прочие расходы и доходы по Гуид/////////////////////////////////////////
    @Test
    @Feature("Получение other-income-expenses")
    @Owner("Малышев")
    @Step("Валидный Гуид")
    @Description("Получение other-income-expenses по Гуид, валидация при помощи схемы Json")
    public void getOtherIncomeExpensesGuid() {
        installSpec(requestSpecification(), Specifications.responseSpecification());
        given()
                .when()
                .pathParam("guid", "d8708a2f-8a97-11ee-b5b1-005056013b0c")
                .get("/other-income-expenses/{guid}")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getOther-income-expensesGuid.json"));
        deleteSpec();
    }

    @Test(dataProvider = "guidNegative", dataProviderClass = ClassifierTest.class)
    @Feature("Получение expenses-articles")
    @Owner("Малышев")
    @Step("Невалидный Гуид = {guid}")
    @Description("Негативный тест Получение expenses-articles по Гуид, несуществующий Гуид")
    public void getOtherIncomeExpensesGuidNotExist(Object guid) {
        installSpec(requestSpecification(), responseSpecification400());
        given()
                .when()
                .pathParam("guid", guid)
                .get("other-income-expenses/{guid}")
                .then().log().all();
    }

}
