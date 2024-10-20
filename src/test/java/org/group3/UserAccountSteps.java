package org.group3;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

public class UserAccountSteps {
    private UserAccounts userAccounts; // Benutzerkonten-Manager
    private UserAccounts.User currentUser; // Aktueller Benutzer
    private String currentUserId; // Aktuelle Benutzer-ID

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
        // Angenommen, der Benutzer wurde in einem vorherigen Schritt erstellt
        // In einer echten Anwendung musst du dich möglicherweise anmelden oder den aktuellen Benutzerkontext festlegen
        userAccounts = new UserAccounts(); // Initialisiere erneut das Benutzerkonten-Objekt
        currentUser = userAccounts.createUser("Test User", "test.user@example.com"); // Erstelle einen Benutzer für die Anmeldung
        currentUserId = currentUser.getUserId(); // Speichere die Benutzer-ID
    }

    @When("I add ${int} to my prepaid account")
    public void i_add_$_to_my_prepaid_account(Integer amount) {
        userAccounts.addFunds(currentUserId, amount); // Füge Guthaben zum Prepaid-Konto hinzu
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
}
