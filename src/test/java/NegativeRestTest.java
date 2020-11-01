import conf.Conf;
import entity.Response;
import entity.User;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NegativeRestTest {
    String token = Conf.getProperty("access_token");
    Integer id;
    User user = new User();


    @BeforeTest
    public void init() {
        RestAssured.baseURI = Conf.getProperty("base_api_url");
    }

    @Test()
    public void getUserByIDAndReturnStatusCode400() {
        step("Get User by id and get 404");
        get("/users/{userID}", 4000).
                then().
                statusCode(200).
                body("code", equalTo(404));
    }

    @Test()
    public void tryToCreateUserWithBadEmptyEmailAndGet422() {
        step("try to Create user using post and get 422");
        user.setEmail("");
        given().
                header("Authorization", token).
                contentType("application/json").
                body(user).
                post("/users").
                then().
                body("code", equalTo(422));
    }

    @Test()
    public void tryToGetNotExistStatusCode404() {
        step("try to get not exist url and get status code 404");
        get("/users11122/{userID}", 4000).
                then().
                statusCode(404);
    }

    @Test()
    public void tryToCreateUserAndGetCode401() {
        step("try to create User with out access token and get 401");
        user.setStatus("xasdadwd");
        given().
                contentType("application/json").
                body(user).
                post("/users").
                then().
                body("code", equalTo(401));
    }
}
