package org.group3;

import java.util.UUID;

public class UserAccount {
    private final String userId;
    private String name;
    private String email;
    private double prepaidBalance;

    public UserAccount(String name, String email) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.prepaidBalance = 0.0;
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

    public double getPrepaidBalance() {
        return prepaidBalance;
    }

    public void addFunds(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.prepaidBalance += amount;
    }

    public void deductBalance(double amount) {
        if (amount > 0 && amount <= prepaidBalance) {
            this.prepaidBalance -= amount;
        } else {
            throw new IllegalStateException("Insufficient funds.");
        }
    }

    public double getBalance() {
        return prepaidBalance;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


}
