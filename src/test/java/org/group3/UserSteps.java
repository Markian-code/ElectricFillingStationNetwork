package org.group3;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps {

    private ECS ecs = new ECS();
    private boolean operationResult;


    @Before
    public void setup() {
        // Initialisiere die Benutzer nur einmal
        ecs = new ECS();
    }


    @Given("the following users exist:")
    public void the_following_users_exist(List<Map<String, String>> users) {
        for (Map<String, String> userData : users) {
            String email = userData.get("email");
            if (ecs.getUserByEmail(email).isEmpty()) { // Prüfe, ob Benutzer bereits existiert
                UserAccount userAccount = new UserAccount(userData.get("name"), email);
                userAccount.addFunds(Double.parseDouble(userData.get("prepaidBalance")));
                ecs.addUser(userAccount);
            }
        }
    }

    @When("I add a user with name {string} and email {string}")
    public void i_add_a_user_with_name_and_email(String name, String email) {
        ecs.addUser(new UserAccount(name, email));
    }

    @Then("the user {string} with mail {string} should be in the system")
    public void the_user_should_be_in_the_system(String name, String email) {
        assertTrue(ecs.getUserByEmail(email).isPresent());
    }

    @Then("the total number of users should be {int}")
    public void the_total_number_of_users_should_be(Integer totalUsers) {
        assertEquals(totalUsers.intValue(), ecs.getNumberOfUsers());
    }

    @When("I remove the user Charlie with email {string}")
    public void i_remove_the_user_with_email(String email) {
        ecs.removeUserByEmail(email);
    }

    @Then("the user with email {string} should not be in the system")
    public void the_user_with_email_should_not_be_in_the_system(String email) {
        assertFalse(ecs.getUserByEmail(email).isPresent());
    }

    @When("I update user {string} and email {string} to have new name {string} and email {string}")
    public void i_update_user_and_email_to_have_new_name_and_email(String currentName, String currentEmail, String newName, String newEmail) {
        Optional<UserAccount> userOptional = ecs.getUserByEmail(currentEmail);

        if (userOptional.isPresent()) {
            ecs.updateUser(currentEmail, newName, newEmail);
        } else {
            throw new IllegalArgumentException("User with email " + currentEmail + " does not exist.");
        }
    }

    @Then("the user should now have name {string} and their email should be {string}")
    public void the_user_should_now_have_name(String newName, String email) {
        assertTrue(ecs.getUserByEmail(email).isPresent());
        assertEquals(newName, ecs.getUserByEmail(email).get().getName());
    }


    @When("I add {double} to my user account with email {string}")
    public void i_add_to_the_user_with_name_and_email(Double amount, String email) {
        ecs.getUserByEmail(email).ifPresent(user -> operationResult = ecs.addFundsToUser(user.getEmail(), amount));
    }

    @Then("the user {string} with email {string} should have a prepaid balance of {double}")
    public void the_user_should_have_a_prepaid_balance_of(String name, String email, Double expectedBalance) {
        assertTrue(ecs.getUserByEmail(email).isPresent());
        assertEquals(expectedBalance, ecs.getUserByEmail(email).get().getPrepaidBalance(), 0.01);
    }

}