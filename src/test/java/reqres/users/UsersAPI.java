package reqres.users;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.JSONObject;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class UsersAPI {

    private static final String CREATE_USERS_URL = "https://reqres.in/api/users";
    private static final String GET_USERS_URL = "https://reqres.in/api/users/12";
    Response responseCreateUsers;
    Response responseGetUsers;

    @Step("Create a user")
    public void createUser() {
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Test User");
        requestParams.put("job", "Automation Engineer");
        responseCreateUsers = SerenityRest.given().header("Content-Type", "application/json").body(requestParams.toString())
                .post(CREATE_USERS_URL);
    }

    @Step("Verify Create User Response")
    public void verifyCreateUser() {
        InputStream createUserJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/create_user.json");
        assertThat(responseCreateUsers.statusCode(), equalTo(201));
        responseCreateUsers.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(createUserJsonSchema));
    }

    @Step("get a user")
    public void getUser() {
        responseGetUsers = SerenityRest.given().header("Content-Type", "application/json")
                .get(GET_USERS_URL);
    }

    @Step("verify get a user response")
    public void verifyGetUser() {
        InputStream getUserJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("schemas/get_user.json");
        assertThat(responseGetUsers.statusCode(), equalTo(200));
        responseGetUsers.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(getUserJsonSchema));
        assertThat(responseGetUsers.then().extract().path("data.id"), equalTo(12));
        assertThat(responseGetUsers.then().extract().path("data.email"), equalTo("rachel.howell@reqres.in"));
        assertThat(responseGetUsers.then().extract().path("data.first_name"), equalTo("Rachel"));
        assertThat(responseGetUsers.then().extract().path("data.last_name"), equalTo("Howell"));
    }
}
