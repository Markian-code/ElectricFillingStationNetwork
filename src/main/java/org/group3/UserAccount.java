package org.group3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserAccount {
    private final String userId;
    private String name;
    private String email;
    private final List<LedgerEntry> ledger;

    public UserAccount(String name, String email) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.ledger = new ArrayList<>();
    }


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
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email;
    }

    public void addLedgerEntry(LedgerType type, double amount, LocalDateTime timestamp) {
        ledger.add(new LedgerEntry(type, amount, timestamp));
    }

    public void addLedgerEntry(LedgerType type, double amount) {
        ledger.add(new LedgerEntry(type, amount, LocalDateTime.now())); // Automatischer Timestamp
    }

    public List<LedgerEntry> getLedger() {
        return new ArrayList<>(ledger);
    }

    public void addFunds(double amount, LocalDateTime timestamp) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        // Fügt eine TOP_UP-Transaktion zum Ledger hinzu
        addLedgerEntry(LedgerType.TOP_UP, amount, timestamp);
    }

    public void addFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        // Fügt eine TOP_UP-Transaktion zum Ledger hinzu
        addLedgerEntry(LedgerType.TOP_UP, amount);
    }

    public double getBalance() {
        return ledger.stream()
                .mapToDouble(entry -> entry.getType() == LedgerType.TOP_UP ? entry.getAmount() : -entry.getAmount())
                .sum();
    }

    public double getPrepaidBalance() {
        return getBalance();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


}