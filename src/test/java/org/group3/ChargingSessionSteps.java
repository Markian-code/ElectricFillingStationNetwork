package org.group3;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargingSessionSteps {
    ChargingSession session = new ChargingSession();
    private UserAccounts userAccounts; // Benutzerkonten-Manager
    private UserAccounts.User currentUser; // Aktueller Benutzer
    private String currentUserId; // Aktuelle Benutzer-ID

    @When("I have a user account")
    public void iHaveAUserAccountWithABalanceOf(double amount) {
        userAccounts = new UserAccounts();
        currentUser = userAccounts.createUser("Test User", "test.user@example.com");
        currentUserId = currentUser.getUserId();
        userAccounts.addFunds(currentUserId, amount);
    }

    @And("I plugged in my car at")
    public void iPluggedInMyCarAt(DataTable chargingSessionDataTable) {
        List<Map<String, String>> rows = chargingSessionDataTable.asMaps();
        for (Map<String, String> columns : rows) {
            session.createChargingSession(columns.get("Location name"), Integer.valueOf("charger"), columns.get("Type"), Double.valueOf("price DC"),
                                            Double.valueOf("price per minute"), columns.get("starttime"));
        }
    }

    @Then("a charging session is created")
    public void aChargingSessionIsCreated() {
        assertEquals(1, session.getSessionCount());
    }
}
