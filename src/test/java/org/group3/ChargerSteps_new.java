package org.group3;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChargerSteps_new {

    @When("owner creates the following chargers:")
    public void theFollowingChargersExist(io.cucumber.datatable.DataTable existingchargerDataTable) {
        List<Map<String, String>> rows = existingchargerDataTable.asMaps();
        for (Map<String, String> row : rows) {
            String chargerId = row.get("ID");
            Type type = Type.valueOf(row.get("Type"));
            Status status = Status.valueOf(row.get("Status"));

            Charger.createCharger(chargerId, type, status);
        }
    }

    @Then("charger with ID {string} should exist")
    public void chargerShouldExist(String chargerId) {
        Charger charger = Charger.getChargerById(chargerId);
        assertNotNull (charger, "Charger with ID " + chargerId + " should exist");
    }

    @Then("charger with ID {string} should have type {string} and status {string}")
    public void chargerShouldHaveTypeAndStatus(String chargerId, String expectedType, String expectedStatus) {
        Charger charger = Charger.getChargerById(chargerId);
        assertEquals(Type.valueOf(expectedType), charger.getType());
        assertEquals(Status.valueOf(expectedStatus), charger.getStatus());
    }

    @Given("the following chargers are created:")
    public void theFollowingChargersAreCreated(DataTable chargerDataTable) {
        List<Map<String, String>> rows = chargerDataTable.asMaps();
        for (Map<String, String> row : rows) {
            String chargerId = row.get("ID");
            Type type = Type.valueOf(row.get("Type"));
            Status status = Status.valueOf(row.get("Status"));
            Charger.createCharger(chargerId, type, status);
        }
    }

    @When("the charger with ID {string} is deleted")
    public void theChargerWithIdIsDeleted(String chargerId) {
        Charger.deleteCharger(chargerId);
    }


    @Then("charger with ID {string} should not longer exist")
    public void chargerWithIDShouldNotLongerExist(String chargerId) {
        Charger charger = Charger.getChargerById(chargerId);
        assertNull(charger, "Charger with ID " + chargerId + "does not exist");
    }


    @And("number of charges is {int}.")
    public void numberOfChargesIs(int expectedCount) {
        int actuelCount = Charger.getTotalNumberOfChargers();
        assertEquals(expectedCount, actuelCount);
    }

    @When("the status of charger with ID {string} is updated to {string}")
    public void theStatusOfChargerWithIDIsUpdatedTo(String chargerID, String newStatus) {
        Charger charger = Charger.getChargerById(chargerID);
        assertNotNull(charger, "Charger with ID " + chargerID + " should exist");
        charger.setStatus(Status.valueOf(newStatus));
    }


    @And("charger with ID {string} should still have status {string}")
    public void chargerWithIDShouldStillHaveStatus(String chargerId, String expectedStatus) {
        Charger charger = Charger.getChargerById(chargerId);
        assertNotNull(charger, "Charger with ID " + chargerId + " should exist");
        assertEquals(Status.valueOf(expectedStatus), charger.getStatus());
    }


}
