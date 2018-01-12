package sbitneva.entity;

import java.util.ArrayList;

public class Port {
    private int portId;
    private String portName;
    private ArrayList<Excursion> excursions = new ArrayList<>();

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public ArrayList<Excursion> getExcursions() {
        return excursions;
    }

    public void setExcursions(ArrayList<Excursion> excursions) {
        this.excursions = excursions;
    }

    @Override
    public String toString() {
        return "Port{" +
                "portId=" + portId +
                ", portName='" + portName + '\'' +
                '}';
    }
}
