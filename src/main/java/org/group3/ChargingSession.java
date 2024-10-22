package org.group3;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChargingSession {

    private final String username;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private final Location location;
    private final Charger charger;
    private final Type chargerType;
    private final double powerConsumed;
    private double totalCost;
    private final String locationName;

    // Liste zum Speichern aller ChargingSessions
    private static final List<ChargingSession> allSessions = new ArrayList<>();

    // Konstruktor für eine neue ChargingSession
    public ChargingSession(String username, LocalDateTime startTime, Location location, String locationName, Charger charger, double powerConsumed) {
        this.username = username;
        this.startTime = startTime;
        this.location = location;
        this.locationName = locationName;  // Füge den Location-Namen hinzu
        this.charger = charger;
        this.chargerType = charger.getType();  // AC oder DC
        this.powerConsumed = powerConsumed;
        allSessions.add(this);  // Füge die neue Session zur Liste hinzu
    }

    // Beende die Session und berechne die Gesamtkosten
    public void endSession(LocalDateTime endTime) {
        this.endTime = endTime;
        calculateTotalCost();
    }

    // Berechne die Gesamtdauer der Session
    public Duration getDuration() {
        if (endTime != null) {
            return Duration.between(startTime, endTime);
        } else {
            throw new IllegalStateException("Charging session has not ended yet.");
        }
    }

    // Berechne die Gesamtkosten der Session basierend auf kWh und Minuten
    private void calculateTotalCost() {
        Duration duration = getDuration();
        long minutes = duration.toMinutes();

        // Verwende den Location-Namen, um die Location-Daten zu holen
        LocationData locationData = location.getLocationByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location does not exist."));

        // Kostenberechnung basierend auf AC/DC
        if (chargerType == Type.AC) {
            double kWhCost = powerConsumed * locationData.getPricePerACkwH();  // Kosten für kWh (AC)
            double minuteCost = minutes * locationData.getPriceACMinute();     // Minutenkosten (AC)
            this.totalCost = kWhCost + minuteCost;
        } else if (chargerType == Type.DC) {
            double kWhCost = powerConsumed * locationData.getPricePerDCkwH();  // Kosten für kWh (DC)
            double minuteCost = minutes * locationData.getPriceDCMinute();     // Minutenkosten (DC)
            this.totalCost = kWhCost + minuteCost;
        }
    }

    // Getter-Methoden für alle Eigenschaften
    public String getUsername() {
        return username;
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

    public Type getChargerType() {
        return chargerType;
    }

    public double getPowerConsumed() {
        return powerConsumed;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getLocationName() {
        return locationName;
    }

    // Statische Methode, um alle Sessions abzurufen
    public static List<ChargingSession> getAllSessions() {
        return allSessions;
    }
}
