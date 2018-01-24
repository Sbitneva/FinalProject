package sbitneva.entity;

import java.util.ArrayList;

public class Ship {
    private int shipId;
    private String shipName;
    private int cruiseDuration;
    private ArrayList<Staff> shipStaff = new ArrayList<>();
    private ArrayList<Port> ports = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public ArrayList<Staff> getShipStaff() {
        return shipStaff;
    }

    public void setShipStaff(ArrayList<Staff> shipStaff) {
        this.shipStaff = shipStaff;
    }

    public ArrayList<Port> getPorts() {
        return ports;
    }

    public void setPorts(ArrayList<Port> ports) {
        this.ports = ports;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getCruiseDuration() {
        return cruiseDuration;
    }

    public void setCruiseDuration(int cruiseDuration) {
        this.cruiseDuration = cruiseDuration;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipId=" + shipId +
                ", shipName='" + shipName + '\'' +
                ", cruiseDuration=" + cruiseDuration +
                ", shipStaff=" + shipStaff +
                ", ports=" + ports +
                ", tickets=" + tickets +
                '}';
    }
}
