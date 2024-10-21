package org.group3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private static int id = 0;

    public Invoice(List<ChargingSession> sessions) {
        id = id++;
        //this.user = user;
        this.sessions = sessions;
    }

    public void addSession(ChargingSession session) {
        this.sessions.add(session);
    }

    public int getId() {
        return id;
    }

    /*public User getUser() {
        return user;
    }*/

    public static void setId(int id) {
        Invoice.id = id;
    }

    public List<ChargingSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<ChargingSession> sessions) {
        this.sessions = sessions;
    }

    public String createInvoice() {
        double finalprice = 0;
        for (ChargingSession session : sessions) {
            finalprice += session.getPrice();
            session.toString();
        }
        return "WIP";
    }

    //Creates the invoice based on the sessions, charged kWh, duration and type
    // Über User zu Session zu Location zu Charger
    // kWh * duration * typeCost

    /*public double createInvoice(User user) {
        double sum = 0;
        double kWh, duration, typeCost;
        for (auto& u : user.getSessions()) { //Loop um jedes Element durchzugehen
            kWh = u.getSessions().getkWH;
            duration = u.getSessions().getDurationInMinutes;
            typeCost = u.getSessions().getLocation().getChargerType().getPrice;
            sum += kWh * duration * typeCost;

        }
    }*/

}
