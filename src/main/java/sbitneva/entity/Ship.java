package sbitneva.entity;

import java.util.ArrayList;

/**
 * Ship entity.
 */
public class Ship {

    private int shipId;
    private String shipName;
    private int cruiseDuration;
    private ArrayList<Staff> shipStaff = new ArrayList<>();
    private ArrayList<Port> ports = new ArrayList<>();
    private ArrayList<Ticket> tickets = new ArrayList<>();

    /**
     * Get ship ID.
     *
     * @return Ship ID
     */
    public int getShipId() {
        return shipId;
    }

    /**
     * Set ship ID.
     *
     * @param shipId Ship ID
     */
    public void setShipId(final int shipId) {
        this.shipId = shipId;
    }

    /**
     * Get ship name.
     *
     * @return Ship name
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * Set ship name.
     *
     * @param shipName Ship name
     */
    public void setShipName(final String shipName) {
        this.shipName = shipName;
    }

    /**
     * Get ship's staff list.
     *
     * @return Staff list
     */
    public ArrayList<Staff> getShipStaff() {
        return shipStaff;
    }

    /**
     * Set ship's staff.
     *
     * @param shipStaff Staff list
     */
    public void setShipStaff(final ArrayList<Staff> shipStaff) {
        this.shipStaff = shipStaff;
    }

    /**
     * Get ship's cruise ports.
     *
     * @return Ship's cruise ports list
     */
    public ArrayList<Port> getPorts() {
        return ports;
    }

    /**
     * Set ship's cruise ports.
     *
     * @param ports Ship's cruise ports list
     */
    public void setPorts(final ArrayList<Port> ports) {
        this.ports = ports;
    }

    /**
     * Get ship's tickets.
     *
     * @return Ship's tickets list
     */
    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Set ship's tickets.
     *
     * @param tickets Ship's tickets list
     */
    public void setTickets(final ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * Get ship's cruise duration.
     *
     * @return Ship's cruise duration
     */
    public int getCruiseDuration() {
        return cruiseDuration;
    }

    /**
     * Set ship's cruise duration.
     *
     * @param cruiseDuration Ship's cruise duration
     */
    public void setCruiseDuration(final int cruiseDuration) {
        this.cruiseDuration = cruiseDuration;
    }

    /**
     * Ship representation.
     *
     * @return Ship entity description string
     */
    @Override
    public String toString() {
        return "Ship{"
                + "shipId=" + shipId
                + ", shipName='" + shipName + '\''
                + ", cruiseDuration=" + cruiseDuration
                + ", shipStaff=" + shipStaff
                + ", ports=" + ports
                + ", tickets=" + tickets
                + '}';
    }
}
