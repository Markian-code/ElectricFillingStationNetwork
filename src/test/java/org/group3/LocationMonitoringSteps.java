package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class LocationMonitoringSteps {

    private Location location = new Location();
    private LocationMonitoring locationMonitoring;

    @Given("the following locations exist:")
    public void theFollowingLocationsExist(io.cucumber.datatable.DataTable locationDataTable) {
        List<Map<String, String>> rows = locationDataTable.asMaps();
        for (Map<String, String> columns : rows) {
            location.addLocation(columns.get("name"),
                    columns.get("address"),
                    Double.parseDouble(columns.get("PricePerACkwH")),
                    Double.parseDouble(columns.get("PricePerDCkwH")),
                    Double.parseDouble(columns.get("PriceACMinute")),
                    Double.parseDouble(columns.get("PriceDCMinute")));
        }
    }

    @Given("the following chargers exist:")
    public void theFollowingChargersExist(io.cucumber.datatable.DataTable chargerTable) {
        List<Map<String, String>> rows = chargerTable.asMaps();
        for (Map<String, String> columns : rows) {
            Charger.createCharger(columns.get("ID"), Type.valueOf(columns.get("Type")), Status.valueOf(columns.get("Status")));
        }
    }

    @And("chargers are assigned to locations:")
    public void chargersAreAssignedToLocations(io.cucumber.datatable.DataTable chargerAssignmentTable) {
        List<Map<String, String>> rows = chargerAssignmentTable.asMaps();
        for (Map<String, String> columns : rows) {
            String locationName = columns.get("name");
            String chargerId = columns.get("chargerID");
            location.addChargerToLocation(locationName, chargerId);
        }
    }

    @When("I monitor all locations")
    public void iMonitorAllLocations() {
        locationMonitoring = new LocationMonitoring(location);
        locationMonitoring.displayAllLocationsWithChargers();
    }

    @Then("the system displays all locations with chargers")
    public void theSystemDisplaysAllLocationsWithChargers() {

        locationMonitoring.displayAllLocationsWithChargers();
    }

    @Then("the output should contain the following:")
    public void theOutputShouldContainTheFollowing(io.cucumber.datatable.DataTable expectedOutputTable) {
        List<Map<String, String>> rows = expectedOutputTable.asMaps();

        for (Map<String, String> expectedRow : rows) {
            String expectedLocation = expectedRow.get("name");
            String expectedChargerId = expectedRow.get("Charger ID");
            String expectedType = expectedRow.get("Type");
            String expectedStatus = expectedRow.get("Status");

            LocationData locationData = location.getLocationByName(expectedLocation).orElse(null);
            assertNotNull(locationData, "Location " + expectedLocation + " should exist");

            List<Charger> chargers = locationData.getChargers();
            boolean found = false;
            for (Charger charger : chargers) {
                if (charger.getChargerId().equals(expectedChargerId)) {
                    assertEquals(Type.valueOf(expectedType), charger.getType(), "Charger type mismatch");
                    assertEquals(Status.valueOf(expectedStatus), charger.getStatus(), "Charger status mismatch");
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Charger with ID " + expectedChargerId + " should be found at location " + expectedLocation);
        }
    }
}

