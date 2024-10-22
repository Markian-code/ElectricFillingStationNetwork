package org.group3;

import io.cucumber.java.en.Then;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceSteps {

    private Invoice userInvoice;

    @Then("user {string} requests an invoice")
    public void userRequestsInvoice(String username) {
        userInvoice = new Invoice(username);
        userInvoice.printUserInvoice();
    }

    @Then("the total invoice amount for user {string} should be €{double}")
    public void totalInvoiceAmountShouldBe(String username, double expectedAmount) {
        userInvoice = new Invoice(username);
        double totalCost = userInvoice.calculateTotalCost();

        assertEquals(expectedAmount, totalCost, 0.01);
    }

    @Then("the invoice for user {string} should list the following sessions in order:")
    public void invoiceShouldListSessionsInOrder(String username, io.cucumber.datatable.DataTable expectedSessionsTable) {
        userInvoice = new Invoice(username);
        List<ChargingSession> actualSessions = userInvoice.getUserSessions();

        List<Map<String, String>> expectedSessions = expectedSessionsTable.asMaps();
        assertEquals(expectedSessions.size(), actualSessions.size(), "Number of sessions should match");

        for (int i = 0; i < expectedSessions.size(); i++) {
            Map<String, String> expected = expectedSessions.get(i);
            ChargingSession actual = actualSessions.get(i);

            assertEquals(expected.get("location"), actual.getLocationName(), "Location should match");
            assertEquals(expected.get("chargerId"), actual.getCharger().getChargerId(), "Charger ID should match");
            assertEquals(expected.get("chargerType"), actual.getChargerType().name(), "Charger Type should match");
            assertEquals(expected.get("startTime"), actual.getStartTime().toString(), "Start Time should match");

        }
    }

}
