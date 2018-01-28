package sbitneva.entity;

import java.util.ArrayList;

/**
 * Port entity.
 */
public class Port {

    private int portId;
    private String portName;
    private ArrayList<Excursion> excursions = new ArrayList<>();

    /**
     * Get port ID.
     *
     * @return Port ID
     */
    public int getPortId() {
        return portId;
    }

    /**
     * Set port ID.
     *
     * @param portId Port ID
     */
    public void setPortId(final int portId) {
        this.portId = portId;
    }

    /**
     * Get port name.
     *
     * @return Port name
     */
    public String getPortName() {
        return portName;
    }

    /**
     * Set port name.
     *
     * @param portName Port name
     */
    public void setPortName(final String portName) {
        this.portName = portName;
    }

    /**
     * Get port's excursions list.
     *
     * @return Port excursions list
     */
    public ArrayList<Excursion> getExcursions() {
        return excursions;
    }

    /**
     * Set port's excursions list.
     * @param excursions Port excursions list
     */
    public void setExcursions(final ArrayList<Excursion> excursions) {
        this.excursions = excursions;
    }

    /**
     * Port representation.
     *
     * @return Port entity description string
     */
    @Override
    public String toString() {
        return "Port{"
                + "portId=" + portId
                + ", portName='" + portName + '\''
                + '}';
    }
}
