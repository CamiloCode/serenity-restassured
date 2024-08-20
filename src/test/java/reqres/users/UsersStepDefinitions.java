package reqres.users;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class UsersStepDefinitions {

    @Steps
    UsersAPI usersAPI;

    @When("a user is created")
    public void createUser() {
        usersAPI.createUser();
        usersAPI.verifyCreateUser();
    }

    @Then("the user data is correct")
    public void theUserInformationIsCorrect() {
        usersAPI.getUser();
        usersAPI.verifyGetUser();
    }
}
