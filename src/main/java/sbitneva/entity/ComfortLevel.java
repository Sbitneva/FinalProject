package sbitneva.entity;

import java.util.ArrayList;

public class ComfortLevel {
    private int comfortLevelId;
    private String comfortLevelName = "";
    private ArrayList<Service> services = new ArrayList<>();

    public int getComfortLevelId() {
        return comfortLevelId;
    }

    public void setComfortLevelId(int comfortLevelId) {
        this.comfortLevelId = comfortLevelId;
    }

    public String getComfortLevelName() {
        return comfortLevelName;
    }

    public void setComfortLevelName(String comfortLevelName) {
        this.comfortLevelName = comfortLevelName;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "ComfortLevel{" +
                "comfortLevelId=" + comfortLevelId +
                ", comfortLevelName='" + comfortLevelName + '\'' +
                ", services=" + services +
                '}';
    }
}
