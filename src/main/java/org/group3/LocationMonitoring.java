package org.group3;

import java.util.List;

public class LocationMonitoring {

    private ECS ecs;

    public LocationMonitoring(ECS ecs) {
        this.ecs = ecs;
    }

    public void displayAllLocationsWithChargers() {
        List<Location> locations = ecs.getAllLocations();

        if (locations.isEmpty()) {
            System.out.println("No locations found");
            return;
        }


        for (Location location : locations) {
            System.out.println("Location: " + location.getName());
            System.out.println("Address: " + location.getAddress());
            System.out.println("AC price per kWh: " + location.getPricePerACkwH());
            System.out.println("DC price per kWh: " + location.getPricePerDCkwH());
            System.out.println("AC price per Minute: " + location.getPriceACMinute());
            System.out.println("DC price per Minute: " + location.getPriceDCMinute());

            List<Charger> chargers = location.getChargers();
            if (chargers.isEmpty()) {
                System.out.println("No chargers found at this location");
            } else {
                System.out.println("All chargers at location:");
                for (Charger charger : chargers) {
                    System.out.println("    Charger ID: " + charger.getChargerId() +
                            ", Type: " + charger.getType() +
                            ", Status: " + charger.getStatus());
                }
            }
            System.out.println("-----------------------------------");
        }
    }


    public void displayChargerStatus(String locationName, String chargerId) {
        Location location = ecs.getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location '" + locationName + "' not found"));


        Charger charger = location.getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID '" + chargerId + "' not found at location '" + locationName + "'"));


        System.out.println("Charger ID: " + charger.getChargerId());
        System.out.println("Type: " + charger.getType());
        System.out.println("Status: " + charger.getStatus());
    }


    public void displayAvailableChargers(String locationName) {
        Location location = ecs.getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location '" + locationName + "' not found"));

        List<Charger> chargers = location.getChargers();
        System.out.println("Available chargers at " + locationName + ":");
        for (Charger charger : chargers) {
            if (charger.getStatus() == ChargerStatus.FREI) {
                System.out.println("  Charger ID: " + charger.getChargerId() + ", Type: " + charger.getType()+ ", Status: " + charger.getStatus());
            }
        }
    }
}