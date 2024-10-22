package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class LocationSteps {

        Location location = new Location();
        private String errorMessage = "";

        @When("owner adds the following location:")
        public void ownerAddsTheFollowingLocations(io.cucumber.datatable.DataTable locationDataTable) {
                List<Map<String, String>> rows = locationDataTable.asMaps();
                for (Map<String, String> columns : rows) {
                    location.addLocation(columns.get("name"), columns.get("address"),Double.parseDouble(columns.get("PricePerACkwH")),
                            Double.parseDouble(columns.get("PricePerDCkwH")),
                            Double.parseDouble(columns.get("PriceACMinute")),
                            Double.parseDouble(columns.get("PriceDCMinute")));
                }
        }

        @Then("the number of locations is {int}")
                public void theNumberOfLocationsIs(int expected) {
                assertEquals(expected, location.getLocationCount());
        }


        @And("the location with name {string} is located at {string}")
        public void theLocationWithNameIsLocatedAt(String name, String expectedAddress) {
                assertEquals(expectedAddress, location.getLocationAddressByName(name));
        }



        @Given("the following locations exists:")
        public void theFollowingLocationExists(io.cucumber.datatable.DataTable locationDataTable) {
                List<Map<String, String>> rows = locationDataTable.asMaps();
                for (Map<String, String> columns : rows) {
                        location.addLocation(columns.get("name"), columns.get("address"),Double.parseDouble(columns.get("PricePerACkwH")),
                                Double.parseDouble(columns.get("PricePerDCkwH")),
                                Double.parseDouble(columns.get("PriceACMinute")),
                                Double.parseDouble(columns.get("PriceDCMinute")));
                }
        }

        @When("owner deletes the location with name {string}")
        public void ownerDeletesTheLocationWithName(String name) {
                location.deleteLocation(name);
        }

        @Then("the new number of locations is {int}")
        public void theNewNumberOfLocationsIs(int expected) {
                assertEquals(expected, location.getLocationCount());
        }

        @And("the location with name {string} exists")
        public void theLocationWithNameExists(String name) {
            assertTrue(location.locationExists(name));
        }

        @Given("the following locations has this prices:")
        public void theFollowingLocationHasThisPrices(io.cucumber.datatable.DataTable locationDataTable) {
                List<Map<String, String>> rows = locationDataTable.asMaps();
                for (Map<String, String> columns : rows) {
                        location.addLocation(columns.get("name"), columns.get("address"),Double.parseDouble(columns.get("PricePerACkwH")),
                                Double.parseDouble(columns.get("PricePerDCkwH")),
                                Double.parseDouble(columns.get("PriceACMinute")),
                                Double.parseDouble(columns.get("PriceDCMinute")));
                }
        }


        @When("owner set for location {string} PricePerACkwH to {double}")
        public void ownerSetPricePerACkwHTo(String name, double newPricePerACkwH) {
                location.updatePricePerACkwH(name, newPricePerACkwH);
        }


        @And("set for location {string} PriceACMinute to {double}")
        public void setPriceACMinuteTo(String name, double newPriceACMinute) {
                location.updatePriceACMinute(name, newPriceACMinute);
        }


        @Then("the prices for location {string} are:")
        public void thePricesForLocationAre(String name, io.cucumber.datatable.DataTable expectedPricesTable) {
                List<Map<String, String>> rows = expectedPricesTable.asMaps();
                for (Map<String, String> columns : rows) {
                        double expectedPricePerACkwH = Double.parseDouble(columns.get("PricePerACkwH"));
                        double expectedPricePerDCkwH = Double.parseDouble(columns.get("PricePerDCkwH"));
                        double expectedPriceACMinute = Double.parseDouble(columns.get("PriceACMinute"));
                        double expectedPriceDCMinute = Double.parseDouble(columns.get("PriceDCMinute"));

                        assertEquals(expectedPricePerACkwH, location.getPricePerACkwHByName(name), 0.01);
                        assertEquals(expectedPricePerDCkwH, location.getPricePerDCkwHByName(name), 0.01);
                        assertEquals(expectedPriceACMinute, location.getPriceACMinuteByName(name), 0.01);
                        assertEquals(expectedPriceDCMinute, location.getPriceDCMinuteByName(name), 0.01);

                }
        }


        @Given("following chargers exists:")
        public void followingChargersExists(io.cucumber.datatable.DataTable chargerTable) {
                List<Map<String, String>> rows = chargerTable.asMaps();
                for (Map<String, String> columns : rows) {
                        Charger.createCharger(columns.get("ID"),Type.valueOf(columns.get("Type")),Status.valueOf(columns.get("Status")));
                }
        }


        @Given("location {string} has following chargers:")
        public void locationHasFollowingChargers(String name, io.cucumber.datatable.DataTable addedChargersTable) {
                location.addLocation(name, "Dresdnersstrasse 42, Wien",0.25, 0.25,0.25,0.25);
                List<Map<String, String>> rows = addedChargersTable.asMaps();
                for (Map<String, String> columns : rows) {
                        location.addChargerToLocation(name, columns.get("ID"));
                }
        }


        @When("owner adds the following chargers to {string}:")
        public void ownerAddsTheFollowingChargers(String name, io.cucumber.datatable.DataTable addChargersTable) {
                List<Map<String, String>> rows = addChargersTable.asMaps();
                for (Map<String, String> columns : rows) {
                    location.addChargerToLocation(name, columns.get("ID"));
                }
        }


        @Then("location {string} has {int} chargers")
        public void locationHasChargers(String name, int expectedChargerCount) {
                List<Charger> chargers = location.getChargersByLocation(name);
                assertEquals(expectedChargerCount, chargers.size());
        }


        @And("charger with ID {string} is of type {string} and status {string}")
        public void chargerWithIDIsOfTypeAndStatus(String chargerId, String expectedType, String expectedStatus) {
                Charger charger = Charger.getChargerById(chargerId);
                assertEquals(Type.valueOf(expectedType), charger.getType());
                assertEquals(Status.valueOf(expectedStatus), charger.getStatus());
        }

        @When("owner removes charger with ID {string} from location {string}")
        public void ownerRemoveChargerFromLocation(String chargerId, String locationName) {
                location.removeChargerFromLocation(locationName, chargerId);
        }

        @Then("charger with ID {string} should not exist at location {string}")
        public void chargerShouldNotExistAtLocation(String chargerId, String locationName) {
                List<Charger> chargers = location.getChargersByLocation(locationName);

                boolean chargerExists = chargers.stream()
                        .anyMatch(charger -> charger.getChargerId().equals(chargerId));
                assertFalse(chargerExists);
        }


        @When("owner sets status of charger with ID {string} at location {string} to {string}")
        public void ownerSetsStatusOfChargerWithIDAtLocationTo(String chargerId, String name, String newStatus) {
                location.setChargerStatusOfLocation(name, chargerId, Status.valueOf(newStatus));
        }



        //////////////////////////  Error Cases ////////////////////////////

        @When("the owner deletes the location with name {string}")
        public void theOwnerDeletesTheLocationWithName(String name) {
                try {
                        location.deleteLocation(name);
                } catch (IllegalArgumentException e) {
                        errorMessage = e.getMessage();
                }
        }

        @When("the owner adds the charger with ID {string} to the location {string}")
        public void theOwnerAddsTheChargerWithIDToTheLocation(String chargerId, String locationName) {
                try {
                        location.addChargerToLocation(locationName, chargerId);
                } catch (IllegalArgumentException e) {
                        errorMessage = e.getMessage();
                }
        }

        @Then("an error message should be displayed saying {string}")
        public void anErrorMessageShouldBeDisplayedSaying(String expectedErrorMessage) {
                assertEquals(expectedErrorMessage, errorMessage);
        }


        @When("the owner tries to change the status of the charger with ID {string} at location {string} to {string}")
        public void theOwnerTriesToChangeTheStatusOfTheChargerWithIDAtLocationTo(String chargerId, String locationName, String newStatus) {
                try {
                        location.setChargerStatusOfLocation(locationName, chargerId, Status.valueOf(newStatus));
                } catch (IllegalArgumentException e) {
                        errorMessage = e.getMessage();
                }
        }


}
