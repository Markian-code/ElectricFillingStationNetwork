package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationMonitoringSteps {

    private ECS ecs = new ECS();
    private LocationMonitoring locationMonitoring = new LocationMonitoring(ecs);
    private String displayOutput;



    @Given("the following locations with chargers exist:")
    public void the_following_locations_with_chargers_exist(List<Map<String, String>> locationData) {
        for (Map<String, String> data : locationData) {
            List<Charger> chargers = new ArrayList<>();
            if (data.get("Charger1") != null && !data.get("Charger1").isEmpty()) {
                chargers.add(new Charger(data.get("Charger1"), ChargerType.valueOf(data.get("Type-1")), ChargerStatus.valueOf(data.get("Status-1"))));
            }
            if (data.get("Charger2") != null && !data.get("Charger2").isEmpty()) {
                chargers.add(new Charger(data.get("Charger2"), ChargerType.valueOf(data.get("Type-2")), ChargerStatus.valueOf(data.get("Status-2"))));
            }
            if (data.get("Charger3") != null && !data.get("Charger3").isEmpty()) {
                chargers.add(new Charger(data.get("Charger3"), ChargerType.valueOf(data.get("Type-3")), ChargerStatus.valueOf(data.get("Status-3"))));
            }

            ecs.addLocationWithChargers(
                    data.get("name"),
                    data.get("address"),
                    Double.parseDouble(data.get("PricePerACkwH")),
                    Double.parseDouble(data.get("PricePerDCkwH")),
                    Double.parseDouble(data.get("PriceACMinute")),
                    Double.parseDouble(data.get("PriceDCMinute")),
                    chargers
            );
        }
    }

    @When("I display all locations with chargers")
    public void i_display_all_locations_with_chargers() {
        displayOutput = captureConsoleOutput(() -> locationMonitoring.displayAllLocationsWithChargers());
    }

    @Then("I should see the following locations with their chargers:")
    public void i_should_see_the_following_locations_with_their_chargers(List<Map<String, String>> expectedChargers) {
        for (Map<String, String> chargerData : expectedChargers) {
            String chargerId = chargerData.get("Charger ID");
            String locationName = chargerData.get("Location");
            String expectedOutput = "Charger ID: " + chargerId + ", Type: " + chargerData.get("Type")
                    + ", Status: " + chargerData.get("Status");
            assertTrue(displayOutput.contains(expectedOutput));
        }
    }

    private String captureConsoleOutput(Runnable runnable) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));
        runnable.run();
        System.setOut(originalOut);
        return baos.toString();
    }

    @When("I display available chargers at location {string}")
    public void iDisplayAllLocationsWithAvailableChargers(String location) {
        displayOutput = captureConsoleOutput(() -> locationMonitoring.displayAvailableChargers(location));
    }

}
