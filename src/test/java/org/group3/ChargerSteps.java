package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

public class ChargerSteps {

    private final Map<String, String> chargers = new HashMap<>(); // Store chargers by their IDs
    private String lastErrorMessage = ""; // Store the last error message

    @Given("I have no chargers")
    public void iHaveNoChargers() {
        chargers.clear();
        System.out.println("There are no chargers in the system.");
    }

    @When("I add a charger with ID {string}")
    public void iAddAChargerWithID(String id) {
        if (!chargers.containsKey(id)) {
            addCharger(id);
        } else {
            lastErrorMessage = "Charger ID already exists";
            System.out.println(lastErrorMessage);
        }
    }

    @When("I add another charger with ID {string}")
    public void iAddAnotherChargerWithID(String id) {
        if (!chargers.containsKey(id)) {
            addCharger(id);  // Add another charger with a new ID
        } else {
            System.out.println("Charger with ID " + id + " already exists.");
        }
    }

    @Then("the charger with ID {string} should be added to the system")
    public void theChargerWithIDShouldBeAddedToTheSystem(String id) {
        if (chargers.containsKey(id)) {
            System.out.println("Charger with ID " + id + " has been successfully added.");
        } else {
            System.out.println("Failed to add charger with ID " + id);
        }
    }

    @Given("I have a charger with ID {string}")
    public void iHaveAChargerWithID(String id) {
        chargers.put(id, "Charger " + id);
        System.out.println("A charger with ID " + id + " is present in the system.");
    }

    @When("I delete the charger with ID {string}")
    public void iDeleteTheChargerWithID(String id) {
        if (chargers.containsKey(id)) {
            deleteCharger(id);
        } else {
            System.out.println("No charger with ID " + id + " found.");
        }
    }

    @Then("the charger with ID {string} should be removed from the system")
    public void theChargerWithIDShouldBeRemovedFromTheSystem(String id) {
        if (!chargers.containsKey(id)) {
            System.out.println("Charger with ID " + id + " has been successfully removed.");
        } else {
            System.out.println("Failed to remove charger with ID " + id);
        }
    }

    public void addCharger(String id) {
        chargers.put(id, "Charger " + id);
        System.out.println("Charger with ID " + id + " added.");
    }

    public void deleteCharger(String id) {
        chargers.remove(id);
        System.out.println("Charger with ID " + id + " deleted.");
    }

    @Given("I have an existing charger with ID {string}")
    public void iHaveAnExistingChargerWithID(String arg0) {
        chargers.put(arg0, "Charger " + arg0);
        System.out.println("A charger with ID " + arg0 + " is present in the system.");
    }
    @Then("an error should be reported saying {string}")
    public void anErrorShouldBeReportedSaying(String errorMessage) {
        assert errorMessage.equals(lastErrorMessage) : "Expected: " + errorMessage + ", but found: " + lastErrorMessage;

    }
}
