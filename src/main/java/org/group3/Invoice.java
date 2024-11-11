package org.group3;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Invoice {

    private final String userEmail;
    private final ECS ecs;


    public Invoice(String userEmail, ECS ecs) {
        this.userEmail = userEmail;
        this.ecs = ecs;
    }


    public double calculateTotalCost() {
        double totalCost = 0;
        for (ChargingSession session : getUserSessions()) {
            totalCost += session.getTotalCost();
        }
        return totalCost;
    }

     // Ruft die Liste der Ladesitzungen des Benutzers ab.

    public List<ChargingSession> getUserSessions() {
        return ecs.getChargingSessionsByUser(userEmail);
    }


     // Ruft die Liste der Aufladungen des Benutzers ab.

    public List<LedgerEntry> getTopUps() {
        UserAccount user = ecs.getUserByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getLedger();
    }


    public List<LedgerLine> createDetailedUserInvoice() {
        List<LedgerLine> ledgerLines = new ArrayList<>();
        List<LedgerEntry> allTransactions = new ArrayList<>(getTopUps());

        // Füge Ladesitzungen als LedgerEntries hinzu, falls noch nicht vorhanden

        for (ChargingSession session : getUserSessions()) {
            boolean alreadyAdded = allTransactions.stream()
                    .anyMatch(entry -> entry.getTimestamp().equals(session.getStartTime())
                            && entry.getType() == LedgerType.CHARGING_SESSION);
            if (!alreadyAdded) {
                LedgerEntry newEntry = new LedgerEntry(LedgerType.CHARGING_SESSION, session.getTotalCost(), session.getStartTime());
                allTransactions.add(newEntry);
            }
        }

        Set<LedgerEntry> uniqueTransactions = new LinkedHashSet<>(allTransactions);
        List<LedgerEntry> sortedTransactions = new ArrayList<>(uniqueTransactions);
        sortedTransactions.sort(Comparator.comparing(LedgerEntry::getTimestamp));

        DecimalFormat df = new DecimalFormat("#.00");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        double runningBalance = 0;

        for (LedgerEntry entry : sortedTransactions) {
            LedgerLine ledgerLine = new LedgerLine();
            ledgerLine.setTimestamp(entry.getTimestamp().format(formatter));

            if (entry.getType() == LedgerType.TOP_UP) {
                runningBalance += entry.getAmount();
                ledgerLine.setLedgerType("Top-Up");
                ledgerLine.setCost(df.format(entry.getAmount()));
                ledgerLine.setBalance(df.format(runningBalance));
                ledgerLine.setLocation("[empty]");
                ledgerLine.setChargerId("[empty]");
                ledgerLine.setType("[empty]");
                ledgerLine.setDuration("[empty]");
                ledgerLine.setPower("[empty]");
            } else if (entry.getType() == LedgerType.CHARGING_SESSION) {
                Optional<ChargingSession> matchingSession = getUserSessions().stream()
                        .filter(s -> s.getStartTime().equals(entry.getTimestamp()))
                        .findFirst();

                if (matchingSession.isPresent()) {
                    ChargingSession session = matchingSession.get();
                    long durationMinutes = session.getDuration().toMinutes();
                    runningBalance -= session.getTotalCost();

                    ledgerLine.setLedgerType("Charging Session");
                    ledgerLine.setLocation(session.getLocation().getName());
                    ledgerLine.setChargerId(session.getCharger().getChargerId());
                    ledgerLine.setType(session.getCharger().getType().toString());
                    ledgerLine.setDuration(String.valueOf(durationMinutes));
                    ledgerLine.setPower(String.valueOf(session.getPowerConsumed()));
                    ledgerLine.setCost(df.format(-session.getTotalCost()));
                    ledgerLine.setBalance(df.format(runningBalance));
                } else {
                    throw new IllegalArgumentException("Matching session not found for Ledger entry at " + entry.getTimestamp());
                }
            } else {
                continue;
            }

            ledgerLines.add(ledgerLine);
        }

        return ledgerLines;
    }

    public void printDetailedUserInvoice() {
        List<LedgerLine> ledgerLines = createDetailedUserInvoice();


        System.out.println("Invoice for user: " + userEmail);
        System.out.println("===============================================================================================================");
        System.out.println("| Timestamp        | Ledger Type       | Location              | ChargerID | Type   | Duration (min) | Power (kWh) | Cost (€) | Balance (€) |");
        System.out.println("===============================================================================================================");

        // Durchlaufen der LedgerLines und Ausgabe jeder Zeile
        for (LedgerLine line : ledgerLines) {
            String formattedLine = String.format(
                    "| %-16s | %-17s | %-20s | %-9s | %-6s | %-14s | %-11s | %-8s | %-11s |",
                    line.getTimestamp(),
                    line.getLedgerType(),
                    line.getLocation(),
                    line.getChargerId(),
                    line.getType(),
                    line.getDuration(),
                    line.getPower(),
                    line.getCost(),
                    line.getBalance()
            );
            System.out.println(formattedLine);
        }
        System.out.println("===============================================================================================================");
    }


     // Innere Klasse LedgerLine, die eine Zeile im Ledger darstellt.

    public static class LedgerLine {
        private String timestamp;
        private String ledgerType;
        private String location;
        private String chargerId;
        private String type;
        private String duration;
        private String power;
        private String cost;
        private String balance;

        // Getter und Setter

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getLedgerType() {
            return ledgerType;
        }

        public void setLedgerType(String ledgerType) {
            this.ledgerType = ledgerType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getChargerId() {
            return chargerId;
        }

        public void setChargerId(String chargerId) {
            this.chargerId = chargerId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
