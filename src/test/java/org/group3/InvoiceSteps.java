package org.group3;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceSteps {

    private ECS ecs;
    private Invoice invoice;
    private UserAccount userAccount;
    private double totalCost;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Before
    public void setUp() {
        ecs = new ECS();
    }

    @Given("a user {string} with email {string} and balance {double} has 5 charging sessions:")
    public void a_user_with_email_has_charging_sessions(String username, String email, double balance, List<Map<String, String>> sessionData) {

        userAccount = new UserAccount(username, email);
        userAccount.addFunds(balance);
        ecs.addUser(userAccount);

        for (Map<String, String> sessionRow : sessionData) {
            String locationName = sessionRow.get("Location");
            String chargerId = sessionRow.get("ChargerID");
            LocalDateTime startTime = LocalDateTime.parse(sessionRow.get("Start Time"));
            LocalDateTime endTime = LocalDateTime.parse(sessionRow.get("End Time"));
            double powerConsumed = Double.parseDouble(sessionRow.get("Power Consumed"));

            ecs.createChargingSession(email, locationName, chargerId, startTime, powerConsumed);
            ecs.endChargingSession(email, endTime);
        }
    }

    @When("the user views the invoice")
    public void the_user_views_the_invoice() {

        invoice = new Invoice(userAccount.getEmail(), ecs);
    }

    @Then("the invoice should display:")
    public void the_invoice_should_display(List<Map<String, String>> expectedInvoiceData) {
        List<ChargingSession> sessions = invoice.getUserSessions();
        assertEquals(expectedInvoiceData.size(), sessions.size());

        for (int i = 0; i < sessions.size(); i++) {
            ChargingSession session = sessions.get(i);
            Map<String, String> expectedData = expectedInvoiceData.get(i);

            assertEquals(expectedData.get("Location"), session.getLocation().getName());
            assertEquals(expectedData.get("ChargerID"), session.getCharger().getChargerId());

            // Zeitformatierung prüfen
            assertEquals(expectedData.get("Start Time"), session.getStartTime().format(formatter));
            assertEquals(expectedData.get("End Time"), session.getEndTime().format(formatter));

            assertEquals(Long.parseLong(expectedData.get("Duration (minutes)")), session.getDuration().toMinutes());
            assertEquals(Double.parseDouble(expectedData.get("Power Consumed")), session.getPowerConsumed(), 0.01);
            assertEquals(Double.parseDouble(expectedData.get("Total Cost (€)")), session.getTotalCost(), 0.01);
            assertEquals(Double.parseDouble(expectedData.get("Balance after charging (€)")), session.getBalanceAfterCharging(), 0.01);
        }
    }

    @Then("the total amount due should be {string}")
    public void the_total_amount_due_should_be(String expectedTotal) {
        double actualTotal = invoice.calculateTotalCost();
        assertEquals(Double.parseDouble(expectedTotal), actualTotal, 0.01);
    }

}
