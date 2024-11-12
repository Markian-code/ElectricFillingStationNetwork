package org.group3;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChargingSessionSteps {

    private ECS ecs;
    private String errorMessage;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Before
    public void setUp() {
        ecs = new ECS();
    }

    @Given("user {string} with email {string} and prepaid balance of {double} added at {string} exists")
    public void userWithPrepaidBalanceAndTimestampExists(String name, String email, double balance, String timestamp) {
        LocalDateTime addedAt = LocalDateTime.parse(timestamp, formatter);
        UserAccount userAccount = new UserAccount(name, email);
        userAccount.addLedgerEntry(LedgerType.TOP_UP, balance, addedAt); // Guthaben mit Zeitstempel hinzufügen
        ecs.addUser(userAccount);
    }

    @When("user with email {string} starts a charging session at {string} with charger {string} consuming {double} kWh at {string}")
    public void userStartsChargingSession(String email, String locationName, String chargerId, double powerConsumed, String startTime) {
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        ecs.createChargingSession(email, locationName, chargerId, start, powerConsumed);
    }

    @And("user with email {string} stops the charging session at {string}")
    public void userStopsChargingSession(String email, String endTime) {
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);
        ecs.endChargingSession(email, end);
    }

    @Then("the total charging time for user with email {string} is {int} minutes")
    public void theTotalChargingTimeForUserIs(String email, int expectedMinutes) {
        List<ChargingSession> sessions = ecs.getChargingSessionsByUser(email);
        assertFalse(sessions.isEmpty(), "No charging session found for user " + email);
        ChargingSession session = sessions.get(sessions.size() - 1);

        assertTrue(session.isSessionEnded(), "Charging session has not ended.");
        assertEquals(expectedMinutes, session.getDuration().toMinutes(), "Charging duration does not match.");
    }

    @And("the total cost for user with email {string} is €{double}")
    public void theTotalCostForUserIs(String email, double expectedCost) {
        Invoice invoice = new Invoice(email, ecs);
        double actualCost = invoice.calculateTotalCost();
        assertEquals(expectedCost, actualCost, 0.01, "Total cost does not match.");
    }

    @And("the new balance for user with email {string} should be {double}")
    public void theUserSBalanceShouldBe(String email, double expectedBalance) {
        UserAccount userAccount = ecs.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + email + "' not found"));
        assertEquals(expectedBalance, userAccount.getBalance(), 0.01, "New balance does not match.");
    }


//Error-Cases------------------------

    @When("user with email {string} wants to view the total cost")
    public void userTriesToCalculateTheTotalCost(String username) {
        try {
            ChargingSession activeSession = ecs.getChargingSessionsByUser(username).stream()
                    .filter(session -> session.getSessionState() == SessionState.ACTIVE)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No active session found for user '" + username + "'"));

            // Hier wird die Methode getTotalCost() aufgerufen, die die IllegalStateException werfen sollte
            activeSession.getTotalCost();
        } catch (IllegalStateException e) {
            // Speichere die Fehlermeldung
            errorMessage = e.getMessage();
        }

    }

    @Then("an error message should be displayed {string}")
    public void anErrorMessageBeDisplayedSaying(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, errorMessage);
    }


}