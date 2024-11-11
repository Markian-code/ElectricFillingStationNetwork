package org.group3;


import java.time.LocalDateTime;

public class LedgerEntry {
    private final LedgerType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public LedgerEntry(LedgerType type, double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public LedgerType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "LedgerEntry{" +
                "type=" + type +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }

}

