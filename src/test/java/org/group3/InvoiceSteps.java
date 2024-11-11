package org.group3;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceSteps {

    private ECS ecs;
    private List<Invoice.LedgerLine> ledgerLines;
    private String currentUserEmail;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Before
    public void setUp() {
        ecs = new ECS();
    }

    @Given("user {string} with email {string} and the following top-ups exist:")
    public void userWithTopUpsExists(String name, String email, List<Map<String, String>> topUps) {
        UserAccount userAccount = new UserAccount(name, email);
        currentUserEmail = email;
        ecs.addUser(userAccount);

        for (Map<String, String> topUp : topUps) {
            double amount = Double.parseDouble(topUp.get("amount"));
            LocalDateTime timestamp = LocalDateTime.parse(topUp.get("timestamp"), formatter);
            ecs.addFundsToUser(email, amount, timestamp);
        }
    }

    @And("the user has the following charging sessions:")
    public void userHasChargingSessions(List<Map<String, String>> sessions) {
        for (Map<String, String> session : sessions) {
            String locationName = session.get("location");
            String chargerId = session.get("chargerId");
            ChargerType type = ChargerType.valueOf(session.get("type"));
            double powerConsumed = Double.parseDouble(session.get("powerConsumed"));
            LocalDateTime startTime = LocalDateTime.parse(session.get("startTime"), formatter);
            LocalDateTime endTime = LocalDateTime.parse(session.get("endTime"), formatter);

            ecs.createChargingSession(currentUserEmail, locationName, chargerId, startTime, powerConsumed);
            ecs.endChargingSession(currentUserEmail, endTime);
        }
    }

    @When("the user views the combined ledger")
    public void the_user_views_the_combined_ledger() {
        Invoice invoice = new Invoice(currentUserEmail, ecs);
        ledgerLines = invoice.createDetailedUserInvoice();
    }

    @Then("the ledger should display:")
    public void the_ledger_should_display(List<Map<String, String>> expectedLedger) {
        assertEquals(expectedLedger.size(), ledgerLines.size(), "Anzahl der Ledger-Einträge stimmt nicht überein.");

        for (int i = 0; i < expectedLedger.size(); i++) {
            Map<String, String> expectedRow = expectedLedger.get(i);
            Invoice.LedgerLine actualLine = ledgerLines.get(i);

            assertEqualsOrEmpty(expectedRow.get("Timestamp"), actualLine.getTimestamp(), "Timestamp");
            assertEqualsOrEmpty(expectedRow.get("Ledger Type"), actualLine.getLedgerType(), "Ledger Type");
            assertEqualsOrEmpty(expectedRow.get("Location"), actualLine.getLocation(), "Location");
            assertEqualsOrEmpty(expectedRow.get("ChargerID"), actualLine.getChargerId(), "ChargerID");
            assertEqualsOrEmpty(expectedRow.get("Type"), actualLine.getType(), "Type");
            assertEqualsOrEmpty(expectedRow.get("Duration (min)"), actualLine.getDuration(), "Duration (min)");
            assertEqualsOrEmpty(expectedRow.get("Power (kWh)"), actualLine.getPower(), "Power (kWh)");
            assertEqualsOrEmpty(expectedRow.get("Cost (€)"), actualLine.getCost(), "Cost (€)");
            assertEqualsOrEmpty(expectedRow.get("Balance (€)"), actualLine.getBalance(), "Balance (€)");
        }
    }

    private void assertEqualsOrEmpty(String expected, String actual, String fieldName) {
        String expectedValue = (expected == null || expected.equals("[empty]")) ? "" : expected.trim();
        String actualValue = (actual == null || actual.equals("[empty]")) ? "" : actual.trim();
        assertEquals(expectedValue, actualValue, fieldName + " stimmt nicht überein.");
    }
}
