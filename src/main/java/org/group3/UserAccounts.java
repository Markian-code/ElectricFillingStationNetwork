package org.group3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserAccounts {
    // Eine Map, um Benutzerkonten mit der Benutzer-ID als Schlüssel zu speichern
    private Map<String, User> userAccounts = new HashMap<>();

    // Innere Klasse zur Darstellung eines Benutzers
    public class User {
        private String userId;
        private String name;
        private String email;
        private double prepaidBalance;

        public User(String name, String email) {
            this.userId = UUID.randomUUID().toString(); // Generiere eine eindeutige Benutzer-ID
            this.name = name;
            this.email = email;
            this.prepaidBalance = 0.0; // Initialisiere den Kontostand auf null
        }

        // Getters und Setters
        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getPrepaidBalance() {
            return prepaidBalance;
        }

        public void addFunds(double amount) {
            this.prepaidBalance += amount; // Füge dem Kontostand Guthaben hinzu
        }
    }

    // Erstelle ein neues Benutzerkonto
    public User createUser(String name, String email) {
        User newUser = new User(name, email);
        userAccounts.put(newUser.getUserId(), newUser);
        return newUser; // Return the created user
    }

    // Füge Guthaben zum Prepaid-Konto eines Benutzers hinzu
    public void addFunds(String userId, double amount) {
        User user = userAccounts.get(userId);
        if (user != null) {
            user.addFunds(amount);
        } else {
            System.out.println("User not found!");
        }
    }

    // Aktualisiere die Benutzerinformationen
    public void updateUser(String userId, String name, String email) {
        User user = userAccounts.get(userId);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            System.out.println("User information updated successfully.");
        } else {
            System.out.println("User not found!");
        }
    }

    // Lösche ein Benutzerkonto
    public void deleteUser(String userId) {
        if (userAccounts.remove(userId) != null) {
            System.out.println("User account deleted successfully.");
        } else {
            System.out.println("User not found!");
        }
    }

    // Methode zum Abrufen der Benutzerdetails (zu Testzwecken)
    public User getUser(String userId) {
        return userAccounts.get(userId);
    }
}
