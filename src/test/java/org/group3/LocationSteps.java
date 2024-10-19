package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationSteps {

        Location location = new Location();

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


}
