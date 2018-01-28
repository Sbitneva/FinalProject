package sbitneva.entity;

/**
 * Excursion entity.
 */
public class Excursion {

    private int excursionId;
    private String excursionName;
    private int shipId;
    private String shipName;
    private String portName;
    private int price;
    private int ticketId;

    /**
     * Excursion default constructor.
     */
    public Excursion() {

    }

    /**
     * Excursion construction from ID.
     *
     * @param excursionId Excursion ID
     */
    public Excursion(final int excursionId) {
        this.excursionId = excursionId;
    }

    /**
     * Get excursion's ticket ID.
     *
     * @return Excursion's ticket ID
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Set excursion's ticket ID.
     *
     * @param ticketId Excursion's ticket ID
     */
    public void setTicketId(final int ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Get excursion ID.
     *
     * @return Excursion ID
     */
    public int getExcursionId() {
        return excursionId;
    }

    /**
     * Set excursion ID.
     *
     * @param excursionId Excursion ID
     */
    public void setExcursionId(final int excursionId) {
        this.excursionId = excursionId;
    }

    /**
     * Get excursion's ship ID.
     *
     * @return Excursion's ship ID.
     */
    public int getShipId() {
        return shipId;
    }

    /**
     * Set excursion's ship ID.
     *
     * @param shipId Excursion's ship ID
     */
    public void setShipId(final int shipId) {
        this.shipId = shipId;
    }

    /**
     * Get excursion's ship name.
     *
     * @return Excursion's ship name
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * Set excursion's ship name.
     *
     * @param shipName Excursion's ship name
     */
    public void setShipName(final String shipName) {
        this.shipName = shipName;
    }

    /**
     * Get excursion's port name.
     *
     * @return Port name
     */
    public String getPortName() {
        return portName;
    }

    /**
     * Set excursion's port name.
     *
     * @param portName Port name
     */
    public void setPortName(final String portName) {
        this.portName = portName;
    }

    /**
     * Get excursion price.
     *
     * @return Excursion price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Set excursion price.
     * @param price Excursion price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * Get excursion name.
     *
     * @return Excursion name
     */
    public String getExcursionName() {
        return excursionName;
    }

    /**
     * Set excursion name.
     *
     * @param excursionName Excursion name
     */
    public void setExcursionName(final String excursionName) {
        this.excursionName = excursionName;
    }

    /**
     * Excursion representation.
     *
     * @return Excursion entity description string
     */
    @Override
    public String toString() {
        return "Excursion{"
                + "excursionId=" + excursionId
                + ", shipId=" + shipId
                + ", shipName='" + shipName + '\''
                + ", portName='" + portName + '\''
                + ", price=" + price
                + '}';
    }
}
