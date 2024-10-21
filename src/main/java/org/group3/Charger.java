package org.group3;

import java.util.HashMap;
import java.util.Map;

public class Charger {
    private final String chargerId;
    private final Type type;
    private Status status;

    private static final Map<String, Charger> chargers = new HashMap<>();

    private Charger(String chargerId, Type type, Status status) {
        this.chargerId = chargerId;
        this.type = type;
        this.status = status;
    }

    public static Charger createCharger(String chargerId, Type type, Status status) {
        Charger charger = new Charger(chargerId, type, status);
        chargers.put(chargerId, charger);
        return charger;
    }

    public static Charger getChargerById(String chargerId) {
        return chargers.get(chargerId);
    }

    public String getChargerId() {
        return chargerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Type getType() {
        return type;
    }

    public static void deleteCharger(String chargerId) {
        chargers.remove(chargerId);
    }

    public static int getTotalNumberOfChargers() {
        return chargers.size();
    }


}

