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

    private ECS ecs;
    private boolean operationResult; // Für Rückgabewerte von Operationen wie "addFundsToUser"

    @Before
    public void setup() {
        // Initialisiere ECS und Benutzer vor jedem Szenario
        ecs = new ECS();
    }

    @Given("the following users exist:")
    public void the_following_users_exist(List<Map<String, String>> users) {
        for (Map<String, String> userData : users) {
            String email = userData.get("email");
            if (ecs.getUserByEmail(email).isEmpty()) { // Benutzer hinzufügen, falls noch nicht vorhanden
                UserAccount userAccount = new UserAccount(userData.get("name"), email);
                double prepaidBalance = Double.parseDouble(userData.get("prepaidBalance"));
                userAccount.addFunds(prepaidBalance); // Prepaid-Balance setzen
                ecs.addUser(userAccount);
            }
        }
    }

    @When("the user adds a user with name {string} and email {string}")
    public void i_add_a_user_with_name_and_email(String name, String email) {
        ecs.addUser(new UserAccount(name, email));
    }

    @Then("the user {string} with mail {string} should be in the system")
    public void the_user_should_be_in_the_system(String name, String email) {
        assertTrue(ecs.getUserByEmail(email).isPresent(), "User is not in the system");
        assertEquals(name, ecs.getUserByEmail(email).get().getName(), "User name does not match");
    }

    @Then("the total number of users should be {int}")
    public void the_total_number_of_users_should_be(Integer totalUsers) {
        assertEquals(totalUsers.intValue(), ecs.getNumberOfUsers(), "Total number of users does not match");
    }

    @When("the user Charlie with email {string} is removed")
    public void i_remove_the_user_with_email(String email) {
        ecs.removeUserByEmail(email);
    }

    @Then("the user with email {string} should not be in the system")
    public void the_user_with_email_should_not_be_in_the_system(String email) {
        assertFalse(ecs.getUserByEmail(email).isPresent(), "User is still in the system");
    }

    @When("the user {string} with the email {string} updates their account to {string} and email {string}")
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
        Optional<UserAccount> userOptional = ecs.getUserByEmail(email);
        assertTrue(userOptional.isPresent(), "User with updated email is not in the system");
        assertEquals(newName, userOptional.get().getName(), "User name does not match the updated value");
    }

    @When("the user adds {double} to their user account with email {string}")
    public void i_add_to_the_user_with_name_and_email(Double amount, String email) {
        ecs.getUserByEmail(email).ifPresent(user -> operationResult = ecs.addFundsToUser(user.getEmail(), amount));
    }

    @Then("the user {string} with email {string} should have a prepaid balance of {double}")
    public void the_user_should_have_a_prepaid_balance_of(String name, String email, Double expectedBalance) {
        Optional<UserAccount> userOptional = ecs.getUserByEmail(email);
        assertTrue(userOptional.isPresent(), "User is not in the system");
        assertEquals(expectedBalance, userOptional.get().getPrepaidBalance(), 0.01, "User prepaid balance does not match");
    }

}
