package org.group3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class UserAccounts {
    private Map<String, User> userAccounts = new HashMap<>();
    private String errorMessage;

    public class User {
        private String userId;
        private String name;
        private String email;
        private double prepaidBalance;
        private List<Double> moneyTopUps;
        private double outstandingBalance;

        public User(String name, String email) {
            this.userId = UUID.randomUUID().toString();
            this.name = name;
            this.email = email;
            this.prepaidBalance = 0.0;
            this.moneyTopUps = new ArrayList<>();
            this.outstandingBalance = 0.0;
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
            this.email = email;
        }

        public double getPrepaidBalance() {
            return prepaidBalance;
        }

        public void addFunds(double amount) {
            this.prepaidBalance += amount;
            this.moneyTopUps.add(amount);
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


    public User createUser(String name, String email) {
        if (email == null || email.isEmpty()) {
            errorMessage = "Email is required";
            return null;
        }

        User newUser = new User(name, email);
        userAccounts.put(newUser.getUserId(), newUser);
        return newUser;
    }


    public String addFunds(String userId, double amount) {
        if (amount < 0) {
            errorMessage = "Invalid amount";
            return errorMessage;
        }

        User user = userAccounts.get(userId);
        if (user != null) {
            user.addFunds(amount);
            return null;
        } else {
            return "User not found!";
        }
    }


    public String setOutstandingBalance(String userId, double balance) {
        User user = userAccounts.get(userId);
        if (user != null) {
            user.setOutstandingBalance(balance);
            return null;
        } else {
            return "User not found!";
        }
    }


    public String updateUser(String userId, String name, String email) {
        User user = userAccounts.get(userId);
        if (user != null) {
            if (email == null || email.isEmpty()) {
                errorMessage = "Email is required";
                return errorMessage;
            }
            user.setName(name);
            user.setEmail(email);
            return null;
        } else {
            return "User not found!";
        }
    }


    public String deleteUser(String userId) {
        if (userAccounts.remove(userId) != null) {
            return null;
        } else {
            return "User not found!";
        }
    }


    public User getUser(String userId) {
        return userAccounts.get(userId);
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
