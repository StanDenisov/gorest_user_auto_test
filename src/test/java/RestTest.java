import conf.Conf;
import entity.Response;
import entity.User;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class RestTest {

    String token = Conf.getProperty("access_token");
    Integer id;
    User user = Conf.getUsersJson().get(0);


    @BeforeTest
    public void init() {
        RestAssured.baseURI = Conf.getProperty("base_api_url");
    }

    @Test(priority = 3)
    public void getUserByID() {
        get("/users/{userID}", id).
                then().
                statusCode(200);
    }

    @Test(priority = 1)
    public void CreateUser() {
        Response response = given().
                header("Authorization",token).
                contentType("application/json").
                body(user).post("/users").
                as(Response.class, ObjectMapperType.GSON);
        this.id = response.data.getId();
        assert(response.getCode()).equals(201);
    }

    @Test(priority = 2)
    public void UpdateUserName() {
        user.setName("Hello im changed");
        given().
                header("Authorization",token).
                contentType("application/json").
                put("/users/{userID}}",this.id).
                then().
                statusCode(200).
                body("code", equalTo(200));
    }

    @Test(priority = 4)
    public void DeleteUser() {
        given().
                header("Authorization",token).
                contentType("application/json").
                delete("/users/{userID}}",this.id).
                then().
                statusCode(200).
                body("code", equalTo(204));
    }
}
