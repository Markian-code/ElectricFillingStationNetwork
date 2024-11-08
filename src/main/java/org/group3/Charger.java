package org.group3;

public class Charger {

    private final String chargerId;
    private ChargerType type;
    private ChargerStatus status;


    Charger(String chargerId, ChargerType type, ChargerStatus status) {
        this.chargerId = chargerId;
        this.type = type;
        this.status = status;
    }

    public String getChargerId() {
        return chargerId;
    }

    public ChargerType getType() {
        return type;
    }

    public ChargerStatus getStatus() {
        return status;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }

    public void setType(ChargerType type) {
        this.type = type;
    }

}

