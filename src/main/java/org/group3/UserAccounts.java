package org.group3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class UserAccounts {
    // Eine Map, um Benutzerkonten mit der Benutzer-ID als Schlüssel zu speichern
    private Map<String, User> userAccounts = new HashMap<>();
    private String errorMessage; // Um Fehlermeldungen zu erfassen

    // Innere Klasse zur Darstellung eines Benutzers
    public class User {
        private String userId;
        private String name;
        private String email;
        private double prepaidBalance;
        private List<Double> moneyTopUps; // Liste der Aufladungen
        private double outstandingBalance; // Ausstehender Saldo

        public User(String name, String email) {
            this.userId = UUID.randomUUID().toString(); // Generiere eine eindeutige Benutzer-ID
            this.name = name;
            this.email = email;
            this.prepaidBalance = 0.0; // Initialisiere den Kontostand auf null
            this.moneyTopUps = new ArrayList<>(); // Initialisiere die Liste der Aufladungen
            this.outstandingBalance = 0.0; // Initialisiere den ausstehenden Saldo
        }

        // Getter und Setter
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
            this.moneyTopUps.add(amount); // Füge die Aufladung zur Liste hinzu
        }

        public List<Double> getMoneyTopUps() {
            return moneyTopUps;
        }

        public double getOutstandingBalance() {
            return outstandingBalance;
        }

        public void setOutstandingBalance(double outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }
    }

    // Erstelle ein neues Benutzerkonto
    public User createUser(String name, String email) {
        if (email == null || email.isEmpty()) {
            errorMessage = "Email is required"; // Setze Fehlermeldung, wenn die E-Mail ungültig ist
            return null; // Rückgabe von null, wenn der Benutzer nicht erstellt werden kann
        }

        User newUser = new User(name, email);
        userAccounts.put(newUser.getUserId(), newUser);
        return newUser; // Rückgabe des erstellten Benutzers
    }

    // Füge Guthaben zum Prepaid-Konto eines Benutzers hinzu
    public String addFunds(String userId, double amount) {
        if (amount < 0) {
            errorMessage = "Invalid amount"; // Setze Fehlermeldung, wenn der Betrag ungültig ist
            return errorMessage;
        }

        User user = userAccounts.get(userId);
        if (user != null) {
            user.addFunds(amount); // Füge Guthaben zum Prepaid-Konto des Benutzers hinzu
            return null; // Rückgabe von null, wenn die Operation erfolgreich ist
        } else {
            return "User not found!"; // Fehlermeldung, wenn der Benutzer nicht gefunden wurde
        }
    }

    // Setze den ausstehenden Saldo
    public String setOutstandingBalance(String userId, double balance) {
        User user = userAccounts.get(userId);
        if (user != null) {
            user.setOutstandingBalance(balance);
            return null; // Rückgabe von null, wenn die Operation erfolgreich ist
        } else {
            return "User not found!"; // Fehlermeldung, wenn der Benutzer nicht gefunden wurde
        }
    }

    // Aktualisiere die Benutzerinformationen
    public String updateUser(String userId, String name, String email) {
        User user = userAccounts.get(userId);
        if (user != null) {
            if (email == null || email.isEmpty()) {
                errorMessage = "Email is required"; // Setze Fehlermeldung, wenn die E-Mail ungültig ist
                return errorMessage;
            }
            user.setName(name);
            user.setEmail(email);
            return null; // Rückgabe von null, wenn die Operation erfolgreich ist
        } else {
            return "User not found!"; // Fehlermeldung, wenn der Benutzer nicht gefunden wurde
        }
    }

    // Lösche ein Benutzerkonto
    public String deleteUser(String userId) {
        if (userAccounts.remove(userId) != null) {
            return null; // Rückgabe von null, wenn das Konto erfolgreich gelöscht wurde
        } else {
            return "User not found!"; // Fehlermeldung, wenn der Benutzer nicht gefunden wurde
        }
    }

    // Methode zum Abrufen der Benutzerdetails (für Testzwecke)
    public User getUser(String userId) {
        return userAccounts.get(userId);
    }

    // Getter für Fehlermeldungen
    public String getErrorMessage() {
        return errorMessage; // Rückgabe der Fehlermeldung
    }
}
