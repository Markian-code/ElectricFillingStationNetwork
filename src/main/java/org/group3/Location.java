package org.group3;

import java.util.HashMap;
import java.util.Map;


public class Location {
    private Map<String, Object[]> locations = new HashMap<>();

    public void addLocation(String name, String address,double pricePerACkwH, double pricePerDCkwH, double priceACMinute, double priceDCMinute) {
        locations.put(name, new Object[] {address, pricePerACkwH, pricePerDCkwH, priceACMinute, priceDCMinute});
    }

    public int getLocationCount() {
        return locations.size();
    }

    public String getLocationAddressByName(String name) {
        Object[] locationData = locations.get(name);
        return locationData != null ? locationData[0].toString() : null;
    }

    public boolean locationExists(String name) {
        return locations.containsKey(name);
    }

    public void deleteLocation(String name) {
        if (locations.containsKey(name)) {
            locations.remove(name);
        } else {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
    }


    public double getPricePerACkwHByName(String name) {
        Object[] locationData = locations.get(name);
        if (locationData == null) {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
        return (double) locationData[1];
    }


    public double getPricePerDCkwHByName(String name) {
        Object[] locationData = locations.get(name);
        if (locationData == null) {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
        return (double) locationData[2];
    }


    public double getPriceACMinuteByName(String name) {
        Object[] locationData = locations.get(name);
        if (locationData == null) {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
        return (double) locationData[3];
    }


    public double getPriceDCMinuteByName(String name) {
        Object[] locationData = locations.get(name);
        if (locationData == null) {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
        return (double) locationData[4];
    }


    public void updatePricePerACkwH(String name, double newPricePerACkwH) {
        Object[] locationData = locations.get(name);
        if (locationData != null) {
            locationData[1] = newPricePerACkwH;
        } else {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
    }

    public void updatePricePerDCkwH(String name, double newPricePerDCkwH) {
        Object[] locationData = locations.get(name);
        if (locationData != null) {
            locationData[2] = newPricePerDCkwH;
        } else {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
    }


    public void updatePriceACMinute(String name, double newPriceACMinute) {
        Object[] locationData = locations.get(name);
        if (locationData != null) {
            locationData[3] = newPriceACMinute;
        } else {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
    }

    public void updatePriceDCMinute(String name, double newPriceDCMinute) {
        Object[] locationData = locations.get(name);
        if (locationData != null) {
            locationData[4] = newPriceDCMinute;
        } else {
            throw new IllegalArgumentException("Location with name " + name + " does not exist.");
        }
    }

















}
