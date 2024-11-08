package org.group3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Location {
    private final String name;
    private final String address;
    private double pricePerACkwH;
    private double pricePerDCkwH;
    private double priceACMinute;
    private double priceDCMinute;
    private List<Charger> chargers = new ArrayList<>();  // Liste der Ladegeräte an dieser Location

    public Location(String name, String address, double pricePerACkwH, double pricePerDCkwH, double priceACMinute, double priceDCMinute) {
        this.name = name;
        this.address = address;
        this.pricePerACkwH = pricePerACkwH;
        this.pricePerDCkwH = pricePerDCkwH;
        this.priceACMinute = priceACMinute;
        this.priceDCMinute = priceDCMinute;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setPricePerACkwH(double pricePerACkwH) {
        this.pricePerACkwH = pricePerACkwH;
    }

    public double getPricePerACkwH() {
        return pricePerACkwH;
    }

    public void setPricePerDCkwH(double pricePerDCkwH) {
        this.pricePerDCkwH = pricePerDCkwH;
    }

    public double getPricePerDCkwH() {
        return pricePerDCkwH;
    }

    public void setPriceACMinute(double priceACMinute) {
        this.priceACMinute = priceACMinute;
    }

    public double getPriceACMinute() {
        return priceACMinute;
    }

    public void setPriceDCMinute(double priceDCMinute) {
        this.priceDCMinute = priceDCMinute;
    }

    public double getPriceDCMinute() {
        return priceDCMinute;
    }

    public void addCharger(Charger charger) {
        Optional<Charger> existingCharger = getChargerById(charger.getChargerId());
        if (existingCharger.isEmpty()) {
            chargers.add(charger);
        } else {
            throw new IllegalArgumentException("Charger with ID '" + charger.getChargerId() + "' already exists in this location.");
        }
    }

    // Charger nach ID finden
    public Optional<Charger> getChargerById(String chargerId) {
        return chargers.stream().filter(c -> c.getChargerId().equals(chargerId)).findFirst();
    }

    // Charger entfernen
    public boolean removeCharger(String chargerId) {
        return chargers.removeIf(c -> c.getChargerId().equals(chargerId));
    }

    public List<Charger> getChargers() {
        return new ArrayList<>(chargers);
    }


    public Charger getChargersAtLocation(String chargerId) {
        return getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID '" + chargerId + "' not found at this location"));
    }

    public void updateCharger(String chargerId, ChargerType newType, ChargerStatus newStatus) {
        Charger charger = getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID " + chargerId + " does not exist at this location."));

        charger.setStatus(newStatus);
        if (newType != null) {
            charger.setType(newType);
        }
    }

}
