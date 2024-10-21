package org.group3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;


public class InvoiceSteps {
    public InvoiceSteps() {
        @Given("there are invoices") -> {

        };

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

        @When("^the user wants to display the invoices$", () -> {
        });

        @Then("^a list with all invoices should be displayed$", () -> {
        });

        @When("^the user wants to see his balance history$", () -> {
        });

        @Then("^a list with all top-ups and their dates should be displayed$", () -> {
        });

        @Given("^there are no invoices$", () -> {
        });

        @Then("^an error should be displayed$", () -> {
        });

        @Given("^no funds have been added$", () -> {
        });

        @When("^the user wants to display the balance history$", () -> {
        });
    }
}
