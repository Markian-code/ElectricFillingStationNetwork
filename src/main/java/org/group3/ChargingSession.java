package org.group3;

import java.time.Duration;
import java.time.LocalDateTime;

public class ChargingSession {
    private final String userEmail;
    private final Location location;
    private final Charger charger;
    private final double powerConsumed;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private double totalCost;
    private double balanceAfterCharging;
    private SessionState sessionState;
    private UserAccount userAccount;

    public ChargingSession(UserAccount userAccount, Location location, Charger charger, double powerConsumed, LocalDateTime startTime) {
        this.userEmail = userAccount.getEmail();
        this.userAccount = userAccount;
        this.location = location;
        this.charger = charger;
        this.powerConsumed = powerConsumed;
        this.startTime = startTime;
        this.sessionState = SessionState.ACTIVE;  // Standardmäßig ist die Sitzung aktiv
    }

    // Methode zum Beenden der Sitzung
    public void endSession(LocalDateTime endTime) {
        if (sessionState == SessionState.COMPLETED) {
            throw new IllegalStateException("Session has already been completed.");
        }
        this.endTime = endTime;
        this.sessionState = SessionState.COMPLETED;

        calculateTotalCost(); // Gesamtkosten berechnen (bestehende Methode)

        // Hinzufügen eines Ledger-Eintrags statt Balance-Abzug
        userAccount.addLedgerEntry(LedgerType.CHARGING_SESSION, totalCost, startTime);
    }

    public Duration getDuration() {
        if (sessionState != SessionState.COMPLETED) {
            throw new IllegalStateException("Charging session is still active.");
        }
        return Duration.between(startTime, endTime);
    }

    private void calculateTotalCost() {
        if (sessionState != SessionState.COMPLETED) {
            throw new IllegalStateException("Cannot calculate total cost. Charging session is still active.");
        }
        long minutes = getDuration().toMinutes();

        if (charger.getType() == ChargerType.AC) {
            totalCost = (powerConsumed * location.getPricePerACkwH()) + (minutes * location.getPriceACMinute());
        } else {
            totalCost = (powerConsumed * location.getPricePerDCkwH()) + (minutes * location.getPriceDCMinute());
        }
    }

    public double getTotalCost() {
        if (sessionState != SessionState.COMPLETED) {
            throw new IllegalStateException("Charging session is still active. Cannot calculate total cost.");
        }
        return totalCost;
    }

    public double getBalanceAfterCharging() {
        if (sessionState != SessionState.COMPLETED) {
            throw new IllegalStateException("Charging session is still active.");
        }
        return balanceAfterCharging;
    }


    public SessionState getSessionState() {
        return sessionState;
    }

    public boolean isSessionEnded() {
        return sessionState == SessionState.COMPLETED;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    public Charger getCharger() {
        return charger;
    }

    public double getPowerConsumed() {
        return powerConsumed;
    }

}