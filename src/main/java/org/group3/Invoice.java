package org.group3;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Invoice {

    private final String userEmail;
    private final ECS ecs;


    public Invoice(String userEmail, ECS ecs) {
        this.userEmail = userEmail;
        this.ecs = ecs;
    }

    // Abrufen aller Charging Sessions für diesen Benutzer, sortiert nach Startzeit
    public List<ChargingSession> getUserSessions() {
        List<ChargingSession> userSessions = ecs.getChargingSessionsByUser(userEmail).stream()
                .sorted(Comparator.comparing(ChargingSession::getStartTime))
                .collect(Collectors.toList());

        if (userSessions.isEmpty()) {
            throw new IllegalStateException("No charging sessions found for user with email: " + userEmail);
        }

        return userSessions;
    }

    // Berechne die Gesamtkosten aller Sessions des Nutzers
    public double calculateTotalCost() {
        return getUserSessions().stream()
                .mapToDouble(ChargingSession::getTotalCost)  // Summe der Kosten jeder Session
                .sum();
    }

    // Ausgabe aller Sessions mit ihren Details, inklusive Charger-ID und Typ
    public void printUserInvoice() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        List<ChargingSession> sessions = getUserSessions();
        if (sessions.isEmpty()) {
            System.out.println("No charging sessions found for user with email: " + userEmail);
            return;
        }

        System.out.println("Invoice for user with email: " + userEmail);
        System.out.println("============================================");

        for (ChargingSession session : sessions) {

            Location location = session.getLocation();
            Charger charger = session.getCharger();

            System.out.println("Session at location: " + location.getName());
            System.out.println("  Charger ID: " + charger.getChargerId());
            System.out.println("  Charger Type: " + charger.getType());  // Typ des Chargers (AC/DC)
            System.out.println("  Start Time: " + session.getStartTime().format(formatter));
            System.out.println("  End Time: " + session.getEndTime().format(formatter));
            System.out.println("  Duration: " + session.getDuration().toMinutes() + " minutes");
            System.out.println("  Power Consumed: " + session.getPowerConsumed() + " kWh");
            System.out.println("  Total Cost: €" + session.getTotalCost());
            System.out.println("  Balance after charging: €" + session.getBalanceAfterCharging());
            System.out.println("--------------------------------------------");
        }

        // Gesamtkosten aller Sessions
        double totalCost = calculateTotalCost();
        System.out.println("Total amount due: €" + totalCost);
        System.out.println("============================================");
    }

}

