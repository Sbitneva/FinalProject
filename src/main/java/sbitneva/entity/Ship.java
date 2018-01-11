package sbitneva.entity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Ship {
    private int shipId;
    private String shipName;
    private ArrayList<Staff> shipStaff = new ArrayList<>();
    private ArrayList<Port> ports = new ArrayList<>();

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
}
