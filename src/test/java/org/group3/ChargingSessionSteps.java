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
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private UserAccount userAccount;

    @Before
    public void setUp() {
        ecs = new ECS();
    }

    @Given("user {string} with email {string} and prepaid balance of {double} exists")
    public void userIsRegistered(String username, String email, double balance) {
        userAccount = new UserAccount(username, email);
        userAccount.addFunds(balance);
        ecs.addUser(userAccount);

    }


    @When("user with email {string} starts a charging session at {string} with charger {string} consuming {double} kWh at {string}")
    public void userStartsChargingSession(String email, String locationName, String chargerId, double powerConsumed, String startTime) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime, formatter);
            ecs.createChargingSession(email, locationName, chargerId, start, powerConsumed);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }


    @And("user with email {string} stops the charging session at {string}")
    public void userStopsChargingSession(String email, String endTime) {
        try {
            LocalDateTime end = LocalDateTime.parse(endTime, formatter);
            ecs.endChargingSession(email, end);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }


    @Then("the total charging time for user with email {string} is {int} minutes")
    public void theTotalChargingTimeForUserIs(String email, int expectedMinutes) {
        List<ChargingSession> sessions = ecs.getChargingSessionsByUser(email);
        assertFalse(sessions.isEmpty(), "No charging session found for user " + email);
        ChargingSession session = sessions.get(sessions.size() - 1); // Letzte Sitzung des Benutzers

        assertTrue(session.isSessionEnded(), "Charging session has not ended.");
        assertEquals(expectedMinutes, session.getDuration().toMinutes(), "Charging duration does not match.");
    }


    @And("the total cost for user with email {string} is €{double}")
    public void theTotalCostForUserIs(String email, double expectedCost) {
        List<ChargingSession> sessions = ecs.getChargingSessionsByUser(email);
        assertFalse(sessions.isEmpty(), "No charging session found for user " + email);
        ChargingSession session = sessions.get(sessions.size() - 1);

        assertTrue(session.isSessionEnded(), "Charging session has not ended.");
        assertEquals(expectedCost, session.getTotalCost(), 0.01, "Charging cost does not match.");
    }

    @And("the new balance for user with email {string} should be {double}")
    public void theUserSBalanceShouldBe(String email, double expectedBalance) {
        UserAccount actualUserAccount = ecs.getUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + email + "' not found"));
        assertEquals(expectedBalance, actualUserAccount.getBalance(), 0.01);
    }



    //Error-Cases------------------------

    @When("user with email {string} tries to calculate the total cost")
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
