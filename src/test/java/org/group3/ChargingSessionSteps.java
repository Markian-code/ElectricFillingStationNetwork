package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ChargingSessionSteps {

    private Location locationManager;
    private LocalDateTime sessionStartTime;
    private ChargingSession currentSession;

    @Given("the following location exists:")
    public void theFollowingLocationExists(io.cucumber.datatable.DataTable locationDataTable) {
        locationManager = new Location();
        List<Map<String, String>> locations = locationDataTable.asMaps();
        for (Map<String, String> loc : locations) {
            String name = loc.get("name");
            String address = loc.get("address");
            double pricePerACkwH = Double.parseDouble(loc.get("pricePerACkwH"));
            double pricePerDCkwH = Double.parseDouble(loc.get("pricePerDCkwH"));
            double priceACMinute = Double.parseDouble(loc.get("priceACMinute"));
            double priceDCMinute = Double.parseDouble(loc.get("priceDCMinute"));
            locationManager.addLocation(name, address, pricePerACkwH, pricePerDCkwH, priceACMinute, priceDCMinute);
        }
    }

    @Given("the following charger exists:")
    public void theFollowingChargerExists(io.cucumber.datatable.DataTable chargerDataTable) {
        List<Map<String, String>> chargers = chargerDataTable.asMaps();
        for (Map<String, String> charger : chargers) {
            String chargerId = charger.get("ID");
            Type type = Type.valueOf(charger.get("Type"));
            Status status = Status.valueOf(charger.get("Status"));
            Charger.createCharger(chargerId, type, status);
        }
    }

    @When("user {string} starts a charging session at {string} using charger {string} with {double} kWh")
    public void userStartsChargingSession(String username, String locationName, String chargerId, double powerConsumed) {
        // Hole die LocationData anhand des Location-Namens
        LocationData locationData = locationManager.getLocationByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location '" + locationName + "' not found"));

        // Prüfe, ob der Charger existiert
        Charger charger = Charger.getChargerById(chargerId);
        if (charger == null) {
            throw new IllegalArgumentException("Charger with ID '" + chargerId + "' does not exist.");
        }

        sessionStartTime = LocalDateTime.now();  // Setze die Startzeit der Session
        // Erstelle eine neue ChargingSession und übergebe den locationName und charger
        currentSession = new ChargingSession(username, sessionStartTime, locationManager, locationName, charger, powerConsumed);
    }

    @When("the session lasts {int} minutes")
    public void theSessionLasts(int minutes) {
        LocalDateTime sessionEndTime = sessionStartTime.plusMinutes(minutes);
        currentSession.endSession(sessionEndTime);
    }

    @Then("the total cost for the session should be €{double}")
    public void totalCostForTheSessionShouldBe(double expectedCost) {
        assertEquals(expectedCost, currentSession.getTotalCost(), 0.01);
    }

    @Then("the session duration should be {int} minutes")
    public void theSessionDurationShouldBe(int expectedMinutes) {
        assertEquals(expectedMinutes, currentSession.getDuration().toMinutes());
    }

    @Then("the session for user {string} should exist in the list of sessions")
    public void theSessionShouldExistInTheList(String username) {
        boolean sessionExists = ChargingSession.getAllSessions().stream()
                .anyMatch(session -> session.getUsername().equals(username));
        assertTrue(sessionExists, "Session for user " + username + " should exist");
    }
}
