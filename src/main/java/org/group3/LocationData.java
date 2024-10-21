package org.group3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class LocationData {
    private String address;
    private double pricePerACkwH;
    private double pricePerDCkwH;
    private double priceACMinute;
    private double priceDCMinute;
    private List<Charger> chargers = new ArrayList<>();

    public LocationData(String address, double pricePerACkwH, double pricePerDCkwH, double priceACMinute, double priceDCMinute) {
        this.address = address;
        this.pricePerACkwH = pricePerACkwH;
        this.pricePerDCkwH = pricePerDCkwH;
        this.priceACMinute = priceACMinute;
        this.priceDCMinute = priceDCMinute;
    }

    // Getters and Setters

    public String getAddress() {
        return address;
    }

    public double getPricePerACkwH() {
        return pricePerACkwH;
    }

    public void setPricePerACkwH(double pricePerACkwH) {
        this.pricePerACkwH = pricePerACkwH;
    }

    public double getPricePerDCkwH() {
        return pricePerDCkwH;
    }

    public void setPricePerDCkwH(double pricePerDCkwH) {
        this.pricePerDCkwH = pricePerDCkwH;
    }

    public double getPriceACMinute() {
        return priceACMinute;
    }

    public void setPriceACMinute(double priceACMinute) {
        this.priceACMinute = priceACMinute;
    }

    public double getPriceDCMinute() {
        return priceDCMinute;
    }

    public void setPriceDCMinute(double priceDCMinute) {
        this.priceDCMinute = priceDCMinute;
    }

    public List<Charger> getChargers() {
        return chargers;
    }

    public void addCharger(Charger charger) {
        this.chargers.add(charger);
    }

    public Optional<Charger> getChargerById(String chargerId) {
        return chargers.stream().filter(charger -> charger.getChargerId().equals(chargerId)).findFirst();
    }

    public boolean removeCharger(String chargerId) {
        return chargers.removeIf(charger -> charger.getChargerId().equals(chargerId));
    }
}
