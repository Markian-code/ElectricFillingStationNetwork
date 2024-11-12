Feature: View User Invoice

  As user
  I want to view my invoices
  so i have a overview on my top-ups and billed charging sessions

  Scenario: View detailed invoice

    Given user "Michael" with email "michael@example.com" and the following top-ups:
      | amount | timestamp           |
      | 50.0   | 2024-10-01 09:00    |
      | 30.0   | 2024-10-05 12:00    |
      | 40.0   | 2024-10-10 08:30    |
    And has the following charging sessions:
      | location              | chargerId | type | powerConsumed | startTime         | endTime           |
      | Wien Hauptbahnhof     | CHARGER-1 | AC   | 10.0          | 2024-10-01 10:00  | 2024-10-01 11:00  |
      | Graz Operngasse       | CHARGER-4 | DC   | 15.0          | 2024-10-06 14:00  | 2024-10-06 15:30  |
      | Salzburg Hauptbahnhof | CHARGER-5 | AC   | 20.0          | 2024-10-08 09:00  | 2024-10-08 10:15  |
    When user views the detailed invoice
    Then the invoice shows:
      | Timestamp        | Ledger Type      | Location              | ChargerID | Type   | Duration (min) | Power (kWh) | Cost (€) | Balance (€) |
      | 2024-10-01 09:00 | Top-Up           |                       |           |        |                |             | 50,00    | 50,00       |
      | 2024-10-01 10:00 | Charging Session | Wien Hauptbahnhof     | CHARGER-1 | AC     | 60             | 10.0        | -4,00    | 46,00       |
      | 2024-10-05 12:00 | Top-Up           |                       |           |        |                |             | 30,00    | 76,00       |
      | 2024-10-06 14:00 | Charging Session | Graz Operngasse       | CHARGER-4 | DC     | 90             | 15.0        | -16,50   | 59,50       |
      | 2024-10-08 09:00 | Charging Session | Salzburg Hauptbahnhof | CHARGER-5 | AC     | 75             | 20.0        | -15,25   | 44,25       |
      | 2024-10-10 08:30 | Top-Up           |                       |           |        |                |             | 40,00    | 84,25       |
