package org.group3;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Invoice {

    private final String username;  // Benutzername des Nutzers

    public Invoice(String username) {
        this.username = username;
    }

    // Abrufen aller Charging Sessions für diesen Benutzer, sortiert nach Startzeit
    public List<ChargingSession> getUserSessions() {
        return ChargingSession.getAllSessions().stream()
                .filter(session -> session.getUsername().equals(username))
                .sorted(Comparator.comparing(ChargingSession::getStartTime))  // Sortiere nach Startzeit
                .collect(Collectors.toList());
    }

    // Berechne die Gesamtkosten aller Sessions des Nutzers
    public double calculateTotalCost() {
        return getUserSessions().stream()
                .mapToDouble(ChargingSession::getTotalCost)  // Summe der Kosten jeder Session
                .sum();
    }

    // Ausgabe aller Sessions mit ihren Details, inklusive Charger-ID und Typ
    public void printUserInvoice() {
        List<ChargingSession> sessions = getUserSessions();
        if (sessions.isEmpty()) {
            System.out.println("No charging sessions found for user: " + username);
            return;
        }

        System.out.println("Invoice for user: " + username);
        System.out.println("============================================");

        for (ChargingSession session : sessions) {
            System.out.println("Session at location: " + session.getLocationName());
            System.out.println("  Charger ID: " + session.getCharger().getChargerId());
            System.out.println("  Charger Type: " + session.getChargerType());
            System.out.println("  Start Time: " + session.getStartTime());
            System.out.println("  End Time: " + session.getEndTime());
            System.out.println("  Duration: " + session.getDuration().toMinutes() + " minutes");
            System.out.println("  Power Consumed: " + session.getPowerConsumed() + " kWh");
            System.out.println("  Total Cost: €" + session.getTotalCost());
            System.out.println("--------------------------------------------");
        }

        // Gesamtkosten aller Sessions
        double totalCost = calculateTotalCost();
        System.out.println("Total amount due: €" + totalCost);
        System.out.println("============================================");
    }

}
