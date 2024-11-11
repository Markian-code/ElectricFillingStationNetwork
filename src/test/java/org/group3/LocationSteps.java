package org.group3;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LocationSteps {

    private ECS ecs;  // Verwalte alle Logik über ECS
    private String errorMessage = "";


    @Before
    public void setUp() {
        ecs = new ECS();
    }
    // Background: Given the following locations exist
    @Given("the following locations exist:")
    public void theFollowingLocationsExist(io.cucumber.datatable.DataTable locationDataTable) {
        List<Map<String, String>> rows = locationDataTable.asMaps();
        for (Map<String, String> columns : rows) {
            Location location = new Location(
                    columns.get("name"),
                    columns.get("address"),
                    Double.parseDouble(columns.get("PricePerACkwH")),
                    Double.parseDouble(columns.get("PricePerDCkwH")),
                    Double.parseDouble(columns.get("PriceACMinute")),
                    Double.parseDouble(columns.get("PriceDCMinute"))
            );
            ecs.addLocation(location);
        }
    }

    // Background: Given the following chargers exist at each location
    @Given("the following chargers exist at each location:")
    public void theFollowingChargersExistAtEachLocation(io.cucumber.datatable.DataTable chargerDataTable) {
        List<Map<String, String>> rows = chargerDataTable.asMaps();
        for (Map<String, String> columns : rows) {
            ecs.addChargerToLocation(
                    columns.get("Location"),
                    columns.get("ChargerID"),
                    ChargerType.valueOf(columns.get("Type")),
                    ChargerStatus.valueOf(columns.get("Status"))
            );
        }
    }




    @Then("location {string} has {int} charger")
    public void locationHasChargers(String locationName, int expectedChargerCount) {
        List<String> chargerIds = ecs.getChargersAtLocation(locationName);
        assertEquals(expectedChargerCount, chargerIds.size());
    }

    @And("charger with ID {string} is of type {string} and status {string}")
    public void chargerWithIDIsOfTypeAndStatus(String chargerId, String expectedType, String expectedStatus) {
        Charger charger = ecs.getCharger(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger not found"));
        assertEquals(ChargerType.valueOf(expectedType), charger.getType());
        assertEquals(ChargerStatus.valueOf(expectedStatus), charger.getStatus());
    }

    @When("owner sets the price for location {string} PricePerACkwH to {double}")
    public void ownerSetsThePricePerACkwHForLocation(String name, double newPricePerACkwH) {
        ecs.updatePrice(name, newPricePerACkwH, PriceType.AC_KWH);
    }

    @And("sets for location {string} PriceACMinute to {double}")
    public void setsForLocationPriceACMinuteTo(String name, double newPriceACMinute) {
        ecs.updatePrice(name, newPriceACMinute, PriceType.AC_MINUTE);
    }

    @Then("the prices for location {string} are:")
    public void thePricesForLocationAre(String name, io.cucumber.datatable.DataTable expectedPricesTable) {
        List<Map<String, String>> rows = expectedPricesTable.asMaps();
        Location location = ecs.getLocation(name)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        for (Map<String, String> columns : rows) {
            double expectedPricePerACkwH = Double.parseDouble(columns.get("PricePerACkwH"));
            double expectedPricePerDCkwH = Double.parseDouble(columns.get("PricePerDCkwH"));
            double expectedPriceACMinute = Double.parseDouble(columns.get("PriceACMinute"));
            double expectedPriceDCMinute = Double.parseDouble(columns.get("PriceDCMinute"));

            assertEquals(expectedPricePerACkwH, location.getPricePerACkwH(), 0.01);
            assertEquals(expectedPricePerDCkwH, location.getPricePerDCkwH(), 0.01);
            assertEquals(expectedPriceACMinute, location.getPriceACMinute(), 0.01);
            assertEquals(expectedPriceDCMinute, location.getPriceDCMinute(), 0.01);
        }
    }

    @When("owner adds the following chargers to {string}:")
    public void ownerAddsTheFollowingChargersToLocation(String locationName, io.cucumber.datatable.DataTable chargerTable) {
        List<Map<String, String>> rows = chargerTable.asMaps();
        for (Map<String, String> columns : rows) {
            ecs.addChargerToLocation(
                    locationName,
                    columns.get("ID"),
                    ChargerType.valueOf(columns.get("Type")),
                    ChargerStatus.valueOf(columns.get("Status"))
            );
        }
    }

    @When("owner removes the charger with ID {string} from location {string}")
    public void ownerRemovesChargerFromLocation(String chargerId, String locationName) {
        ecs.removeChargerFromLocation(locationName, chargerId);
    }

    @Then("charger with ID {string} should not exist at location {string}")
    public void chargerShouldNotExistAtLocation(String chargerId, String locationName) {
        List<String> chargerIds = ecs.getChargersAtLocation(locationName);
        boolean chargerExists = chargerIds.stream().anyMatch(id -> id.equals(chargerId));
        assertFalse(chargerExists);
    }

    @When("owner sets the status of charger with ID {string} at location {string} to {string}")
    public void ownerSetsStatusOfChargerAtLocationTo(String chargerId, String locationName, String newStatus) {
        try {
            ecs.updateChargerStatus(locationName, chargerId, ChargerStatus.valueOf(newStatus));
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("charger with ID {string} should have type {string} and status {string}")
    public void chargerWithIDShouldHaveTypeAndStatus(String chargerId, String expectedType, String expectedStatus) {
        Charger charger = ecs.getCharger(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger not found"));
        assertEquals(ChargerType.valueOf(expectedType), charger.getType());
        assertEquals(ChargerStatus.valueOf(expectedStatus), charger.getStatus());
    }

    @When("owner deletes the location with name {string}")
    public void theOwnerDeletesTheLocationWithName(String name) {
        try {
            ecs.removeLocation(name);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("an error message should be displayed saying {string}")
    public void anErrorMessageShouldBeDisplayedSaying(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, errorMessage);
    }

    @When("owner adds the charger with ID {string} to the location {string}")
    public void theOwnerAddsTheChargerWithIDToLocation(String chargerId, String locationName) {
        try {
            ecs.addChargerToLocation(locationName, chargerId, ChargerType.AC, ChargerStatus.FREI);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @When("owner tries to change the status of the charger with ID {string} at location {string} to {string}")
    public void theOwnerTriesToChangeTheStatusOfChargerAtLocationTo(String chargerId, String locationName, String newStatus) {
        try {
            ecs.updateChargerStatus(locationName, chargerId, ChargerStatus.valueOf(newStatus));
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }
}