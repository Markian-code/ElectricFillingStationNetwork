package org.group3;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.*;

public class ChargingSession {
    private static int sessionId = 0;
    private Date date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UserAccounts.User user;
    private Location location;
    private Charger charger;
    private Type type;
    private double chargedKWh;
    private double price = 0;
    private final Map<String, Object[]> sessions = new HashMap<>();

    public ChargingSession(LocalDateTime startTime, LocalDateTime endTime, UserAccounts.User user, Location location, Charger charger, double chargedKWh) {
        sessionId = sessionId++;
        date = new Date();
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.location = location;
        this.charger = charger;
        this.type = charger.getType();
        this.chargedKWh = chargedKWh;
    }


    public void createChargingSession(String name, int chargerId, UserAccounts.User user, Location location, Charger charger, double chargedKWh) {
        ChargingSession chargingSession = new ChargingSession(startTime, duration, user, location, charger, chargedKWh);
        //locations.put(name, new Object[] {address, pricePerACkwH, pricePerDCkwH, priceACMinute, priceDCMinute, new ArrayList<Charger>()});
    }

    // Getters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getChargedKWh() {
        return chargedKWh;
    }

    public long getChargingDurationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    public double getPrice() {
        return price;
    }

    public double calculatePrice(String name) {
        price = chargedKWh * getChargingDurationInMinutes();
        if (type == Type.AC) {
            price *= location.getPriceACMinuteByName(name) * location.getPricePerACkwHByName(name);
        } else {
            price *= location.getPriceDCMinuteByName(name) * location.getPricePerDCkwHByName(name);
        }
        return price;
    }

    public int getSessionCount() {
        return sessions.size();
    }
}