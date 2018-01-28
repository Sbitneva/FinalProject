package sbitneva.entity;

import java.util.ArrayList;

/**
 * Comfort level entity.
 */
public class ComfortLevel {
    private int comfortLevelId;
    private String comfortLevelName = "";
    private ArrayList<Service> services = new ArrayList<>();

    /**
     * Get comfort level ID.
     *
     * @return Comfort level ID
     */
    public int getComfortLevelId() {
        return comfortLevelId;
    }

    /**
     * Set comfot level ID.
     *
     * @param comfortLevelId Comfort level ID
     */
    public void setComfortLevelId(final int comfortLevelId) {
        this.comfortLevelId = comfortLevelId;
    }

    /**
     * Get comfort level name.
     *
     * @return Comfort level name
     */
    public String getComfortLevelName() {
        return comfortLevelName;
    }

    /**
     * Set comfort level name.
     *
     * @param comfortLevelName Comfort level name
     */
    public void setComfortLevelName(final String comfortLevelName) {
        this.comfortLevelName = comfortLevelName;
    }

    /**
     * Get additional services for comfort level.
     *
     * @return Services list
     */
    public ArrayList<Service> getServices() {
        return services;
    }

    /**
     * Set additional services for comfort level.
     * @param services Services list
     */
    public void setServices(final ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "ComfortLevel{"
                + "comfortLevelId=" + comfortLevelId
                + ", comfortLevelName='" + comfortLevelName + '\''
                + ", services=" + services
                + '}';
    }
}
