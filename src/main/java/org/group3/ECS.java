package org.group3;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ECS {
    private static final List<Location> locations = new ArrayList<>();
    private static final List<ChargingSession> chargingSessions = new ArrayList<>();
    private static final Map<String, UserAccount> usersAccounts = new HashMap<>();


    // ------- Location Management -------

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Optional<Location> getLocation(String locationName) {
        return locations.stream()
                .filter(loc -> loc.getName().equals(locationName))
                .findFirst();
    }

    public void removeLocation(String locationName) {
        Optional<Location> locationOpt = getLocation(locationName);
        if (locationOpt.isPresent()) {
            locations.remove(locationOpt.get());
        } else {
            throw new IllegalArgumentException("Location with name " + locationName + " does not exist.");
        }
    }

    public int getNumberOfLocations() {
        return locations.size();
    }

    public List<Location> getAllLocations() {
        return new ArrayList<>(locations);
    }

    public void updatePrice(String locationName, double newPrice, PriceType type) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));
        switch (type) {
            case AC_KWH -> location.setPricePerACkwH(newPrice);
            case DC_KWH -> location.setPricePerDCkwH(newPrice);
            case AC_MINUTE -> location.setPriceACMinute(newPrice);
            case DC_MINUTE -> location.setPriceDCMinute(newPrice);
        }
    }

    public void addLocationWithChargers(String name, String address, double pricePerACkwH, double pricePerDCkwH, double priceACMinute, double priceDCMinute, List<Charger> chargers) {
        Location location = new Location(name, address, pricePerACkwH, pricePerDCkwH, priceACMinute, priceDCMinute);

        for (Charger charger : chargers) {
            location.addCharger(charger);
        }
        addLocation(location);
    }



    // ------- Charger Management -------


    public void updateChargerStatus(String locationName, String chargerId, ChargerStatus newStatus) {

        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));

        Charger charger = location.getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID " + chargerId + " does not exist at this location."));

        charger.setStatus(newStatus);
    }

    public void updateCharger(String locationName, String chargerId, ChargerType newType, ChargerStatus newStatus) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));

        location.updateCharger(chargerId, newType, newStatus);
    }

    public void addChargerToLocation(String locationName, String chargerId, ChargerType type, ChargerStatus status) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name " + locationName + " does not exist."));

        Optional<Charger> existingCharger = location.getChargerById(chargerId);
        if (existingCharger.isEmpty()) {
            Charger newCharger = new Charger(chargerId, type, status);
            location.addCharger(newCharger);
        }
    }

    public void removeChargerFromLocation(String locationName, String chargerId) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name" + locationName + "does not exist."));

        if (!location.removeCharger(chargerId)) {
            throw new IllegalArgumentException("Charger with ID " + chargerId + "does not exist at this location.");
        }
    }


    public List<String> getChargersAtLocation(String locationName) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location with name" + locationName + "does not exist."));

        return location.getChargers().stream()
                .map(Charger::getChargerId)  // Extrahiere die Charger-IDs
                .collect(Collectors.toList());
    }

    public Optional<Charger> getCharger(String chargerId) {
        for (Location location : locations) {
            Optional<Charger> charger = location.getChargerById(chargerId);
            if (charger.isPresent()) {
                return charger;
            }
        }
        return Optional.empty();
    }





    // ------- UserAccount Management -------

    public void addUser(UserAccount user) {
        if (usersAccounts.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }
        usersAccounts.put(user.getEmail(), user);
    }

    public Optional<UserAccount> getUserByEmail(String email) {
        return Optional.ofNullable(usersAccounts.get(email));
    }

    public void removeUserByEmail(String email) {
        if (!usersAccounts.containsKey(email)) {
            throw new IllegalArgumentException("User with email " + email + " does not exist.");
        }
        usersAccounts.remove(email);
    }

    public boolean updateUser(String email, String newName, String newEmail) {
        UserAccount user = usersAccounts.remove(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " does not exist.");
        }

        user.setName(newName);
        user.setEmail(newEmail);

        if (usersAccounts.containsKey(newEmail)) {
            throw new IllegalArgumentException("User with email " + newEmail + " already exists.");
        }

        usersAccounts.put(newEmail, user);
        return true;
    }

    public boolean addFundsToUser(String email, double amount) {
        UserAccount user = usersAccounts.get(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " does not exist.");
        }
        user.addFunds(amount); // Automatisch LocalDateTime.now()
        return true;
    }

    public boolean addFundsToUser(String email, double amount, LocalDateTime timestamp) {
        UserAccount user = usersAccounts.get(email);
        if (user == null) {
            throw new IllegalArgumentException("User with email " + email + " does not exist.");
        }
        user.addFunds(amount, timestamp);
        return true;
    }

    public int getNumberOfUsers() {
        return usersAccounts.size();
    }


// ------- Charging Session  -------

    public void createChargingSession(String userEmail, String locationName, String chargerId, LocalDateTime startTime, double powerConsumed) {
        Location location = getLocation(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Location '" + locationName + "' not found"));
        Charger charger = location.getChargerById(chargerId)
                .orElseThrow(() -> new IllegalArgumentException("Charger with ID '" + chargerId + "' not found"));

        UserAccount userAccount = getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + userEmail + "' not found"));

        ChargingSession session = new ChargingSession(userAccount, location, charger, powerConsumed, startTime);
        chargingSessions.add(session);
    }

    public void endChargingSession(String userEmail, LocalDateTime endTime) {
        ChargingSession activeSession = chargingSessions.stream()
                .filter(session -> session.getUserEmail().equals(userEmail) && session.getSessionState() == SessionState.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No active session found for user with email '" + userEmail + "'"));

        activeSession.endSession(endTime);
    }

    public List<ChargingSession> getChargingSessionsByUser(String userEmail) {
        return chargingSessions.stream()
                .filter(session -> session.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }


}