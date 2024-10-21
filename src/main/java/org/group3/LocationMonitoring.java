package org.group3;

import java.util.List;
import java.util.Map;

public class LocationMonitoring {

    private Location location;

    public LocationMonitoring(Location location) {
        this.location = location;
    }

    public void displayAllLocationsWithChargers() {
        Map<String, LocationData> locationsMap = location.getAllLocations();

        if (locationsMap.isEmpty()) {
            System.out.println("No locations found");
            return;
        }

        for (Map.Entry<String, LocationData> entry : locationsMap.entrySet()) {
            String locationName = entry.getKey();
            LocationData locationData = entry.getValue();

            System.out.println("Location: " + locationName);
            System.out.println("Address: " + locationData.getAddress());
            System.out.println("AC price per kWh: " + locationData.getPricePerACkwH());
            System.out.println("DC price per kWh: " + locationData.getPricePerDCkwH());
            System.out.println("AC price per Minute: " + locationData.getPriceACMinute());
            System.out.println("DC price per Minute: " + locationData.getPriceDCMinute());

            List<Charger> chargers = locationData.getChargers();
            if (chargers.isEmpty()) {
                System.out.println("No chargers found");
            } else {
                System.out.println("Available chargers:");
                for (Charger charger : chargers) {
                    System.out.println("    Charger ID: " + charger.getChargerId() +
                            ", Typ: " + charger.getType() +
                            ", Status: " + charger.getStatus());
                }
            }
            System.out.println("-----------------------------------");
        }
    }
}

