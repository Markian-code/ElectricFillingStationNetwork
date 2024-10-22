package org.group3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Location {
    private final Map<String, LocationData> locations = new HashMap<>();

    public void addLocation(String name, String address, double pricePerACkwH, double pricePerDCkwH, double priceACMinute, double priceDCMinute) {
        locations.put(name, new LocationData(address, pricePerACkwH, pricePerDCkwH, priceACMinute, priceDCMinute));
    }

    public int getLocationCount() {
        return locations.size();
    }

    public Optional<LocationData> getLocationByName(String name) {
        return Optional.ofNullable(locations.get(name));
    }

    public String getLocationAddressByName(String name) {
        return getLocationByName(name).map(LocationData::getAddress)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + name + " does not exist."));
    }

    public boolean locationExists(String name) {
        return locations.containsKey(name);
    }

    public void deleteLocation(String name) {
        if (!locations.containsKey(name)) {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
        locations.remove(name);
    }


    private double getPrice(String name, PriceType type) {
        LocationData location = getLocationByName(name).orElseThrow(() -> new IllegalArgumentException("Location with name " + name + " does not exist."));
        return switch (type) {
            case AC_KWH -> location.getPricePerACkwH();
            case DC_KWH -> location.getPricePerDCkwH();
            case AC_MINUTE -> location.getPriceACMinute();
            case DC_MINUTE -> location.getPriceDCMinute();
        };
    }

    public double getPricePerACkwHByName(String name) {
        return getPrice(name, PriceType.AC_KWH);
    }

    public double getPricePerDCkwHByName(String name) {
        return getPrice(name, PriceType.DC_KWH);
    }

    public double getPriceACMinuteByName(String name) {
        return getPrice(name, PriceType.AC_MINUTE);
    }

    public double getPriceDCMinuteByName(String name) {
        return getPrice(name, PriceType.DC_MINUTE);
    }


    private void updatePrice(String name, double newPrice, PriceType type) {
        LocationData location = getLocationByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + name + " does not exist."));
        switch (type) {
            case AC_KWH -> location.setPricePerACkwH(newPrice);
            case DC_KWH -> location.setPricePerDCkwH(newPrice);
            case AC_MINUTE -> location.setPriceACMinute(newPrice);
            case DC_MINUTE -> location.setPriceDCMinute(newPrice);
        }
    }

    public void updatePricePerACkwH(String name, double newPricePerACkwH) {
        updatePrice(name, newPricePerACkwH, PriceType.AC_KWH);
    }

    public void updatePricePerDCkwH(String name, double newPricePerDCkwH) {
        updatePrice(name, newPricePerDCkwH, PriceType.DC_KWH);
    }

    public void updatePriceACMinute(String name, double newPriceACMinute) {
        updatePrice(name, newPriceACMinute, PriceType.AC_MINUTE);
    }

    public void updatePriceDCMinute(String name, double newPriceDCMinute) {
        updatePrice(name, newPriceDCMinute, PriceType.DC_MINUTE);
    }

    public void addChargerToLocation(String locationName, String chargerId) {
        LocationData location = getLocationByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));
        Charger charger = Charger.getChargerById(chargerId);
        if (charger != null) {
            location.addCharger(charger);
        } else {
            throw new IllegalArgumentException("Charger with ID " + chargerId + " does not exist.");
        }
    }

    public List<Charger> getChargersByLocation(String locationName) {
        return getLocationByName(locationName)
                .map(LocationData::getChargers)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));
    }

    public void setChargerStatusOfLocation(String locationName, String chargerId, Status newStatus) {
        LocationData location = getLocationByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));
        Charger charger = location.getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID " + chargerId + " does not exist at this location."));
        charger.setStatus(newStatus);
    }

    public void removeChargerFromLocation(String locationName, String chargerId) {
        LocationData location = getLocationByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));
        if (!location.removeCharger(chargerId)) {
            throw new IllegalArgumentException("Charger with ID " + chargerId + " does not exist at this location.");
        }
    }

    public Map<String, LocationData> getAllLocations() {
        return locations;
    }


    private enum PriceType {
        AC_KWH, DC_KWH, AC_MINUTE, DC_MINUTE
    }


}
