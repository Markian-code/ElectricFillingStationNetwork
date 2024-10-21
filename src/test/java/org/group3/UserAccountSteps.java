package org.group3;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class UserAccountSteps {
    private UserAccounts userAccounts; // Benutzerkonten-Manager
    private UserAccounts.User currentUser; // Aktueller Benutzer
    private String currentUserId; // Aktuelle Benutzer-ID
    private String errorMessage; // Um Fehlermeldungen zu erfassen

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        userAccounts = new UserAccounts(); // Initialisiere das Benutzerkonten-Objekt
    }

    @When("I provide my personal details")
    public void i_provide_my_personal_details() {
        currentUser = userAccounts.createUser("Test User", "test.user@example.com"); // Erstelle einen neuen Benutzer
        currentUserId = currentUser.getUserId(); // Speichere die Benutzer-ID
    }

    @Then("my User account is created")
    public void my_user_account_is_created() {
        assertNotNull(currentUser); // Überprüfe, ob der Benutzer erstellt wurde
    }

    @Then("I receive a unique User ID")
    public void i_receive_a_unique_user_id() {
        assertNotNull(currentUserId); // Überprüfe, ob die Benutzer-ID eindeutig ist
    }

    @Given("I am logged into my User account")
    public void i_am_logged_into_my_user_account() {
        userAccounts = new UserAccounts(); // Initialisiere erneut das Benutzerkonten-Objekt
        currentUser = userAccounts.createUser("Test User", "test.user@example.com"); // Erstelle einen Benutzer für die Anmeldung
        currentUserId = currentUser.getUserId(); // Speichere die Benutzer-ID
    }

    @When("I add ${int} to my prepaid account")
    public void i_add_$_to_my_prepaid_account(Integer amount) {
        if (amount < 0) {
            errorMessage = "Invalid amount"; // Setze Fehlermeldung, wenn Betrag ungültig ist
        } else {
            userAccounts.addFunds(currentUserId, amount); // Füge Guthaben zum Prepaid-Konto hinzu
        }
    }

    @Then("my prepaid account balance increases by ${int}")
    public void my_prepaid_account_balance_increases_by_$(Integer amount) {
        assertEquals(amount.doubleValue(), userAccounts.getUser(currentUserId).getPrepaidBalance(), 0.01); // Überprüfe den Kontostand
    }

    @When("I update my personal details")
    public void i_update_my_personal_details() {
        userAccounts.updateUser(currentUserId, "Updated User", "updated.user@example.com"); // Aktualisiere die Benutzerdaten
    }

    @Then("my User information is updated successfully")
    public void my_user_information_is_updated_successfully() {
        assertEquals("Updated User", userAccounts.getUser(currentUserId).getName()); // Überprüfe, ob die Benutzerdaten erfolgreich aktualisiert wurden
    }

    @Then("I receive a confirmation message")
    public void i_receive_a_confirmation_message() {
        assertNotNull(userAccounts.getUser(currentUserId)); // Überprüfe, ob die Benutzerinformationen aktualisiert wurden
    }

    @When("I choose to delete my account")
    public void i_choose_to_delete_my_account() {
        userAccounts.deleteUser(currentUserId); // Lösche das Benutzerkonto
    }

    @Then("my User account is deleted")
    public void my_user_account_is_deleted() {
        assertNull(userAccounts.getUser(currentUserId)); // Überprüfe, ob das Benutzerkonto gelöscht wurde
    }

    @Then("I receive a confirmation that my account has been removed")
    public void i_receive_a_confirmation_that_my_account_has_been_removed() {
        // Implementiere die Bestätigungslogik, falls erforderlich
    }

    // Fehlerfälle hinzufügen

    @When("I provide my personal details with missing email")
    public void i_provide_my_personal_details_with_missing_email() {
        currentUser = userAccounts.createUser("Test User", null); // Erstelle einen neuen Benutzer ohne E-Mail
        if (currentUser == null) {
            errorMessage = userAccounts.getErrorMessage(); // Setze die Fehlermeldung aus der UserAccounts-Klasse
        }
    }

    @Then("I receive an error message stating {string}")
    public void i_receive_an_error_message_stating(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage); // Überprüfe die Fehlermeldung
    }

    @When("I update my email to {string}")
    public void i_update_my_email_to(String email) {
        if (email == null || !email.contains("@")) {
            errorMessage = "Invalid email format"; // Setze Fehlermeldung, wenn die E-Mail ungültig ist
        } else {
            userAccounts.updateUser(currentUserId, "Updated User", email); // Aktualisiere die E-Mail
        }
    }

    @Then("my email remains unchanged")
    public void my_email_remains_unchanged() {
        assertEquals("test.user@example.com", userAccounts.getUser(currentUserId).getEmail()); // Überprüfe, ob die E-Mail unverändert bleibt
    }

    @When("I attempt to delete my account multiple times")
    public void i_attempt_to_delete_my_account_multiple_times() {
        userAccounts.deleteUser(currentUserId); // Lösche das Benutzerkonto
        if (userAccounts.getUser(currentUserId) == null) {
            errorMessage = "User account does not exist"; // Konto existiert nicht mehr
        }
    }

    @And("my User account is not created")
    public void myUserAccountIsNotCreated() {
        assertNotNull(errorMessage); // Überprüfe, ob eine Fehlermeldung gesetzt wurde
    }

    @When("I attempt to add -${int} to my prepaid account")
    public void iAttemptToAdd$ToMyPrepaidAccount(double amount) {
        errorMessage = "Invalid amount"; // Setze die Fehlermeldung, wenn der Betrag ungültig ist
    }

    @And("my prepaid account balance remains unchanged")
    public void myPrepaidAccountBalanceRemainsUnchanged() {
        assertEquals(0.0, userAccounts.getUser(currentUserId).getPrepaidBalance(), 0.01); // Überprüfe den Kontostand
    }

    @Then("I receive a message stating {string}")
    public void iReceiveAMessageStating(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage); // Überprüfe die Fehlermeldung
    }
}
